package inApp;
import amountInput.*;
import authentication.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class MonthBudgetForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String String = null;
	protected JLabel titleLabel;
	private JTable dataTable;
	private DefaultTableModel tableModel;
	private JTextField totalInField, totalOutField, budgetField;
	private JButton cashInButton, cashOutButton;
	String monthName;
	String username;
	private JLabel totalCashIn;
	private JLabel totalCashOut;
	private JButton btnDelete;
	private JButton updateButton;

	public MonthBudgetForm(String username, String monthName) {

		this.monthName = monthName;
		this.username = username;
		setResizable(false);
		// Set up the JFrame
		setTitle("Month Budget Form");

		MonthButtons monthButtonsObj = new MonthButtons(username);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				monthButtonsObj.setVisible(true);
			}
		});

		setBounds(100, 100, 960, 600);

		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		// Create the title label and add it to the top of the JFrame

		titleLabel = new JLabel(monthName);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		titleLabel.setBounds(0, 0, 944, 48);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		getContentPane().add(titleLabel);

		// Create the data table and add it to the center of the JFrame
		dataTable = new JTable();
		tableModel = new DefaultTableModel(new String[] { "Date", "Remark", "Category", "Cash In", "Cash Out", "id" },
				0);
		dataTable.setModel(tableModel);
		JScrollPane scrollPane = new JScrollPane(dataTable);
		try {
			Connection conn = DBConnection.getConnection();
			// Create a Statement object to execute SQL queries
			Statement stmt = conn.createStatement();

			// Execute a SELECT query to fetch the data from the corresponding table
			String query = "SELECT * FROM " + username + "_" + monthName;
			ResultSet rs = stmt.executeQuery(query);

			// Populate the tableModel with the data
			while (rs.next()) {
				String date = rs.getString("date");
				String remark = rs.getString("remark");
				String category = rs.getString("category");
				String cashIn = rs.getString("cashIn");
				String cashOut = rs.getString("cashOut");
				int id = rs.getInt("id");
				tableModel.addRow(new Object[] { date, remark, category, cashIn, cashOut, id });
			}

			// Close the ResultSet, Statement, and Connection objects
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		scrollPane.setBounds(32, 48, 878, 339);
		getContentPane().add(scrollPane);

		// Create the section showing the totals and budget at the bottom of the JFrame
		JPanel totalsPanel = new JPanel(new GridLayout(1, 3));
		totalsPanel.setBounds(0, 0, 0, 0);
		totalInField = new JTextField();
		totalOutField = new JTextField();
		budgetField = new JTextField();
		totalInField.setEditable(false);
		totalOutField.setEditable(false);
		budgetField.setEditable(false);
		totalsPanel.add(new JLabel("Total In: "));
		totalsPanel.add(totalInField);
		totalsPanel.add(new JLabel("Total Out: "));
		totalsPanel.add(totalOutField);
		totalsPanel.add(new JLabel("Budget: "));
		totalsPanel.add(budgetField);
		getContentPane().add(totalsPanel);

		JLabel lblNewLabel = new JLabel("Total In   :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setBounds(32, 417, 116, 25);
		getContentPane().add(lblNewLabel);

		JLabel lblTotalOut = new JLabel("Total Out :");
		lblTotalOut.setForeground(Color.RED);
		lblTotalOut.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTotalOut.setBounds(32, 452, 116, 25);
		getContentPane().add(lblTotalOut);

		cashOutButton = new JButton("Cash Out");
		cashOutButton.setBounds(539, 495, 148, 38);
		getContentPane().add(cashOutButton);
		cashOutButton.setBackground(Color.RED);
		cashInButton = new JButton("Cash In");
		cashInButton.setBounds(279, 495, 148, 38);
		getContentPane().add(cashInButton);
		cashInButton.setBackground(Color.GREEN);

		// Add listeners to the Cash In and Cash Out buttons
		cashInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CashInPage cashInObj = new CashInPage(username, monthName);
				cashInObj.setVisible(true);
			}
		});

		cashOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CashOutPage cashOutObj = new CashOutPage(username, monthName);
				cashOutObj.setVisible(true);
			}
		});

		// Create the refresh button and add it to the JFrame
		JButton refreshButton = new JButton("Refresh");
		refreshButton.setBounds(32, 11, 100, 25);
		getContentPane().add(refreshButton);
		refreshButton.setBackground(Color.LIGHT_GRAY);

		totalCashIn = new JLabel("-");
		totalCashIn.setForeground(Color.GREEN);
		totalCashIn.setFont(new Font("Tahoma", Font.PLAIN, 24));
		totalCashIn.setBounds(158, 417, 210, 25);
		getContentPane().add(totalCashIn);

		totalCashOut = new JLabel("-");
		totalCashOut.setForeground(Color.RED);
		totalCashOut.setFont(new Font("Tahoma", Font.PLAIN, 24));
		totalCashOut.setBounds(158, 452, 210, 25);
		getContentPane().add(totalCashOut);

		// Add ActionListener to the Refresh button
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Close the current form
				dispose();

				// Open a new instance of the MonthBudgetForm with the same username and
				// monthName
				MonthBudgetForm monthBudgetForm = new MonthBudgetForm(username, monthName);
				monthBudgetForm.setVisible(true);
			}
		});

		try {
			Connection conn = DBConnection.getConnection();
			Statement stmtCashIn = conn.createStatement();
			ResultSet rsCashIn = stmtCashIn
					.executeQuery("SELECT SUM(CashIn) AS totalCashIn FROM " + username + "_" + monthName);

			if (rsCashIn.next()) {

				totalCashIn.setText(rsCashIn.getString("totalCashIn"));

			}
			stmtCashIn.close();
			rsCashIn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			Connection conn = DBConnection.getConnection();
			Statement stmtCashOut = conn.createStatement();
			ResultSet rsCashOut = stmtCashOut
					.executeQuery("SELECT SUM(CashOut) AS totalCashOut FROM " + username + "_" + monthName);

			if (rsCashOut.next()) {

				totalCashOut.setText(rsCashOut.getString("totalCashOut"));

			}
			stmtCashOut.close();
			rsCashOut.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(791, 495, 119, 38);
		getContentPane().add(btnDelete);

		updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRowIndex = dataTable.getSelectedRow();

				// Check if a row is selected
				if (selectedRowIndex == -1) {
					JOptionPane.showMessageDialog(null, "Please select a row to update.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Get the values of the selected row
				String id = tableModel.getValueAt(selectedRowIndex, 5).toString();
				String date = tableModel.getValueAt(selectedRowIndex, 0).toString();
				String remark = tableModel.getValueAt(selectedRowIndex, 1).toString();
				String category = tableModel.getValueAt(selectedRowIndex, 2).toString();
				String cashIn = tableModel.getValueAt(selectedRowIndex, 3).toString();
				String cashOut = tableModel.getValueAt(selectedRowIndex, 4).toString();

				// Show a dialog to edit the values
				if(Integer.parseInt(cashIn)==0)
				{
					JPanel panel = new JPanel(new GridLayout(0, 1));
					panel.add(new JLabel("Date:"));
					JTextField dateField = new JTextField(date);
					panel.add(dateField);
					panel.add(new JLabel("Remark:"));
					JTextField remarkField = new JTextField(remark);
					panel.add(remarkField);
					panel.add(new JLabel("Category:"));
					JTextField categoryField = new JTextField(category);
					panel.add(categoryField);
					
					panel.add(new JLabel("Cash Out:"));
					JTextField cashOutField = new JTextField(cashOut);
					panel.add(cashOutField);
					int result = JOptionPane.showConfirmDialog(null, panel, "Edit Row", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE);

					// Check if the user clicked the OK button
					if (result == JOptionPane.OK_OPTION) {
						// Update the values in the tableModel
						tableModel.setValueAt(dateField.getText(), selectedRowIndex, 0);
						tableModel.setValueAt(remarkField.getText(), selectedRowIndex, 1);
						tableModel.setValueAt(categoryField.getText(), selectedRowIndex, 2);
						
						tableModel.setValueAt(cashOutField.getText(), selectedRowIndex, 4);

						// Update the changes in the database table
						try {
							Connection conn = DBConnection.getConnection();
							// Create a PreparedStatement object to execute SQL queries with parameters
							String query = "UPDATE " + username + "_" + monthName
									+ " SET date=?, remark=?, category=?, cashIn=?, cashOut=? WHERE id=?";
							PreparedStatement pstmt = conn.prepareStatement(query);
							pstmt.setString(1, dateField.getText());
							pstmt.setString(2, remarkField.getText());
							pstmt.setString(3, categoryField.getText());
							pstmt.setString(4,"0");
							pstmt.setString(5, cashOutField.getText());
							pstmt.setString(6, id);
							pstmt.executeUpdate();

							// Close the PreparedStatement and Connection objects
							pstmt.close();
							conn.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
				if(Integer.parseInt(cashOut)==0)
				{	// Show a dialog to edit the values
					JPanel panel = new JPanel(new GridLayout(0, 1));
					panel.add(new JLabel("Date:"));
					JTextField dateField = new JTextField(date);
					panel.add(dateField);
					panel.add(new JLabel("Remark:"));
					JTextField remarkField = new JTextField(remark);
					panel.add(remarkField);
					panel.add(new JLabel("Category:"));
					JTextField categoryField = new JTextField(category);
					panel.add(categoryField);
					
					panel.add(new JLabel("Cash In:"));
					JTextField cashInField = new JTextField(cashIn);
					panel.add(cashInField);
					int result = JOptionPane.showConfirmDialog(null, panel, "Edit Row", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE);

					// Check if the user clicked the OK button
					if (result == JOptionPane.OK_OPTION) {
						// Update the values in the tableModel
						tableModel.setValueAt(dateField.getText(), selectedRowIndex, 0);
						tableModel.setValueAt(remarkField.getText(), selectedRowIndex, 1);
						tableModel.setValueAt(categoryField.getText(), selectedRowIndex, 2);
						tableModel.setValueAt(cashInField.getText(), selectedRowIndex, 3);

						// Update the changes in the database table
						try {
							Connection conn = DBConnection.getConnection();
							// Create a PreparedStatement object to execute SQL queries with parameters
							String query = "UPDATE " + username + "_" + monthName
									+ " SET date=?, remark=?, category=?, cashIn=?, cashOut=? WHERE id=?";
							PreparedStatement pstmt = conn.prepareStatement(query);
							pstmt.setString(1, dateField.getText());
							pstmt.setString(2, remarkField.getText());
							pstmt.setString(3, categoryField.getText());
							pstmt.setString(4,cashInField.getText());
							pstmt.setString(5, "0");
							pstmt.setString(6, id);
							pstmt.executeUpdate();

							// Close the PreparedStatement and Connection objects
							pstmt.close();
							conn.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
	
			}

		});
		updateButton.setBounds(791, 445, 119, 32);
		getContentPane().add(updateButton);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = dataTable.getSelectedRow();
				if (selectedRow != -1) {
					try {
						// Get the id of the selected row
						int id = (int) dataTable.getValueAt(selectedRow, 5);
						System.out.println(id);
						Connection conn = DBConnection.getConnection();
						// Create a PreparedStatement object to execute SQL queries
						String query = "DELETE FROM " + username + "_" + monthName + " WHERE id=?";
						PreparedStatement pstmt = conn.prepareStatement(query);
						// Set the parameter values for the PreparedStatement object
						pstmt.setInt(1, id);
						// Execute the query
						pstmt.executeUpdate();
						// Close the PreparedStatement and Connection objects
						pstmt.close();
						conn.close();
						// Remove the selected row from the JTable
						tableModel.removeRow(selectedRow);
					} catch (SQLException ex) {
						ex.printStackTrace();
					}

				}

			}
		});

		// Display the JFrame
		setLocationRelativeTo(null);
		setVisible(true);

	}
}
