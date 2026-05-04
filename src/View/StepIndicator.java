package View;

import javax.swing.*;
import java.awt.*;

public class StepIndicator extends JPanel
{
    private static final String[] STEP_NAMES = {"Profile", "Define", "Plan", "Collect", "Analyse"};

    private static final Color COLOR_DONE    = new Color(34, 139, 34);
    private static final Color COLOR_ACTIVE  = new Color(30, 100, 200);
    private static final Color COLOR_PENDING = new Color(150, 150, 150);

    private int currentStep = 0;

    public StepIndicator()
    {
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(210, 215, 225)));
        setPreferredSize(new Dimension(0, 70));
    }


    public void setCurrentStep(int step)
    {
        this.currentStep = step;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int n          = STEP_NAMES.length;
        int stepWidth  = getWidth() / n;
        int circleSize = 28;
        int cy         = getHeight() / 2 - 8;

        for (int i = 0; i < n; i++)
        {
            int cx = stepWidth * i + stepWidth / 2;


            if (i < n - 1)
            {
                g2.setColor(i < currentStep ? COLOR_DONE : COLOR_PENDING);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(cx + circleSize / 2, cy, cx + stepWidth - circleSize / 2, cy);
            }


            Color circleColor = (i < currentStep) ? COLOR_DONE
                    : (i == currentStep) ? COLOR_ACTIVE
                    : COLOR_PENDING;
            g2.setColor(circleColor);
            g2.fillOval(cx - circleSize / 2, cy - circleSize / 2, circleSize, circleSize);


            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 13));
            String label = (i < currentStep) ? "✓" : String.valueOf(i + 1);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(label, cx - fm.stringWidth(label) / 2, cy + fm.getAscent() / 2 - 1);


            g2.setColor(circleColor);
            g2.setFont(new Font("SansSerif", i == currentStep ? Font.BOLD : Font.PLAIN, 11));
            FontMetrics fm2 = g2.getFontMetrics();
            g2.drawString(STEP_NAMES[i], cx - fm2.stringWidth(STEP_NAMES[i]) / 2, cy + circleSize / 2 + 16);

        }


    }



}
