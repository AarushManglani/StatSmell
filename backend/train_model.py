# train_model.py
import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from imblearn.over_sampling import SMOTE
from xgboost import XGBClassifier
from sklearn.metrics import classification_report, confusion_matrix
import joblib
from pathlib import Path

BASE_DIR = Path(__file__).resolve().parent
DATA_PATH = BASE_DIR.parent / "data" / "smell_dataset_realistic.csv"
MODEL_PATH = BASE_DIR / "smell_model.pkl"
MODEL_JSON = BASE_DIR / "smell_model.json"

def train_and_save_model():
    print(f"📂 Loading dataset from: {DATA_PATH}")
    df = pd.read_csv(DATA_PATH)

    print(f"✅ Loaded dataset: {df.shape[0]} rows, {df.shape[1]} columns")

    expected_cols = [
        "NOC", "ELOC", "NMNOPARAM", "PRM", "PMMM",
        "WMC", "NOA", "LCOM", "CYCLO", "NOPA",
        "FanIn", "MaxFanOut", "SMELL"
    ]
    for col in expected_cols:
        if col not in df.columns:
            raise ValueError(f"Missing column: {col}")

    X = df.drop("SMELL", axis=1)
    y = df["SMELL"]

    le = LabelEncoder()
    y_enc = le.fit_transform(y)

    print("🧩 Before SMOTE:", np.bincount(y_enc))

    smote = SMOTE(random_state=42, k_neighbors=5)
    X_res, y_res = smote.fit_resample(X, y_enc)

    print("🔁 After SMOTE:", np.bincount(y_res))
    print(f"✅ Balanced dataset size: {X_res.shape}")

    X_train, X_val, y_train, y_val = train_test_split(
        X_res, y_res, test_size=0.2, random_state=42, stratify=y_res
    )

    print("🚀 Training XGBoost 3.0.5 model...")
    model = XGBClassifier(
        n_estimators=250,
        learning_rate=0.05,
        max_depth=7,
        subsample=0.9,
        colsample_bytree=0.8,
        random_state=42,
        n_jobs=-1,
        eval_metric="mlogloss"
    )

    model.fit(X_train, y_train, eval_set=[(X_val, y_val)], verbose=False)

    preds = model.predict(X_val)
    print("\n=== Final Model Performance ===")
    print(classification_report(y_val, preds, target_names=le.classes_))
    print(confusion_matrix(y_val, preds))

    # Save model in portable JSON format (XGBoost 3+ recommended way)
    model.save_model(MODEL_JSON)
    joblib.dump({"model_path": str(MODEL_JSON), "label_encoder": le}, MODEL_PATH)

    print(f"\n✅ Model saved successfully:")
    print(f"   - Model JSON: {MODEL_JSON}")
    print(f"   - Metadata:   {MODEL_PATH}")

if __name__ == "__main__":
    train_and_save_model()
