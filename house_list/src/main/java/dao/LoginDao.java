package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.LoginBeans;

public class LoginDao {
	private final String JDBC_URL = "jdbc:h2:tcp://localhost/~/house_list";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";

	/**
	 * アカウントの検索
	 * 
	 * @param name
	 * @param password
	 * @return LoginBeans
	 */
	public LoginBeans findAccount(String name, String password) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			String sql = "SELECT ID, NAME, PASSWORD FROM ACCOUNT WHERE NAME = ? AND PASSWORD = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, name);
			pStmt.setString(2, password);
			ResultSet rs = pStmt.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("id");
				LoginBeans login = new LoginBeans(id, name, password);
				return login;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

