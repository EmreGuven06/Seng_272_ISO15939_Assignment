import data.ScenarioRepository;
import model.Session;
import View.*;

import javax.swing.*;
import java.awt.*;



public class Main extends JFrame
{

    private static final int PROFILE = 0, DEFINE = 1, PLAN = 2, COLLECT = 3, ANALYSE = 4;
    private static final String[] CARDS = {"profile", "define", "plan", "collect", "analyse"};

    private final Session  session    = new Session();
    private final ScenarioRepository repository = new ScenarioRepository();

    private final StepIndicator stepIndicator = new StepIndicator();
    private final ProfilePanel profilePanel  = new ProfilePanel();
    private final DefinePanel definePanel   = new DefinePanel(repository);
    private final PlanPanel planPanel     = new PlanPanel();
    private final CollectPanel collectPanel  = new CollectPanel();
    private final AnalysePanel analysePanel  = new AnalysePanel();

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel  = new JPanel(cardLayout);

    private final JButton backBtn = new JButton("← Back");
    private final JButton nextBtn = new JButton("Next →");

    private int currentStep = PROFILE;

    public Main()
    {
        super("ISO/IEC 15939 – Measurement Process Simulator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(860, 640);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(stepIndicator, BorderLayout.NORTH);

        cardPanel.add(profilePanel, CARDS[PROFILE]);
        cardPanel.add(definePanel,  CARDS[DEFINE]);
        cardPanel.add(planPanel,    CARDS[PLAN]);
        cardPanel.add(collectPanel, CARDS[COLLECT]);
        cardPanel.add(analysePanel, CARDS[ANALYSE]);
        add(cardPanel, BorderLayout.CENTER);

        add(buildNavBar(), BorderLayout.SOUTH);

        showStep(PROFILE);
    }

    private void showStep(int step)
    {
        currentStep = step;
        cardLayout.show(cardPanel, CARDS[step]);
        stepIndicator.setCurrentStep(step);

        if (step == PLAN)    planPanel.loadFromSession(session);
        if (step == COLLECT) collectPanel.loadFromSession(session);
        if (step == ANALYSE) analysePanel.loadFromSession(session);

        backBtn.setEnabled(step > PROFILE);
        nextBtn.setText(step == ANALYSE ? "Finish" : "Next →");
    }

    private void onNext()
    {
        if (currentStep == PROFILE && !profilePanel.validateAndSave(session)) return;
        if (currentStep == DEFINE  && !definePanel.validateAndSave(session))  return;

        if (currentStep == ANALYSE)
        {
            JOptionPane.showMessageDialog(this,
                    "Analysis complete!\nThank you, " + session.getUsername() + ".",
                    "Finished", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        showStep(currentStep + 1);
    }

    private void onBack()
    {
        if (currentStep > PROFILE) showStep(currentStep - 1);
    }

    private JPanel buildNavBar()
    {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 10));
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(210, 215, 225)));
        bar.setBackground(new Color(248, 249, 252));

        styleBtn(backBtn, false);
        styleBtn(nextBtn, true);
        backBtn.addActionListener(e -> onBack());
        nextBtn.addActionListener(e -> onNext());

        bar.add(backBtn);
        bar.add(nextBtn);

        return bar;
    }

    private void styleBtn(JButton btn, boolean primary)
    {
        btn.setPreferredSize(new Dimension(110, 36));
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (primary)
        {
            btn.setBackground(new Color(40, 100, 200));
            btn.setForeground(Color.WHITE);
        }
        else
        {
            btn.setBackground(new Color(220, 225, 235));
            btn.setForeground(new Color(60, 70, 90));
        }

    }

    public static void main(String[] args)
    {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }


}