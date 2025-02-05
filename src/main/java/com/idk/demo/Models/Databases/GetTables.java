package com.idk.demo.Models.Databases;

import com.idk.demo.Models.Users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.idk.demo.Models.Databases.DatabaseConnection.connect;

public class GetTables {
    public static ArrayList<Users> getUsers() {
        String sql = "SELECT * FROM users";
        ArrayList<Users> usersList = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(sql)) {
                while(resultSet.next()) {
                    Users user = new Users(resultSet.getInt("userID"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("position"));
                    System.out.println(user.toString());
                    usersList.add(user);

                }
                System.out.println("User read successfully.");
                return usersList;
            }

        } catch (SQLException e) {
            System.out.println("Table read failed: " + e.getMessage());
            return usersList;
        }
    }
    public static void main(String[] args) {
        ArrayList<Users> user = getUsers();
    }
}
