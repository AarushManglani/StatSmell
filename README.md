## Credits
This project is inspired by the research paper  
E. Hamouda, A. El-Korany and S. Makady, "Smell-ML: A Machine Learning Framework for Detecting Rarely Studied Code Smells," 
in IEEE Access, vol. 13, pp. 12966-12980, 2025, doi: 10.1109/ACCESS.2025.3530927.

## Project Architecture

The system consists of three main components: code parsing, machine learning prediction, and a backend API.

1. **Code Input**
   - The user uploads Java source code files to the system.

2. **Metric Extraction**
   - Java source files are analyzed using **JavaParser**.
   - Structural metrics such as method counts, complexity indicators, and class features are extracted.

3. **Feature Processing**
   - Extracted metrics are converted into a structured format suitable for machine learning models.

4. **Heuristic and Machine Learning Analysis**
   - A set of heuristics are used to identify the code smell before passing it onto the ML model.
   - A trained model (`smell_model.pkl`) predicts whether the code exhibits potential code smells.

6. **Backend API**
   - Implemented using **Flask**.
   - Handles file uploads, runs the analysis pipeline, and returns prediction results.

7. **Output**
   - The system outputs predictions indicating potential code smells detected in the input code.
