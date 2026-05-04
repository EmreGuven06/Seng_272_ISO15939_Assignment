package View;

import model.QualityDimension;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.List;

public class RadarChart extends JPanel
{
    private List<QualityDimension> dimensions;

    public RadarChart()
    {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(320, 280));
    }

    public void setDimensions(List<QualityDimension> dims)
    {
        this.dimensions = dims;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (dimensions == null || dimensions.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int radius = Math.min(cx, cy) - 50;
        int n = dimensions.size();


        for (int level = 1; level <= 5; level++)
        {
            double r = radius * level / 5.0;
            g2.setColor(new Color(220, 225, 240));
            g2.setStroke(new BasicStroke(1f));
            g2.draw(polygon(cx, cy, r, n));
            g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
            g2.setColor(new Color(160, 165, 180));
            g2.drawString(String.valueOf(level), cx + 3, (int)(cy - r) + 4);
        }


        g2.setColor(new Color(180, 190, 210));
        for (int i = 0; i < n; i++)
        {
            double angle = -Math.PI / 2 + 2 * Math.PI * i / n;
            g2.drawLine(cx, cy,
                    (int)(cx + radius * Math.cos(angle)),
                    (int)(cy + radius * Math.sin(angle)));
        }


        Path2D data = new Path2D.Double();
        for (int i = 0; i < n; i++)
        {
            double r     = radius * dimensions.get(i).calculateScore() / 5.0;
            double angle = -Math.PI / 2 + 2 * Math.PI * i / n;
            double x = cx + r * Math.cos(angle);
            double y = cy + r * Math.sin(angle);
            if (i == 0) data.moveTo(x, y); else data.lineTo(x, y);
        }

        data.closePath();
        g2.setColor(new Color(60, 120, 220, 60));
        g2.fill(data);
        g2.setColor(new Color(40, 90, 200));
        g2.setStroke(new BasicStroke(2f));
        g2.draw(data);


        for (int i = 0; i < n; i++)
        {
            double r     = radius * dimensions.get(i).calculateScore() / 5.0;
            double angle = -Math.PI / 2 + 2 * Math.PI * i / n;
            int px = (int)(cx + r * Math.cos(angle));
            int py = (int)(cy + r * Math.sin(angle));
            g2.setColor(new Color(40, 90, 200));
            g2.fillOval(px - 4, py - 4, 8, 8);
        }


        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        g2.setColor(new Color(40, 50, 80));
        for (int i = 0; i < n; i++)
        {
            double angle = -Math.PI / 2 + 2 * Math.PI * i / n;
            double score = dimensions.get(i).calculateScore();
            String label = shortName(dimensions.get(i).getName())
                    + " (" + String.format("%.1f", score) + ")";
            int lx = (int)(cx + (radius + 22) * Math.cos(angle));
            int ly = (int)(cy + (radius + 22) * Math.sin(angle));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(label, lx - fm.stringWidth(label) / 2, ly + 4);
        }

    }
    private Path2D polygon(int cx, int cy, double r, int n)
    {
        Path2D p = new Path2D.Double();

        for (int i = 0; i < n; i++)
        {
            double angle = -Math.PI / 2 + 2 * Math.PI * i / n;
            double x = cx + r * Math.cos(angle);
            double y = cy + r * Math.sin(angle);
            if (i == 0) p.moveTo(x, y); else p.lineTo(x, y);
        }

        p.closePath();
        return p;
    }

    private String shortName(String name)
    {
        if (name.length() <= 12) return name;
        int sp = name.indexOf(' ');
        return sp > 0 ? name.substring(0, sp) : name.substring(0, 10) + "…";
    }

}
