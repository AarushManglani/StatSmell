from fastapi import FastAPI, UploadFile, Form
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import FileResponse
import joblib
import numpy as np
from smell_metrics import extract_metrics, rule_based_detect
import os
from xgboost import XGBClassifier

# --- Paths ---
BASE_DIR = os.path.dirname(os.path.abspath(__file__))   # backend/
ROOT_DIR = os.path.dirname(BASE_DIR)                    # smell-ml-web/
FRONTEND_DIR = os.path.join(ROOT_DIR, "frontend")       # smell-ml-web/frontend/
MODEL_PKL = os.path.join(BASE_DIR, "smell_model.pkl")

# --- FastAPI App ---
app = FastAPI(title="Hybrid Code Smell Detector")

# Allow frontend to access backend
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)

# --- Serve Frontend ---
@app.get("/")
def serve_frontend():
    """Serve the main index.html page."""
    index_path = os.path.join(FRONTEND_DIR, "index.html")
    if not os.path.exists(index_path):
        return {"error": f"index.html not found at {index_path}"}
    return FileResponse(index_path)

# --- Load ML Model ---
model_data = joblib.load(MODEL_PKL)
le = model_data["label_encoder"]
model_json_path = model_data["model_path"]

model = XGBClassifier()
model.load_model(model_json_path)
print(f"✅ Loaded ML model from {model_json_path}")

# --- Prediction Route ---
@app.post("/predict")
async def predict_smell(code: str = Form(None), file: UploadFile = None):
    try:
        # --- read Java code ---
        if file:
            code = (await file.read()).decode("utf-8")
        if not code or not code.strip():
            return {"error": "No Java code provided."}

        # --- extract metrics ---
        metrics = extract_metrics(code)
        print("📊 Extracted metrics:", metrics)

        # --- Rule-based ---
        rule_smell = rule_based_detect(code, metrics)
        if rule_smell:
            return {
                "smell": str(rule_smell),
                "confidence": 95.0,
                "source": "rule-based"
            }

        # --- ML-based ---
        X = np.array(metrics).reshape(1, -1)
        probs = model.predict_proba(X)[0]
        idx = int(np.argmax(probs))
        label = str(le.inverse_transform([idx])[0])
        conf = float(round(float(probs[idx]) * 100, 2))  # ✅ ensure native float

        # --- fallback ---
        if conf < 60 and label != "NO_SMELL":
            label = "NO_SMELL"

        return {
            "smell": label,
            "confidence": conf,
            "source": "ml"
        }

    except Exception as e:
        return {"error": str(e)}
