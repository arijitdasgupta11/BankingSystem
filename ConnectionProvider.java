package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {
	private static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static Properties props;

	static {
		props = new Properties();
	}

	public static Connection getConnection(String username, String password) {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			try {
				props.setProperty("user", username);
				props.setProperty("password", password);
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}

}
