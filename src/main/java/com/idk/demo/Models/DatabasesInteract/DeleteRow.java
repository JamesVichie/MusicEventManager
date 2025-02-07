package com.idk.demo.Models.DatabasesInteract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static com.idk.demo.Models.DatabasesInteract.DatabaseConnection.connect;

public class DeleteRow {
    public static void deleteUserRow(String username) {
//        String sql = "INSERT INTO users (username, password, position) VALUES (?, ?, ?)";
//        String sql = "DELETE " + username + " FROM users";
//
//
//        try (Connection conn = connect();
//             Statement stmt = conn.createStatement()){
//            stmt.execute("DELETE " + username + " FROM users");
//            System.out.println("User successfully added.");
//
//        } catch (SQLException e) {
//            if (e.getErrorCode() == 19) {  // 19 = SQLITE_CONSTRAINT (Unique constraint violation)
//                System.out.println("Insert failed: Username already exists!");
//            } else {
//                System.out.println("SQL Error: " + e.getMessage());
//            }
//        }
        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User '" + username + "' successfully deleted.");
            } else {
                System.out.println("No user found with username: " + username);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
