package sqlitedb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
	public static void main(String[] args) {
		final String TABLE_NAME = "Student";

		try (Connection con = DatabaseConnection.getConnection();
				Statement stmt = con.createStatement();) {
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME 
										+ "(id INT NOT NULL,"
										+ "student_number VARCHAR(8) NOT NULL,"
										+ "first_name VARCHAR(20) NOT NULL,"
										+ "last_name VARCHAR(20) NOT NULL,"
										+ "PRIMARY KEY (id))");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
