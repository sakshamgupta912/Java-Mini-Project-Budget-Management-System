import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.awt.event.ActionListener;

public class CashOutPage extends JFrame {
	
	private JTextField remarkField,categoryField;
	private JFormattedTextField amountField;
	private JDatePickerImpl datePicker ;
	String monthName;
	String username;
	
	public void setDefaultCloseOperation() {
        setVisible(false);
        }
    public CashOutPage(String username,String monthName) {
        setTitle("CashOut");
        setBounds(100, 100, 960, 600);
        getContentPane().setLayout(null);
        setResizable(false);
        // Create the heading label and add it to the frame
        JLabel headingLabel = new JLabel("Cash Out");
        headingLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        headingLabel.setBounds(10, 10, 200, 30);
        getContentPane().add(headingLabel);

        // Create the "Remark" label and text field
        JLabel remarkLabel = new JLabel("Remark:");
        remarkLabel.setBounds(10, 89, 100, 30);
        getContentPane().add(remarkLabel);
        this.remarkField = new JTextField();
        remarkField.setBounds(120, 90, 200, 30);
        getContentPane().add(remarkField);

        // Create the "Amount" label and integer input field
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(10, 129, 100, 30);
        getContentPane().add(amountLabel);
        this.amountField = new JFormattedTextField(java.text.NumberFormat.getIntegerInstance());
        amountField.setBounds(120, 130, 200, 30);
        getContentPane().add(amountField);

        // Create the "Category" label and text field
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(10, 169, 100, 30);
        getContentPane().add(categoryLabel);
        this.categoryField = new JTextField();
        categoryField.setBounds(120, 170, 200, 30);
        getContentPane().add(categoryField);

        // Create the "CashOut" and "Reset" buttons
        JButton cashOutButton = new JButton("CashOut");
        cashOutButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
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
                    pstmt.setInt(4, 0);
                    pstmt.setInt(5, amount);

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
        });
        cashOutButton.setBounds(10, 210, 100, 30);
        getContentPane().add(cashOutButton);
        

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

        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation();
    }

//    public static void main(String[] args) {
//        CashInPage cashInPage = new CashInPage();
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
 
}
