import java.sql.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/JavaProject";
        String username = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}

public class Login extends JFrame implements ActionListener {
    private JTextField userText;
    private JPasswordField passwordText;
    private JButton loginButton, resetButton, registerButton;

    public Login() {
        setTitle("Login Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 960, 600);
        setResizable(false);
        getContentPane().setLayout(null);

        userText = new JTextField();
        userText.setBounds(671, 216, 204, 32);
        getContentPane().add(userText);

        passwordText = new JPasswordField();
        passwordText.setBounds(671, 302, 206, 32);
        getContentPane().add(passwordText);

        loginButton = new JButton("Login");
        loginButton.setBounds(671, 385, 77, 23);
        getContentPane().add(loginButton);
        loginButton.addActionListener(this);

        resetButton = new JButton("Reset");
        resetButton.setBounds(798, 385, 77, 23);
        getContentPane().add(resetButton);
        resetButton.addActionListener(this);

        registerButton = new JButton("Register!");
        registerButton.setBounds(785, 458, 90, 23);
        getContentPane().add(registerButton);

        JLabel backgroundLabel = new JLabel(new ImageIcon("C:\\Users\\saksh\\Desktop\\Java Mini Project\\Eclipse\\1.png"));
        backgroundLabel.setBounds(0, 0, 960, 600);
        getContentPane().add(backgroundLabel);

        registerButton.addActionListener(this);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());

            try {
                // Get a connection to the database
                Connection connection = DBConnection.getConnection();

                // Create a statement to execute SQL queries
                Statement statement = connection.createStatement();

                // Execute the query and get the result set
                ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE Username = '" + username + "' AND Password = '" + password + "'");

                // Check if the user exists in the database
                if (resultSet.next()) {
                    // If the user exists, show the month button form
                	
                    MonthButtons monthButtonForm= new MonthButtons(username);
                    monthButtonForm.setVisible(true);
                    setVisible(false);
                } else {
                    // If the user doesn't exist, show an error message
                    JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
                }

                // Close the database connection, statement, and result set
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == resetButton) {
            // handle reset button action
            userText.setText("");
            passwordText.setText("");
        } else if (e.getSource() == registerButton) {
            Registration registrationForm = new Registration();
            registrationForm.setVisible(true);
        }
    }


    public static void main(String[] args) {
        new Login();
    }
}
