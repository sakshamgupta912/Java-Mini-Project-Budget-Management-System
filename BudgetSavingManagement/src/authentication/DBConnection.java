package authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	 public static Connection getConnection() throws SQLException {
	        String url = "jdbc:mysql://localhost:3306/JavaProject";
	        String username = "root";
	        String password = "root";
	        Connection connection = DriverManager.getConnection(url, username, password);
	        return connection;
	    }

}
