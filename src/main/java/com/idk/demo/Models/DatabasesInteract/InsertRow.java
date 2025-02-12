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

import com.idk.demo.Models.Venues;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.time.LocalDate;
import static com.idk.demo.Models.DatabasesInteract.DatabaseConnection.connect;
import static java.lang.Float.parseFloat;

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

    public static void insertClientRow(String name, Float clientCommission) {
        String sql = "INSERT INTO clients (clientName, clientCommission) VALUES (?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setFloat(2, clientCommission);

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
                                      Date date,
                                      String time,
                                      int duration,
                                      int audienceSize,
                                      String type,
                                      String category,
                                      Boolean confirmed) {
        String sql = "INSERT INTO events " +
                "(clientName, " +
                "eventName, " +
                "mainArtist, " +
                "date, " +
                "time, " +
                "duration, " +
                "audienceSize, " +
                "type, " +
                "category, " +
                "confirmed )" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

//        String sqc = "INSERT INTO clients" +
//                "(clientName)" +
//                "VALUES (?)";
        insertClientRow(clientName, (float)0);

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, clientName);
            stmt.setString(2, eventName);
            stmt.setString(3, mainArtist);
            stmt.setDate(4, date);
            stmt.setString(5, time);
            stmt.setInt(6, duration);
            stmt.setInt(7, audienceSize);
            stmt.setString(8, type);
            stmt.setString(9, category);
            stmt.setBoolean(10, confirmed);

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
    public static void insertVenuesBatch(List<Venues> venues) {
        String sql = "INSERT INTO Venues (name, capacity, suitableFor, category, bookingPricePerHour) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set auto-commit to false for batch processing
            conn.setAutoCommit(false);

            // Add each venue to the batch
            for (Venues venue : venues) {
                pstmt.setString(1, venue.getVenueName());
                pstmt.setInt(2, venue.getCapacity());
                pstmt.setString(3, venue.getSuitable());
                pstmt.setString(4, venue.getCategory());
                pstmt.setFloat(5, venue.getPrice());

                // Add to batch
                pstmt.addBatch();
            }

            // Execute the batch
            int[] result = pstmt.executeBatch();

            // Commit the transaction
            conn.commit();
            System.out.println("Batch insert completed successfully, inserted " + result.length + " rows.");

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

    public static void insertOrderRow(String eventName, String venueName, Date orderDate, int duration, float price, float commission, String clientName) {
//            public Orders(int orderID, String eventName, String venueName, Date orderDate, int duration, float price, float commission) {

            String sql = "INSERT INTO orders (eventName, venueName, orderDate, duration, price, commission, clientName) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventName);
            stmt.setString(2, venueName);
            stmt.setDate(3, orderDate);
            stmt.setInt(4, duration);
            stmt.setFloat(5, price);
            stmt.setFloat(6, commission);
            stmt.setString(7, clientName);


            stmt.executeUpdate();
            System.out.println("orders successfully added.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }



    public static void main(String[] args) {
        // Insert test data
        insertUserRow("Vichie", "Vichie", "Manager");
        insertUserRow("q", "q", "Manager");
        insertUserRow("w", "w", "Staff");
        insertUserRow("z", "z", "Staff");

//        insertClientRow("Jack A", (float) 0);
//        insertClientRow("Peter B", (float) 0);
//        insertClientRow("Lucy C", (float) 0);
//        insertClientRow("Margret D", (float) 0);
//        insertClientRow("Bob E", (float) 0);
//        insertClientRow("Jess F", (float) 0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            insertEventRow("Jeff", "Music Night", "Jackson Browne", Date.valueOf("2024-03-15"),
                    "12pm", 3, 3333, "Indoor", "Live", false);

            insertEventRow("John", "Comedy Night", "Kevin Hart", Date.valueOf("2024-08-22"),
                    "7pm", 2, 200, "Indoor", "Comedy", false);
            insertEventRow("Steve", "Tech Talk", "Elon Musk", Date.valueOf("2024-09-30"),
                    "4pm", 1, 1000, "Online", "Conference", false);
            insertEventRow("Sarah", "Food Fest", "Gordon Ramsay", Date.valueOf("2024-04-12"),
                    "11am", 8, 3000, "Outdoor", "Festival", false);


            insertEventRow("Anna", "Fashion Show", "Gigi Hadid", Date.valueOf("2024-09-09"),
                    "5pm", 3, 700, "Indoor", "Fashion", false);

            insertEventRow("Emily", "Opera Night", "Andrea Bocelli", Date.valueOf("2024-12-20"),
                    "7pm", 3, 800, "Indoor", "Music", false);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        insertVenueRow("Sunset Arena", 5000, "Concerts", "Entertainment", 7500.00f);
        insertVenueRow("Grand Hall", 3000, "Conferences", "Business", 6500.00f);
        insertVenueRow("Riverside Pavilion", 2000, "Weddings", "Celebrations", 4000.00f);
        insertVenueRow("Tech Hub Center", 10000, "Expos", "Technology", 12000.00f);
        insertVenueRow("Eagle Stadium", 8000, "Sports", "Recreational", 9500.00f);
        insertVenueRow("Cultural Auditorium", 1500, "Plays", "Arts", 3000.00f);
        insertVenueRow("Oceanview Terrace", 1000, "Gatherings", "Social", 2000.00f);
        insertVenueRow("Skyline Hall", 3500, "Seminars", "Education", 5000.00f);
        insertVenueRow("Summit Center", 2500, "Workshops", "Professional", 4500.00f);

        insertVenueAvailabilityRow(1, "2025-02-10", "18:00:00");
    }
}

