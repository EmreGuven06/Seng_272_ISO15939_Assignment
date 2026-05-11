
ISO/IEC 15939 – Measurement Process Simulator


Student Name : Emre Güven
Student ID   : 202328029


COMPILATION & RUN

1. Open a terminal in the project root folder

2. Create the output directory:
   mkdir -p out

3. Compile:
   javac -d out -sourcepath src src/Main.java

4. Run:
   java -cp out Main

Requirements: Java SE 17 or higher


PROJECT STRUCTURE

src/
  Main.java                  - Main window and controller
  model/
    Metric.java              - Single metric and score calculation
    QualityDimension.java    - Dimension (group of metrics)
    Scenario.java            - Scenario
    Session.java             - Shared data between steps
  data/
    ScenarioRepository.java  - All hard-coded scenario data
  view/
    StepIndicator.java       - Top step indicator
    ProfilePanel.java        - Step 1: user information
    DefinePanel.java         - Step 2: quality type / mode / scenario
    PlanPanel.java           - Step 3: metric table (read-only)
    CollectPanel.java        - Step 4: raw values and scores
    AnalysePanel.java        - Step 5: analysis results
    RadarChart.java          - Bonus: radar chart


FEATURES

- 5-step wizard interface using CardLayout
- Step indicator with checkmark on completed steps
- Profile validation (empty field check)
- Single selection enforced via radio buttons (ButtonGroup)
- Automatic score calculation (1-5 range, 0.5 precision)
- Dimension-based weighted average calculation
- Gap analysis (identifies weakest dimension)
- Radar chart drawn with Graphics2D (Bonus)
- MVC architecture (model / view / controller separation)
- No external libraries used (standard Java SE only)
- Collections used: ArrayList, HashMap


SCORE CALCULATION

Higher is better: score = 1 + (value - min) / (max - min) x 4
Lower is better:  score = 5 - (value - min) / (max - min) x 4
Result is clamped to [1.0, 5.0] and rounded to nearest 0.5



NOTES

- All scenario data is hard-coded in ScenarioRepository.java
- No external libraries were used
- Application runs with standard Java SE library only


