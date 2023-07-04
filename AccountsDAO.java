package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountsDAO {

	private static Connection conn;

	public static Connection getConn() {
		return conn;
	}

	public AccountsDAO(String username, String password) {
		conn = ConnectionProvider.getConnection(username, password);
	}

	public String createAccount(String name, String id, double balance) throws SQLException {

		String insertquery = "INSERT INTO ACCOUNTS(ID,NAME,BALANCE) VALUES(?,?,?)";

		if (balance >= 500) {
			PreparedStatement pstmt = conn.prepareStatement(insertquery);

			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setDouble(3, balance);

			int flag = pstmt.executeUpdate();

			if (flag > 0) {
				return "Account Created Successfully";
			}
			return "Some error happened on server.Please Try Again!";
		}
		return "Minimum Deposit should be atleast 500/-";
	}

	public double getBalance(String id) throws SQLException {

		double balance = 0;
		String getquery = "SELECT BALANCE FROM ACCOUNTS WHERE ID=?";

		PreparedStatement pstmt = conn.prepareStatement(getquery);
		pstmt.setString(1, id);

		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {

		balance = rs.getDouble(1);
		return balance;
		}
		return 0;
	}

	public String creditIntoAccount(String id, double amount) throws SQLException {

		double currentbalance = this.getBalance(id);
		currentbalance += amount;

		String updatequery = "UPDATE ACCOUNTS SET BALANCE=? WHERE ID=?";

		PreparedStatement pstmt = conn.prepareStatement(updatequery);
		pstmt.setDouble(1, currentbalance);
		pstmt.setString(2, id);

		int flag = pstmt.executeUpdate();

		if (flag > 0) {
			return "Amount " + amount + "/- credited into Acc/No. " + id;
		}
		return "Some error happened on server.Please Try Again!";
	}

	public String debitFromAccount(String id, double amount) throws SQLException {

		double currentbalance = this.getBalance(id);
		if (currentbalance > amount) {
			currentbalance -= amount;

			String updatequery = "UPDATE ACCOUNTS SET BALANCE=? WHERE ID=?";

			PreparedStatement pstmt = conn.prepareStatement(updatequery);
			pstmt.setDouble(1, currentbalance);
			pstmt.setString(2, id);

			int flag = pstmt.executeUpdate();

			if (flag > 0) {
				return "Amount " + amount + " debited from Acc/No: " + id;
			}
			return "Some error happened on server.Please Try Again!";
		}
		return "Insufficient Balance";
	}

	public ResultSet getAllAccounts() throws SQLException {

		String query = "SELECT * FROM ACCOUNTS";

		PreparedStatement pstmt = conn.prepareStatement(query);

		ResultSet rs = pstmt.executeQuery(query);
		return rs;
	}

	public String deleteAccount(String id) throws SQLException {

		String deletequery = "DELETE FROM ACCOUNTS WHERE ID=?";

		PreparedStatement pstmt = conn.prepareStatement(deletequery);

		pstmt.setString(1, id);

		int flag = pstmt.executeUpdate();

		if (flag > 0) {
			return "Acc/No. " + id + " has been deleted successfully";
		}
		return "Account does'nt exists";
	}

}
