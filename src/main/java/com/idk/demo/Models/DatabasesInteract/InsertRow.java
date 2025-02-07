//package com.idk.demo.Models.Databases;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import static com.idk.demo.Models.Databases.DatabaseConnection.connect;
//
//
//public class InsertRow {
//    public static void insertUserRow(String username, String password, String position) {
//        String sql = "INSERT INTO users (username, password, position) VALUES (?, ?, ?)";
//
//        try (Connection conn = connect();
//             PreparedStatement stmt = conn.prepareStatement(sql);) {
//            stmt.setString(1, username);
//            stmt.setString(2, password);
//            stmt.setString(3, position);
//
//            stmt.executeUpdate();
//            System.out.println("Row successfully added.");
//
//
//        } catch (SQLException e) {
//            if (e.getErrorCode() == 19) {  // 19 = SQLITE_CONSTRAINT (Unique constraint violation)
//                System.out.println("Insert failed: Username already exists!");
//            } else {
//                System.out.println("SQL Error: " + e.getMessage());
//            }
//        }
//    }
//
//    public static void insertClientRow(String name) {
//        String sql = "INSERT INTO clients (clientName) VALUES (?)";
//
//        try (Connection conn = connect();
//             PreparedStatement stmt = conn.prepareStatement(sql);) {
//            stmt.setString(1, name);
//
//            stmt.executeUpdate();
//            System.out.println("Row successfully added.");
//
//
//        } catch (SQLException e) {
//            if (e.getErrorCode() == 19) {  // 19 = SQLITE_CONSTRAINT (Unique constraint violation)
//                System.out.println("Insert failed: Username already exists!");
//            } else {
//                System.out.println("SQL Error: " + e.getMessage());
//            }
//        }
//    }
//
//    public static void main(String[] args) {
////        insertUserRow("TEST", "TEST", "Manager");
//        insertClientRow("TESTss");
//
//    }
//}
//

package com.idk.demo.Models.DatabasesInteract;

import java.sql.*;

import static com.idk.demo.Models.DatabasesInteract.DatabaseConnection.connect;

public class InsertRow {

    public static void insertUserRow(String username, String password, String position) {
        String sql = "INSERT INTO users (username, password, position) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, position);

            stmt.executeUpdate();
            System.out.println("User successfully added.");
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {  // 19 = SQLITE_CONSTRAINT (Unique constraint violation)
                System.out.println("Insert failed: Username already exists!");
            } else {
                System.out.println("SQL Error: " + e.getMessage());
            }
        }
    }

    public static void insertClientRow(String name) {
        String sql = "INSERT INTO clients (clientName) VALUES (?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);

            stmt.executeUpdate();
            System.out.println("Client successfully added.");
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                System.out.println("Insert failed: Client name already exists!");
            } else {
                System.out.println("SQL Error: " + e.getMessage());
            }
        }
    }

    public static void insertEventRow(String clientName,
                                      String eventName,
                                      String mainArtist,
                                      String date,
                                      String time,
                                      int duration,
                                      int audienceSize,
                                      String type,
                                      String category) {
        String sql = "INSERT INTO events " +
                "(clientName, " +
                "eventName, " +
                "mainArtist, " +
                "date, " +
                "time, " +
                "duration, " +
                "audienceSize, " +
                "type, " +
                "category) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, clientName);
            stmt.setString(2, eventName);
            stmt.setString(3, mainArtist);
            stmt.setString(4, date);
            stmt.setString(5, time);
            stmt.setInt(6, duration);
            stmt.setInt(7, audienceSize);
            stmt.setString(8, type);
            stmt.setString(9, category);

            stmt.executeUpdate();
            System.out.println("Event successfully added.");
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                System.out.println("Insert failed: Event name already exists!");
            } else {
                System.out.println("SQL Error: " + e.getMessage());
            }
        }
    }

    public static void insertVenueRow(String venueName, int capacity, String suitable, String category, float price) {
        String sql = "INSERT INTO venues (venueName, capacity, suitable, category, price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, venueName);
            stmt.setInt(2, capacity);
            stmt.setString(3, suitable);
            stmt.setString(4, category);
            stmt.setFloat(5, price);

            stmt.executeUpdate();
            System.out.println("Venue successfully added.");
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                System.out.println("Insert failed: Venue name already exists!");
            } else {
                System.out.println("SQL Error: " + e.getMessage());
            }
        }
    }

    public static void insertVenueAvailabilityRow(int venueID, String date, String time) {
        //FIX ME! UNIQUE ISSUE! HAVE TO MAKE A COMPOSITE KEY?
        String sql = "INSERT INTO venuesAvaliability (venueID, date, time) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, venueID);
            stmt.setString(2, date);
            stmt.setString(3, time);

            stmt.executeUpdate();
            System.out.println("Venue availability successfully added.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Insert test data
        insertUserRow("TESfT", "TEST", "Manager");
        insertUserRow("TESsfT", "TEST", "Manager");
        insertUserRow("TESafT", "TEST", "Manager");
        insertUserRow("TESdfT", "TEST", "ddd");

        insertClientRow("Calient A");
        insertEventRow("Jeff", "Music aasas", "Jackson Browne", "09/01/2003",
                "12pm", 3, 3333, "Indoor", "Live");
        insertVenueRow("xxx Hfall", 1000, "Concesrts", "Entertainment", 5000.00f);
        insertVenueRow("www Hfall", 1000, "we", "wegw   e", 5000.00f);
        insertVenueRow("qqqppp Hfall", 1000, "Concesrts", "Entertsssainment", 5000.00f);
        insertVenueRow("fggg Hfall", 31000, "fsd", "Entertainment", 5000.00f);
        insertVenueRow("jjjj Hfall", 1000, "Concdsfesrts", "xxx", 5000.00f);
        insertVenueRow("jjjjasa Hfall", 1000, "Concdsfesrts", "Entertainment", 5000.00f);
        insertVenueRow("d Hfall", 1000, "Concdsfesrts", "vvv", 5000.00f);
        insertVenueRow("ssjjjj Hfall", 1000, "Concdsfesrts", "bbb", 5000.00f);
        insertVenueRow("gggggg Hfall", 1000, "Concdsfesrts", "AAA", 5000.00f);



        insertVenueAvailabilityRow(1, "2025-02-10", "18:00:00");
    }
}

