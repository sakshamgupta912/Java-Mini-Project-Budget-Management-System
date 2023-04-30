package inApp;
import javax.swing.*;

import amountInput.*;
import authentication.*;

import authentication.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EditRecordForm extends JDialog {
    private JTextField dateField;
    private JTextField remarkField;
    private JComboBox<String> categoryComboBox;
    private JTextField cashInField;
    private JTextField cashOutField;
    private JButton updateButton;
    private JButton cancelButton;
    private int recordId;
    private DefaultComboBoxModel<String> categoryModel;

    public EditRecordForm(JFrame parent, String username, String monthName, int recordId) {
        super(parent, "Edit Record", true);
        this.recordId = recordId;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // Initialize components
        dateField = new JTextField(10);
        remarkField = new JTextField(10);
        categoryModel = new DefaultComboBoxModel<>();
        categoryComboBox = new JComboBox<>(categoryModel);
        cashInField = new JTextField(10);
        cashOutField = new JTextField(10);
        updateButton = new JButton("Update");
        cancelButton = new JButton("Cancel");

        // Add components to form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Date:"), c);
        c.gridy++;
        formPanel.add(new JLabel("Remark:"), c);
        c.gridy++;
        formPanel.add(new JLabel("Category:"), c);
        c.gridy++;
        formPanel.add(new JLabel("Cash In:"), c);
        c.gridy++;
        formPanel.add(new JLabel("Cash Out:"), c);

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        formPanel.add(dateField, c);
        c.gridy++;
        formPanel.add(remarkField, c);
        c.gridy++;
        formPanel.add(categoryComboBox, c);
        c.gridy++;
        formPanel.add(cashInField, c);
        c.gridy++;
        formPanel.add(cashOutField, c);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load record data
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM " + username + "_" + monthName + " WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, recordId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                dateField.setText(rs.getString("date"));
                remarkField.setText(rs.getString("remark"));
                cashInField.setText(rs.getString("cashIn"));
                cashOutField.setText(rs.getString("cashOut"));

                // Load categories from database
                Statement stmt = conn.createStatement();
                query = "SELECT DISTINCT category FROM " + username + "_" + monthName;
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    categoryModel.addElement(rs.getString("category"));
                }

                // Select the category of the current record in the combo box
                String currentCategory = rs.getString("category");
                for (int i = 0; i < categoryModel.getSize(); i++) {
                    if (categoryModel.getElementAt(i).equals(currentCategory)) {
                        categoryComboBox.setSelectedIndex(i);
                        break;
                    }
                }

                stmt.close();
            }
            rs.close();
            pstmt.close();
            conn.close();
        }
        catch (Exception ex) {
            // Display an error message if there is a problem with the database
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }
}
       
