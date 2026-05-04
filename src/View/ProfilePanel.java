package View;

import model.Session;

import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel
{
    private final JTextField usernameField    = new JTextField(20);
    private final JTextField schoolField      = new JTextField(20);
    private final JTextField sessionNameField = new JTextField(20);


    public ProfilePanel()
    {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Step 1: Profile Information");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 8, 10, 8);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill   = GridBagConstraints.HORIZONTAL;

        addRow(form, gc, 0, "Username:",     usernameField);
        addRow(form, gc, 1, "School:",        schoolField);
        addRow(form, gc, 2, "Session Name:",  sessionNameField);

        add(form, BorderLayout.CENTER);
    }

    private void addRow(JPanel form, GridBagConstraints gc, int row, String text, JTextField field)
    {
        gc.gridx = 0; gc.gridy = row; gc.weightx = 0;
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl.setPreferredSize(new Dimension(130, 28));
        form.add(lbl, gc);

        gc.gridx = 1; gc.weightx = 1;
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        form.add(field, gc);
    }

    public boolean validateAndSave(Session session)
    {
        if (usernameField.getText().trim().isEmpty())
        {
            warn("Please enter your username to continue.");
            usernameField.requestFocus();
            return false;
        }

        if (schoolField.getText().trim().isEmpty())
        {
            warn("Please enter your school name to continue.");
            schoolField.requestFocus();
            return false;
        }

        if (sessionNameField.getText().trim().isEmpty())
        {
            warn("Please enter a session name to continue.");
            sessionNameField.requestFocus();
            return false;
        }


        session.setUsername(usernameField.getText().trim());
        session.setSchool(schoolField.getText().trim());
        session.setSessionName(sessionNameField.getText().trim());
        return true;

    }

    private void warn(String msg)
    {
        JOptionPane.showMessageDialog(this, msg, "Missing Information", JOptionPane.WARNING_MESSAGE);
    }



}
