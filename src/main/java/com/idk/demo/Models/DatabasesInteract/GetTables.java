//package com.idk.demo.Models.Databases;
//
//import com.idk.demo.Models.Events;
//import com.idk.demo.Models.Users;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//
//import static com.idk.demo.Models.Databases.DatabaseConnection.connect;
//
//public class GetTables {
//    public static ArrayList<Users> getUsers() {
//        String sql = "SELECT * FROM users";
//        ArrayList<Users> usersList = new ArrayList<>();
//
//        try (Connection conn = connect();
//             Statement stmt = conn.createStatement()) {
//            try (ResultSet resultSet = stmt.executeQuery(sql)) {
//                while(resultSet.next()) {
//                    Users user = new Users(resultSet.getInt("userID"),
//                            resultSet.getString("username"),
//                            resultSet.getString("password"),
//                            resultSet.getString("position"));
//                    System.out.println(user.toString());
//                    usersList.add(user);
//
//                }
//                System.out.println("User read successfully.");
//                return usersList;
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Table read failed: " + e.getMessage());
//            return usersList;
//        }
//    }
//
//    public static ArrayList<Events> getEvents() {
//        String sql = "SELECT * FROM events";
//        ArrayList<Events> eventList = new ArrayList<>();
//
//        try (Connection conn = connect();
//             Statement stmt = conn.createStatement()) {
//            try (ResultSet resultSet = stmt.executeQuery(sql)) {
//                while(resultSet.next()) {
//                    Events event = new Events(
//                            resultSet.getString("clientName"),
//                            resultSet.getString("eventName"),
//                            resultSet.getString("mainArtist"),
//                            resultSet.getString("date"),
//                            resultSet.getString("time"),
//                            resultSet.getInt("duration"),
//                            resultSet.getInt("audienceSize"),
//                            resultSet.getString("type"),
//                            resultSet.getString("category")
//                            );
//                    System.out.println(event.toString());
//                    eventList.add(event);
//
//                }
//                System.out.println("User read successfully.");
//                return eventList;
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Table read failed: " + e.getMessage());
//            return eventList;
//        }
//    }
//    public static void main(String[] args) {
//
//        ArrayList<Users> user = getUsers();
//        ArrayList<Events> event = getEvents();
//    }
//}
package com.idk.demo.Models.DatabasesInteract;

import com.idk.demo.Models.*;

import java.sql.*;
import java.util.ArrayList;

