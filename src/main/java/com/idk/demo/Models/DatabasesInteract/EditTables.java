package com.idk.demo.Models.DatabasesInteract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static com.idk.demo.Models.DatabasesInteract.DatabaseConnection.connect;

public class EditTables {
    public static void promoteUser(String username){
        String sql = "UPDATE users SET position = ? WHERE username = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "Manager");
            pstmt.setString(2, username);

            pstmt.executeUpdate();
            System.out.println("User successfully updated.");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
