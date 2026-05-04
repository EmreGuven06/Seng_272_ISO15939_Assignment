package View;

import data.ScenarioRepository;
import model.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class DefinePanel extends JPanel
{
    private final JRadioButton rbProduct   = new JRadioButton("Product Quality");
    private final JRadioButton rbProcess   = new JRadioButton("Process Quality");
    private final JRadioButton rbHealth    = new JRadioButton("Health");
    private final JRadioButton rbEducation = new JRadioButton("Education");

    private final ButtonGroup scenarioGroup = new ButtonGroup();
    private final JPanel      scenarioPanel = new JPanel();

    private final ScenarioRepository repository;

    public DefinePanel(ScenarioRepository repository)
    {
        this.repository = repository;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Step 2: Define Quality Dimensions");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        add(title, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(5, 40, 10, 40));

        content.add(buildQualityTypeSection());
        content.add(Box.createVerticalStrut(16));
        content.add(buildModeSection());
        content.add(Box.createVerticalStrut(16));
        content.add(buildScenarioSection());

        add(new JScrollPane(content), BorderLayout.CENTER);


        rbProduct.setSelected(true);
        rbHealth.setSelected(true);
        refreshScenarios();


        ActionListener modeChange = e -> refreshScenarios();
        rbHealth.addActionListener(modeChange);
        rbEducation.addActionListener(modeChange);
    }

    private JPanel buildQualityTypeSection()
    {
        JPanel panel = section("Quality Type");
        ButtonGroup g = new ButtonGroup();
        g.add(rbProduct); g.add(rbProcess);
        style(rbProduct, "Product Quality — software characteristics (performance, security, usability)");
        style(rbProcess, "Process Quality — development process characteristics");
        panel.add(rbProduct);
        panel.add(rbProcess);
        return panel;
    }

    private JPanel buildModeSection()
    {
        JPanel panel = section("Mode");
        ButtonGroup g = new ButtonGroup();
        g.add(rbHealth); g.add(rbEducation);
        style(rbHealth,    "Health — health management system scenarios");
        style(rbEducation, "Education — LMS system scenarios");
        panel.add(rbHealth);
        panel.add(rbEducation);
        return panel;
    }
    private JPanel buildScenarioSection()
    {
        JPanel wrapper = section("Scenario");
        scenarioPanel.setLayout(new BoxLayout(scenarioPanel, BoxLayout.Y_AXIS));
        scenarioPanel.setBackground(Color.WHITE);
        wrapper.add(scenarioPanel);
        return wrapper;
    }

    private void refreshScenarios()
    {

        java.util.Enumeration<AbstractButton> old = scenarioGroup.getElements();
        java.util.List<AbstractButton> toRemove = new java.util.ArrayList<>();
        while (old.hasMoreElements()) toRemove.add(old.nextElement());
        for (AbstractButton b : toRemove) scenarioGroup.remove(b);

        scenarioPanel.removeAll();
        String mode = rbHealth.isSelected() ? "Health" : "Education";
        List<String> names = repository.getScenarioNames(mode);

        boolean first = true;
        for (String name : names)
        {
            JRadioButton rb = new JRadioButton(name);
            style(rb, name);
            scenarioGroup.add(rb);
            scenarioPanel.add(rb);
            if (first) { rb.setSelected(true); first = false; }
        }
        scenarioPanel.revalidate();
        scenarioPanel.repaint();
    }

    public boolean validateAndSave(Session session)
    {
        String scenName = selectedScenario();
        if (scenName == null)
        {
            JOptionPane.showMessageDialog(this, "Please select a scenario to continue.",
                    "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        session.setQualityType(rbProduct.isSelected() ? "Product" : "Process");
        session.setMode(rbHealth.isSelected() ? "Health" : "Education");
        session.setScenario(repository.getScenario(session.getMode(), scenName));
        return true;
    }

    private String selectedScenario()
    {
        java.util.Enumeration<AbstractButton> en = scenarioGroup.getElements();
        while (en.hasMoreElements())
        {
            AbstractButton b = en.nextElement();
            if (b.isSelected()) return b.getText();
        }
        return null;
    }

    private JPanel section(String title)
    {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder (BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(200, 210, 230)),
                        " " + title + " ", 0, 0,
                        new Font("SansSerif", Font.BOLD, 13), new Color(60, 80, 140)),
                BorderFactory.createEmptyBorder(4, 10, 8, 10)));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        return p;
    }

    private void style(JRadioButton rb, String text)
    {
        rb.setText(text);
        rb.setBackground(Color.WHITE);
        rb.setFont(new Font("SansSerif", Font.PLAIN, 13));
        rb.setAlignmentX(Component.LEFT_ALIGNMENT);
        rb.setBorder(BorderFactory.createEmptyBorder(3, 4, 3, 4));
    }


}
