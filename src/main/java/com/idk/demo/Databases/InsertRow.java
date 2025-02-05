package com.idk.demo.Databases;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static com.idk.demo.Databases.DatabaseConnection.connect;


public class InsertRow {
    public static void insertUserRow(String username, String password, String position) {
        String sql = "INSERT INTO users (username, password, position) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, position);

            stmt.executeUpdate();
            System.out.println("Row successfully added.");


        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {  // 19 = SQLITE_CONSTRAINT (Unique constraint violation)
                System.out.println("Insert failed: Username already exists!");
            } else {
                System.out.println("SQL Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {insertUserRow("TEST", "TEST", "Manager");}
}

