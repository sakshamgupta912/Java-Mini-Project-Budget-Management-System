package authentication;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;

public class Registration extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField phoneField;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField rePasswordField;

	/**
	 * Launch the application.
	 */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    Registration frame = new Registration();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

	/**
	 * Create the frame.
	 */
	public void setDefaultCloseOperation() {
		setVisible(false);
	}

	public Registration() {

		setDefaultCloseOperation();

		setBounds(100, 100, 960, 650);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		nameField = new JTextField();
		nameField.setBounds(592, 155, 237, 34);
		contentPane.add(nameField);
		nameField.setColumns(10);

		phoneField = new JTextField();
		phoneField.setBounds(592, 233, 237, 34);
		contentPane.add(phoneField);
		phoneField.setColumns(10);

		usernameField = new JTextField();
		usernameField.setBounds(592, 316, 237, 34);
		contentPane.add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(592, 397, 237, 34);
		contentPane.add(passwordField);

		rePasswordField = new JPasswordField();
		rePasswordField.setBounds(592, 477, 237, 34);
		contentPane.add(rePasswordField);

		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// handle register button action
				try {
					Connection connection = DBConnection.getConnection();
					String username = usernameField.getText();
					String phone = phoneField.getText();
					String name = nameField.getText();
					String password = new String(passwordField.getPassword());
					String rePassword = new String(rePasswordField.getPassword());

					// Check if username already exists in the users table
					String selectQuery = "SELECT * FROM users WHERE username = ?";
					PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
					selectStatement.setString(1, username);
					ResultSet resultSet = selectStatement.executeQuery();
					if (resultSet.next()) {
						// Username already exists in the table
						JOptionPane.showMessageDialog(null, "Username already exists", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (!password.equals(rePassword)) {
						// Passwords do not match
						System.err.println("Passwords do not match");
						JOptionPane.showMessageDialog(null, "Passwords do not match", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						// Insert the new user into the users table
						String insertQuery = "INSERT INTO users (username, phone, name, password) VALUES (?, ?, ?, ?)";
						PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
						insertStatement.setString(1, username);
						insertStatement.setString(2, phone);
						insertStatement.setString(3, name);
						insertStatement.setString(4, password);
						insertStatement.executeUpdate();

						// Create tables for each month
						String[] months = new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September",
								"October", "November", "December" };
						for (String month : months) {
							String tableName = username + "_" + month;
							String createTableQuery = "CREATE TABLE " + tableName
									+ " (Date DATE, Remark VARCHAR(100), Category VARCHAR(50), CashIn INT, CashOut INT, id INT AUTO_INCREMENT, PRIMARY KEY (id))";
							PreparedStatement createTableStatement = connection.prepareStatement(createTableQuery);
							createTableStatement.executeUpdate();
						}
						JOptionPane.showMessageDialog(null, "User registered successfully");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		registerButton.setBounds(592, 547, 94, 34);
		contentPane.add(registerButton);

		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameField.setText("");
				phoneField.setText("");
				usernameField.setText("");
				passwordField.setText("");
				rePasswordField.setText("");
			}
		});
		resetButton.setBounds(735, 547, 94, 34);
		contentPane.add(resetButton);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\saksh\\Desktop\\Java Mini Project- Budget Management System Git\\RegBg.png"));
		lblNewLabel.setBounds(0, 0, 960, 600);
		contentPane.add(lblNewLabel);
		setLocationRelativeTo(null);
	}
}