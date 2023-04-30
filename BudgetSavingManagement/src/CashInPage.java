import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;

import java.awt.Font;
import java.util.Calendar;
import java.util.Properties;
import org.jdatepicker.impl.*;
import java.text.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

import java.util.Date;



public class CashInPage extends JFrame implements ActionListener {

private JTextField remarkField,categoryField;
private JFormattedTextField amountField;
private JDatePickerImpl datePicker ;
String monthName;
String username;

public void setDefaultCloseOperation() {
    setVisible(false);
}

public CashInPage(String username, String monthName) {
    this.monthName = monthName;
    this.username = username;

    setTitle("CashIn");
    setBounds(100, 100, 960, 600);
    getContentPane().setLayout(null);
    setResizable(false);
    // Create the heading label and add it to the frame
    JLabel headingLabel = new JLabel("Cash In");
    headingLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
    headingLabel.setBounds(10, 10, 200, 30);
    getContentPane().add(headingLabel);

    // Create the "Remark" label and text field
    JLabel remarkLabel = new JLabel("Remark:");
    remarkLabel.setBounds(10, 95, 100, 30);
    getContentPane().add(remarkLabel);
    this.remarkField = new JTextField();
    remarkField.setBounds(120, 95, 200, 30);
    getContentPane().add(remarkField);

    // Create the "Amount" label and integer input field
    JLabel amountLabel = new JLabel("Amount:");
    amountLabel.setBounds(10, 135, 100, 30);
    getContentPane().add(amountLabel);
    this.amountField = new JFormattedTextField(java.text.NumberFormat.getIntegerInstance());
    amountField.setBounds(120, 135, 200, 30);
    getContentPane().add(amountField);

    // Create the "Category" label and text field
    JLabel categoryLabel = new JLabel("Category:");
    categoryLabel.setBounds(10, 175, 100, 30);
    getContentPane().add(categoryLabel);
    this.categoryField = new JTextField();
    categoryField.setBounds(120, 175, 200, 30);
    getContentPane().add(categoryField);

    // Create the "CashOut" and "Reset" buttons
    JButton cashInButton = new JButton("Cash In");
    cashInButton.setBounds(10, 213, 100, 30);
    cashInButton.addActionListener(this); // register the button for action events
    getContentPane().add(cashInButton);

    // Create the "Date" label and date picker
    JLabel dateLabel = new JLabel("Date:");
    dateLabel.setBounds(10, 55, 100, 30);
    getContentPane().add(dateLabel);
    UtilDateModel model = new UtilDateModel();
    JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
    this.datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    datePicker.setBounds(120, 55, 200, 30);
    getContentPane().add(datePicker);

    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation();
}

//    public static void main(String[] args) {
//        CashInPage cashInPage = new CashInPage(username,monthName);
//    }

    // DateLabelFormatter class to format the date in date picker
    public class DateLabelFormatter extends AbstractFormatter {
        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
    public void actionPerformed(ActionEvent e) {
        // Get the values from the input fields
        String remark = remarkField.getText();
        String category = categoryField.getText();
        
        
        
        String input = amountField.getText();
        input = input.replaceAll(",", ""); // Remove commas from input
        int amount = Integer.parseInt(input);

        // Get the selected date from the date picker
        Date selectedDate = (Date) datePicker.getModel().getValue();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(selectedDate);
        
       
        try {
            // Create a connection to the database
        	 Connection conn = DBConnection.getConnection();
            // Build the SQL query to insert the data
            String tableName = username + "_" + monthName;
            String query = "INSERT INTO " + tableName + " (Date, Remark, Category, CashIn, CashOut) VALUES (?, ?, ?, ?, ?)";

            // Create a prepared statement to execute the query
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, date);
            pstmt.setString(2, remark);
            pstmt.setString(3, category);
            pstmt.setInt(4, amount);
            pstmt.setInt(5, 0);

            // Execute the query and close the connection
            pstmt.executeUpdate();
            conn.close();

            // Display a message box to indicate success
            JOptionPane.showMessageDialog(null, "Data saved successfully!");

        } catch (Exception ex) {
            // Display an error message if there is a problem with the database
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

}