import static com.idk.demo.Models.DatabasesInteract.DatabaseConnection.connect;

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

    public static ArrayList<Events> getEvents() {
        String sql = "SELECT * FROM events";
        ArrayList<Events> eventList = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(sql)) {
                while(resultSet.next()) {
                    Events event = new Events(
                            resultSet.getString("clientName"),
                            resultSet.getString("eventName"),
                            resultSet.getString("mainArtist"),
                            resultSet.getString("date"),
                            resultSet.getString("time"),
                            resultSet.getInt("duration"),
                            resultSet.getInt("audienceSize"),
                            resultSet.getString("type"),
                            resultSet.getString("category"),
                            resultSet.getBoolean("confirmed")
                    );
                    System.out.println(event.toString());
                    eventList.add(event);
                }
                System.out.println("Events read successfully.");
                return eventList;
            }
        } catch (SQLException e) {
            System.out.println("Table read failed: " + e.getMessage());
            return eventList;
        }
    }

    public static Events getEvent(String eventName) {
        String sql = "SELECT * FROM events WHERE eventName = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, eventName);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    Events event = new Events(
                            resultSet.getString("clientName"),
                            resultSet.getString("eventName"),
                            resultSet.getString("mainArtist"),
                            resultSet.getString("date"),
                            resultSet.getString("time"),
                            resultSet.getInt("duration"),
                            resultSet.getInt("audienceSize"),
                            resultSet.getString("type"),
                            resultSet.getString("category"),
                            resultSet.getBoolean("confirmed")

                    );
                    System.out.println("Venue retrieved successfully: " + event);
                    return event;
                } else {
                    System.out.println("No venue found with the name: " + eventName);
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching venue: " + e.getMessage());
        }

        return null;
    }

    public static ArrayList<Clients> getClients() {
        String sql = "SELECT * FROM clients";
        ArrayList<Clients> clientList = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(sql)) {
                while(resultSet.next()) {
                    Clients client = new Clients(
                            resultSet.getInt("clientID"),
                            resultSet.getString("clientName")
                    );
                    System.out.println(client.toString());
                    clientList.add(client);
                }
                System.out.println("Clients read successfully.");
                return clientList;
            }
        } catch (SQLException e) {
            System.out.println("Table read failed: " + e.getMessage());
            return clientList;
        }
    }

    public static ArrayList<Venues> getVenues() {
        String sql = "SELECT * FROM venues";
        ArrayList<Venues> venueList = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(sql)) {
                while(resultSet.next()) {
                    Venues venue = new Venues(
                            resultSet.getInt("venueID"),
                            resultSet.getString("venueName"),
                            resultSet.getInt("capacity"),
                            resultSet.getString("suitable"),
                            resultSet.getString("category"),
                            resultSet.getFloat("price")
                    );
                    System.out.println(venue.toString());
                    venueList.add(venue);
                }
                System.out.println("Venues read successfully.");
                return venueList;
            }
        } catch (SQLException e) {
            System.out.println("Table read failed: " + e.getMessage());
            return venueList;
        }
    }

    public static Venues getVenue(String venueName) {
        String sql = "SELECT * FROM venues WHERE venueName = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, venueName);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    Venues venue = new Venues(
                            resultSet.getInt("venueID"),
                            resultSet.getString("venueName"),
                            resultSet.getInt("capacity"),
                            resultSet.getString("suitable"),
                            resultSet.getString("category"),
                            resultSet.getFloat("price")
                    );
                    System.out.println("Venue retrieved successfully: " + venue);
                    return venue;
                } else {
                    System.out.println("No venue found with the name: " + venueName);
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching venue: " + e.getMessage());
        }

        return null;
    }

    public static ArrayList<VenueAvailability> getVenueAvailabilities() {
        String sql = "SELECT * FROM venuesAvaliability";
        ArrayList<VenueAvailability> availabilityList = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(sql)) {
                while(resultSet.next()) {
                    VenueAvailability availability = new VenueAvailability(
                            resultSet.getInt("bookingID"),
                            resultSet.getInt("venueID"),
                            resultSet.getString("date"),
                            resultSet.getString("time")
                    );
                    System.out.println(availability.toString());
                    availabilityList.add(availability);
                }
                System.out.println("Venue Availabilities read successfully.");
                return availabilityList;
            }
        } catch (SQLException e) {
            System.out.println("Table read failed: " + e.getMessage());
            return availabilityList;
        }
    }

    public static ArrayList<Orders> getOrders() {
        String sql = "SELECT * FROM orders";
        ArrayList<Orders> orderList = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery(sql)) {
                while(resultSet.next()) {
                    Orders order = new Orders(
//                            resultSet.getInt("orderID"),
                            resultSet.getString("eventName"),
                            resultSet.getString("venueName")
                    );
                    System.out.println(order.toString());
                    orderList.add(order);
                }
                System.out.println("Clients read successfully.");
                return orderList;
            }
        } catch (SQLException e) {
            System.out.println("Table read failed: " + e.getMessage());
            return orderList;
        }
    }
    public static void main(String[] args) {
        ArrayList<Users> user = getUsers();
        ArrayList<Events> event = getEvents();
        ArrayList<Clients> client = getClients();
        ArrayList<Venues> venue = getVenues();
        ArrayList<VenueAvailability> availability = getVenueAvailabilities();
    }
}
