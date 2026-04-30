package data;

import model.QualityDimension;
import model.Metric;
import model.Scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScenarioRepository
{
    private final Map<String, List<Scenario>> scenarioMap = new HashMap<>();

    public ScenarioRepository()
    {
        buildEducationScenarios();
        buildHealthScenarios();
    }

    private void buildEducationScenarios() {
        List<Scenario> list = new ArrayList<>();


        Scenario scenC = new Scenario("Scenario C – Team Alpha");

        QualityDimension usability = new QualityDimension("Usability", 25);
        usability.addMetric(new Metric("SUS Score",        50, true,  0,   100, "points", 89));
        usability.addMetric(new Metric("Onboarding Time",  50, false, 0,   60,  "min",     5));

        QualityDimension perfEff = new QualityDimension("Performance Efficiency", 20);
        perfEff.addMetric(new Metric("Video Start Time",   50, false, 0,   15,  "sec",     2));
        perfEff.addMetric(new Metric("Concurrent Exams",   50, true,  0,   600, "users", 450));

        QualityDimension accessibility = new QualityDimension("Accessibility", 20);
        accessibility.addMetric(new Metric("WCAG Compliance",     50, true, 0, 100, "%", 88));
        accessibility.addMetric(new Metric("Screen Reader Score",  50, true, 0, 100, "%", 76));

        QualityDimension reliability = new QualityDimension("Reliability", 20);
        reliability.addMetric(new Metric("Uptime", 50, true,  95, 100, "%",   99.5));
        reliability.addMetric(new Metric("MTTR",   50, false, 0,  120, "min",   18));

        QualityDimension funcSuit = new QualityDimension("Functional Suitability", 15);
        funcSuit.addMetric(new Metric("Feature Completion",     50, true, 0, 100, "%", 95));
        funcSuit.addMetric(new Metric("Assignment Submit Rate", 50, true, 0, 100, "%", 82));

        scenC.addDimension(usability);
        scenC.addDimension(perfEff);
        scenC.addDimension(accessibility);
        scenC.addDimension(reliability);
        scenC.addDimension(funcSuit);
        list.add(scenC);


        Scenario scenD = new Scenario("Scenario D – Team Beta");

        QualityDimension usabilityD = new QualityDimension("Usability", 30);
        usabilityD.addMetric(new Metric("SUS Score",       50, true,  0,  100, "points", 72));
        usabilityD.addMetric(new Metric("Onboarding Time", 50, false, 0,  60,  "min",    20));

        QualityDimension perfEffD = new QualityDimension("Performance Efficiency", 25);
        perfEffD.addMetric(new Metric("Video Start Time",  50, false, 0,  15,  "sec",     7));
        perfEffD.addMetric(new Metric("Concurrent Exams",  50, true,  0,  600, "users", 300));

        QualityDimension reliabilityD = new QualityDimension("Reliability", 25);
        reliabilityD.addMetric(new Metric("Uptime", 50, true,  95, 100, "%",   97.8));
        reliabilityD.addMetric(new Metric("MTTR",   50, false, 0,  120, "min",   45));

        QualityDimension funcSuitD = new QualityDimension("Functional Suitability", 20);
        funcSuitD.addMetric(new Metric("Feature Completion",     50, true, 0, 100, "%", 78));
        funcSuitD.addMetric(new Metric("Assignment Submit Rate", 50, true, 0, 100, "%", 65));

        scenD.addDimension(usabilityD);
        scenD.addDimension(perfEffD);
        scenD.addDimension(reliabilityD);
        scenD.addDimension(funcSuitD);
        list.add(scenD);

        scenarioMap.put("Education", list);
    }

    private void buildHealthScenarios()
    {
        List<Scenario> list = new ArrayList<>();


        Scenario scenA = new Scenario("Scenario A – Hospital A");

        QualityDimension security = new QualityDimension("Security", 30);
        security.addMetric(new Metric("Auth Success Rate",     50, true,  0, 100, "%",      98));
        security.addMetric(new Metric("Data Breach Incidents", 50, false, 0,  10, "count",   0));

        QualityDimension usabilityH = new QualityDimension("Usability", 25);
        usabilityH.addMetric(new Metric("SUS Score",       50, true,  0, 100, "points", 80));
        usabilityH.addMetric(new Metric("Task Error Rate", 50, false, 0,  50, "%",       8));

        QualityDimension reliabilityH = new QualityDimension("Reliability", 25);
        reliabilityH.addMetric(new Metric("Uptime",        50, true,  95, 100, "%",   99.2));
        reliabilityH.addMetric(new Metric("Response Time", 50, false, 0,   5,  "sec",  0.8));

        QualityDimension maintainability = new QualityDimension("Maintainability", 20);
        maintainability.addMetric(new Metric("Code Coverage", 50, true,  0,  100, "%",   74));
        maintainability.addMetric(new Metric("Bug Fix Time",  50, false, 0,   72, "hrs",  6));

        scenA.addDimension(security);
        scenA.addDimension(usabilityH);
        scenA.addDimension(reliabilityH);
        scenA.addDimension(maintainability);
        list.add(scenA);


        Scenario scenB = new Scenario("Scenario B – Clinic B");

        QualityDimension securityB = new QualityDimension("Security", 35);
        securityB.addMetric(new Metric("Auth Success Rate",     50, true,  0, 100, "%",      95));
        securityB.addMetric(new Metric("Data Breach Incidents", 50, false, 0,  10, "count",   2));

        QualityDimension perfEffB = new QualityDimension("Performance Efficiency", 30);
        perfEffB.addMetric(new Metric("Page Load Time", 50, false, 0, 10, "sec", 3));
        perfEffB.addMetric(new Metric("API Response",   50, false, 0,  5, "sec", 1.2));

        QualityDimension reliabilityB = new QualityDimension("Reliability", 35);
        reliabilityB.addMetric(new Metric("Uptime",     50, true,  95, 100, "%",  96.5));
        reliabilityB.addMetric(new Metric("Error Rate", 50, false, 0,   10, "%",   3.0));

        scenB.addDimension(securityB);
        scenB.addDimension(perfEffB);
        scenB.addDimension(reliabilityB);
        list.add(scenB);

        scenarioMap.put("Health", list);
    }

    public List<String> getScenarioNames(String mode)
    {
        List<String> names = new ArrayList<>();
        for (Scenario s : scenarioMap.getOrDefault(mode, new ArrayList<>()))
        {
            names.add(s.getName());
        }
        return names;
    }

    public Scenario getScenario(String mode, String name)
    {
        for (Scenario s : scenarioMap.getOrDefault(mode, new ArrayList<>()))
        {
            if (s.getName().equals(name)) return s;
        }
        return null;
    }

    public List<String> getModes()
    {
        return new ArrayList<>(scenarioMap.keySet());
    }


}
