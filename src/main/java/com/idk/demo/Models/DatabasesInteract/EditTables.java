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

    public static void updateUsername(String username, String newUsername){
        String sql = "UPDATE users SET username = ? WHERE username = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newUsername);
            pstmt.setString(2, username);

            pstmt.executeUpdate();
            System.out.println("Username!! successfully updated.");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void updatePassword(String username, String newPassword){
        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);

            pstmt.executeUpdate();
            System.out.println("Password successfully updated.");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void updateConfirmed(String eventName, Boolean confirmed){
        String sql = "UPDATE events SET confirmed = ? WHERE eventName = ?";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setBoolean(1, confirmed);
            pstmt.setString(2, eventName);
            pstmt.executeUpdate();
            System.out.println("Confirmed!! successfully updated. order confimration");

        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println(eventName + " ur dumb fix thsi");
        }
    }
}
