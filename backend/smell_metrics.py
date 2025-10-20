import os
import jpype
import jpype.imports
from jpype.types import *
import re

BASE_DIR = os.path.dirname(os.path.abspath(__file__))

def start_jvm():
    if not jpype.isJVMStarted():
        jars = [
            os.path.join(BASE_DIR, "javaparser-core-3.26.1.jar"),
            os.path.join(BASE_DIR, "javaparser-symbol-solver-core-3.26.1.jar"),
        ]
        cp = [BASE_DIR] + jars
        jpype.startJVM(classpath=";".join(cp))
        print(f"✅ JVM started with: {';'.join(cp)}")

def extract_metrics(java_code: str):
    try:
        start_jvm()
        MetricExtractor = jpype.JClass("MetricExtractor")
        result = MetricExtractor.extractMetrics(java_code)
        return [float(x) for x in result]
    except Exception as e:
        print(f"⚠️ Error extracting metrics: {e}")
        return [0.0] * 12

def rule_based_detect(code: str, metrics: list) -> str | None:
    noc, eloc, nmnoparam, prm, pmmm, wmc, noa, lcom, cyclo, nopa, fan_in, fan_out = metrics

    # --- Refused Bequest (RB) ---
    if (
        re.search(r"throw\s+new\s+UnsupportedOperationException", code)
        or re.search(
            r"@Override\s+public\s+\w+\s+\w+\s*\([^)]*\)\s*\{[^a-zA-Z0-9]*\}",
            code,
            re.DOTALL,
        )
        or re.search(
            r"@Override\s+public\s+\w+\s+\w+\s*\([^)]*\)\s*\{\s*//[^}]*\}",
            code,
            re.DOTALL,
        )
    ):
        return "RB"




        # --- Inappropriate Intimacy (II) ---
    ii_pattern = re.compile(
        r"\b(?!this|super|System)\w+(?:\.\w+)+\s*=",
        re.MULTILINE
    )

    external_accesses = ii_pattern.findall(code)
    if external_accesses:
        print(f"DEBUG — II detected: {external_accesses}")
        return "II"

        # --- Speculative Generality (SG) ---
    sg_decl_pattern = re.compile(r'\b(?:abstract\s+class|interface)\s+(\w+)', re.MULTILINE)
    sg_use_pattern = re.compile(r'\b(?:extends|implements|new)\s+(\w+)', re.MULTILINE)

    declared_names = sg_decl_pattern.findall(code)
    used_names = sg_use_pattern.findall(code)

    if declared_names:
        unused = [name for name in declared_names if name not in used_names]
        if unused:
            print(f"DEBUG — SG triggered: {unused} declared but never subclassed, implemented, or instantiated.")
            return "SG"
        else:
            print("DEBUG — SG skipped: all abstract/interfaces are subclassed or instantiated.")



    # --- ClassDataShouldBePrivate (CDSBP) ---
    public_field_pattern = re.compile(r'^\s*public\s+(?!class|interface|static|void)\w+\s+\w+\s*;', re.MULTILINE)
    public_fields = public_field_pattern.findall(code)
    if "abstract" not in code and public_fields and wmc < 5 and noa >= 2:
        print(f"DEBUG — public fields detected: {public_fields}")
        return "ClassDataShouldBePrivate"
        # --- Middle Man (MM) ---
    method_blocks = re.findall(r"public\s+\w+\s+\w+\s*\([^)]*\)\s*\{([^}]*)\}", code)
    own_methods = re.findall(r"public\s+\w+\s+\w+\s*\(", code)

    delegated = 0
    for block in method_blocks:
        lines = [l.strip() for l in block.splitlines() if l.strip()]
        if len(lines) == 1 and re.search(r"\w+\.\w+\(", lines[0]):
            delegated += 1

    if own_methods:
        ratio = delegated / len(own_methods)
        if len(own_methods) >= 4 and ratio >= 0.75 and fan_out >= 2:
            return "MM"


