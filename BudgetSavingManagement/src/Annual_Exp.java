import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Annual_Exp extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public Annual_Exp(String username) {
		setResizable(false);
		// Center the frame on the screen
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MonthButtons monthButtonsObj = new MonthButtons(username);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				monthButtonsObj.setVisible(true);
			}
		});
		setBounds(100, 100, 960, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		String[] columnNames = { "Month Name", "Cash In", "Cash Out" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		// Connect to the database
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DBConnection.getConnection();
			stmt = conn.createStatement();

			// Fetch data for each month and add it to the table
			for (int i = 1; i <= 12; i++) {
				String monthName = getMonthName(i);
				String tableName = username + "_" + monthName;
				String query = "SELECT CashIn, CashOut FROM " + tableName;
				ResultSet rs = stmt.executeQuery(query);

				// Calculate the total Cash In and Cash Out for the month
				double totalCashIn = 0;
				double totalCashOut = 0;
				while (rs.next()) {
					totalCashIn += rs.getDouble("CashIn");
					totalCashOut += rs.getDouble("CashOut");
				}

				Object[] rowData = { monthName, totalCashIn, totalCashOut };
				model.addRow(rowData);
			}

			// Calculate the total Cash In and Cash Out for the year
			double totalCashIn = 0;
			double totalCashOut = 0;
			for (int i = 0; i < model.getRowCount(); i++) {
				totalCashIn += (double) model.getValueAt(i, 1);
				totalCashOut += (double) model.getValueAt(i, 2);
			}
			Object[] totalRow = { "Total", totalCashIn, totalCashOut };
			model.addRow(totalRow);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		// Add the JTable to the content pane
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * Returns the name of the month corresponding to the given month number (1 =
	 * January, 2 = February, etc.)
	 */
	private String getMonthName(int monthNumber) {
		String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		return monthNames[monthNumber - 1];
	}
}
