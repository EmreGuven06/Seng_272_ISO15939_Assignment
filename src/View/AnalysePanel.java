package View;

import model.QualityDimension;
import model.Session;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AnalysePanel extends JPanel
{
    private final JPanel    barsPanel = new JPanel();
    private final JPanel    gapPanel  = new JPanel();
    private final RadarChart radar    = new RadarChart();

    public AnalysePanel()
    {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Step 5: Analyse Results");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        add(title, BorderLayout.NORTH);


        JPanel center = new JPanel(new BorderLayout(20, 0));
        center.setBackground(Color.WHITE);
        center.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        barsPanel.setLayout(new BoxLayout(barsPanel, BoxLayout.Y_AXIS));
        barsPanel.setBackground(Color.WHITE);
        barsPanel.setBorder(titledBorder("Dimension Scores"));

        JPanel radarWrap = new JPanel(new BorderLayout());
        radarWrap.setBackground(Color.WHITE);
        radarWrap.setBorder(titledBorder("Radar Chart"));
        radarWrap.add(radar, BorderLayout.CENTER);
        radarWrap.setPreferredSize(new Dimension(340, 300));

        center.add(barsPanel, BorderLayout.CENTER);
        center.add(radarWrap, BorderLayout.EAST);

        add(new JScrollPane(center), BorderLayout.CENTER);


        gapPanel.setLayout(new BoxLayout(gapPanel, BoxLayout.Y_AXIS));
        gapPanel.setBackground(Color.WHITE);
        gapPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        add(gapPanel, BorderLayout.SOUTH);
    }

    public void loadFromSession(Session session)
    {
        barsPanel.removeAll();
        gapPanel.removeAll();
        if (session.getScenario() == null) return;

        List<QualityDimension> dims = session.getScenario().getDimensions();

        for (QualityDimension dim : dims)
        {
            barsPanel.add(buildBar(dim.getName(), dim.calculateScore()));
            barsPanel.add(Box.createVerticalStrut(8));
        }

        radar.setDimensions(dims);

        QualityDimension worst = findWorst(dims);
        if (worst != null) gapPanel.add(buildGapCard(worst));

        barsPanel.revalidate(); barsPanel.repaint();
        gapPanel.revalidate();  gapPanel.repaint();
    }

    private JPanel buildBar(String name, double score)
    {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(String.format("%-28s  %.2f / 5.00", name, score));
        lbl.setFont(new Font("Monospaced", Font.PLAIN, 12));
        lbl.setPreferredSize(new Dimension(260, 26));

        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue((int)(score / 5.0 * 100));
        bar.setForeground(scoreColor(score));
        bar.setBackground(new Color(230, 235, 245));
        bar.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230)));
        bar.setPreferredSize(new Dimension(0, 22));

        row.add(lbl, BorderLayout.WEST);
        row.add(bar, BorderLayout.CENTER);
        return row;
    }

    private JPanel buildGapCard(QualityDimension worst)
    {
        double score = worst.calculateScore();
        double gap   = 5.0 - score;
        String level = qualityLevel(score);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(255, 248, 230));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(240, 180, 60)),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        line(card, "⚠  Gap Analysis",
                new Font("SansSerif", Font.BOLD, 14), new Color(160, 80, 0));
        line(card, "Weakest dimension:  " + worst.getName(),
                new Font("SansSerif", Font.PLAIN, 13), Color.BLACK);
        line(card, "Score:  " + String.format("%.2f", score) + " / 5.00",
                new Font("SansSerif", Font.PLAIN, 13), Color.BLACK);
        line(card, "Gap:    " + String.format("%.2f", gap),
                new Font("SansSerif", Font.PLAIN, 13), Color.BLACK);
        line(card, "Quality level:  " + level,
                new Font("SansSerif", Font.BOLD, 13), levelColor(level));
        line(card, "This dimension has the lowest score and requires the most improvement.",
                new Font("SansSerif", Font.ITALIC, 12), new Color(120, 60, 0));
        return card;
    }

    private void line(JPanel p, String text, Font font, Color color)
    {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(color);
        lbl.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(lbl);
    }

    private QualityDimension findWorst(List<QualityDimension> dims)
    {
        QualityDimension worst = null;
        double min = Double.MAX_VALUE;
        for (QualityDimension d : dims)
        {
            double s = d.calculateScore();
            if (s < min)
            {
                min = s;
                worst = d;
            }

        }

        return worst;

    }

    private Color scoreColor(double s)
    {
        if (s >= 4.5) return new Color(0, 160, 0);
        if (s >= 3.5) return new Color(80, 160, 0);
        if (s >= 2.5) return new Color(200, 160, 0);
        if (s >= 1.5) return new Color(200, 80, 0);
        return new Color(200, 0, 0);
    }

    private String qualityLevel(double s)
    {
        if (s >= 4.5) return "Excellent";
        if (s >= 3.5) return "Good";
        if (s >= 2.5) return "Needs Improvement";
        return "Poor";
    }

    private Color levelColor(String level)
    {
        switch (level)
        {
            case "Excellent": return new Color(0, 130, 0);
            case "Good":      return new Color(80, 150, 0);
            case "Needs Improvement": return new Color(200, 120, 0);
            default:          return new Color(180, 0, 0);
        }

    }

    private javax.swing.border.Border titledBorder(String title)
    {
        return BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 210, 230)),
                " " + title + " ", 0, 0,
                new Font("SansSerif", Font.BOLD, 12), new Color(60, 80, 140));
    }


}
