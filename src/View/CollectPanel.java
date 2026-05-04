package View;

import model.QualityDimension;
import model.Metric;
import model.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CollectPanel extends JPanel
{
    private final JPanel contentArea = new JPanel();

    public CollectPanel()
    {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Step 4: Collect Data");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        add(title, BorderLayout.NORTH);

        contentArea.setLayout(new BoxLayout(contentArea, BoxLayout.Y_AXIS));
        contentArea.setBackground(Color.WHITE);
        add(new JScrollPane(contentArea), BorderLayout.CENTER);
    }

    public void loadFromSession(Session session)
    {
        contentArea.removeAll();
        if (session.getScenario() == null) return;

        for (QualityDimension dim : session.getScenario().getDimensions())
        {
            contentArea.add(buildBlock(dim));
            contentArea.add(Box.createVerticalStrut(12));
        }
        contentArea.revalidate();
        contentArea.repaint();
    }

    private JPanel buildBlock(QualityDimension dim)
    {
        JPanel block = new JPanel(new BorderLayout());
        block.setBackground(Color.WHITE);
        block.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 20));
        block.setAlignmentX(Component.LEFT_ALIGNMENT);
        block.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JLabel header = new JLabel(dim.getName() + "   (Coefficient: " + dim.getCoefficient() + ")");
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setForeground(new Color(30, 80, 160));
        header.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        block.add(header, BorderLayout.NORTH);

        String[] cols = {"Metric", "Direction", "Range", "Value", "Score (1–5)", "Coeff / Unit"};
        DefaultTableModel model = new DefaultTableModel(cols, 0)
        {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Metric m : dim.getMetrics())
        {
            model.addRow(new Object[]{m.getName(), m.getDirectionLabel(),
                    m.getRangeLabel(), m.getValue(), m.calculateScore(),
                    m.getCoefficient() + " / " + m.getUnit()});
        }

        JTable table = new JTable(model);
        styleTable(table);

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.add(table.getTableHeader(), BorderLayout.NORTH);
        wrap.add(table, BorderLayout.CENTER);
        wrap.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230)));
        block.add(wrap, BorderLayout.SOUTH);
        return block;
    }

    private void styleTable(JTable t)
    {
        t.setFont(new Font("SansSerif", Font.PLAIN, 13));
        t.setRowHeight(26);
        t.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        t.getTableHeader().setBackground(new Color(235, 240, 250));
        t.setGridColor(new Color(220, 225, 235));
        t.setShowGrid(true);
        t.setDefaultRenderer(Object.class, new TableCellRenderer()

        {
            public Component getTableCellRendererComponent(JTable tbl, Object val,
                                                           boolean sel, boolean focus, int row, int col)
            {
                JLabel cell = new JLabel(val == null ? "" : val.toString());
                cell.setFont(t.getFont());
                cell.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                cell.setOpaque(true);

                if (col == 4 && val instanceof Double)
                {
                    double score = (Double) val;
                    cell.setFont(t.getFont().deriveFont(Font.BOLD));
                    cell.setForeground(score >= 4.5 ? new Color(0, 130, 0)
                            : score >= 3.0 ? new Color(180, 120, 0)
                            : new Color(180, 0, 0));
                }

                else
                {
                    cell.setForeground(Color.BLACK);
                }

                cell.setBackground(sel ? tbl.getSelectionBackground()
                        : row % 2 == 0 ? Color.WHITE : new Color(248, 250, 255));
                return cell;
            }


        });

    }


}
