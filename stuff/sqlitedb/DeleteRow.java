package sqlitedb;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteRow {
	public static void main(String[] args) {
		final String TABLE_NAME = "Student";

		try (Connection con = DatabaseConnection.getConnection();
				Statement stmt = con.createStatement();) {
			String sql = "DELETE FROM " + TABLE_NAME + 
					" WHERE first_name LIKE 'Tom'";
			
			int result = stmt.executeUpdate(sql);
			
			if (result == 1) {
				System.out.println("Delete from table " + TABLE_NAME + " executed successfully");
				System.out.println(result + " row(s) affected");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
