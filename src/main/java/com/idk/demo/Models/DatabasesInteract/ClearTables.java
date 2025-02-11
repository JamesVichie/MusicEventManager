package com.idk.demo.Models.DatabasesInteract;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.idk.demo.Models.DatabasesInteract.DatabaseConnection.connect;

public class ClearTables {
    // clear a table if it doesn't exist
    public static void clearUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "userID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT UNIQUE NOT NULL, "
                + "password TEXT NOT NULL,"
                + "position TEXT NOT NULL"
                + ");";
        //Always have a manager in the user
        String sqli = "INSERT INTO users (username, password, position) VALUES (\"a\", \"a\", \"Manager\")";
        String sqlii = "INSERT INTO users (username, password, position) VALUES (\"b\", \"b\", \"Staff\")";


        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("Drop table if exists users;");
            stmt.execute(sql);
            stmt.executeUpdate(sqli);
            stmt.executeUpdate(sqlii);

            System.out.println("User cleared successfully.");
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                System.out.println("User failed: User already exists!");
            } else {
                System.out.println("User creation failed: " + e.getMessage());            }
        }

    }

    public static void clearClientsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS clients ("
                + "clientID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "clientName TEXT UNIQUE NOT NULL "
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("Drop table if exists clients;");
            stmt.execute(sql);
            System.out.println("Client cleared successfully.");
        } catch (SQLException e) {
            System.out.println("Client creation failed: " + e.getMessage());
        }


    }

    public static void clearEventsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS events ("
                + "eventID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "clientName TEXT UNIQUE NOT NULL, "
                + "eventName TEXT UNIQUE NOT NULL, "
                + "mainArtist TEXT NOT NULL, "
                + "date TEXT NOT NULL, "
                + "time TEXT NOT NULL, "
                + "duration INTEGER NOT NULL, "
                + "audienceSize INTEGER NOT NULL, "
                + "type TEXT NOT NULL, "
                + "category TEXT NOT NULL, "
                + "confirmed BOOLEAN NOT NULL "
//                + "FOREIGN KEY (clientID) REFERENCES clients(clientID) ON DELETE CASCADE"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("Drop table if exists events;");
            stmt.execute(sql);
            System.out.println("Events Table cleared successfully.");
        } catch (SQLException e) {
            System.out.println("Events creation failed: " + e.getMessage());
        }
    }

    public static void clearVenuesTable() {

        String sql = "CREATE TABLE IF NOT EXISTS venues ("
                + "venueID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "venueName TEXT UNIQUE NOT NULL, "
                + "capacity INTEGER NOT NULL, "
                + "suitable TEXT NOT NULL, "
                + "category TEXT NOT NULL, "
                + "price FLOAT NOT NULL "
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("Drop table if exists venues;");
            stmt.execute(sql);
            System.out.println("Venues Table cleared successfully.");
        } catch (SQLException e) {
            System.out.println("Venues creation failed: " + e.getMessage());
        }
    }

    public static void clearVenuesAvailabilityTable() {

        String sql = "CREATE TABLE IF NOT EXISTS venuesAvaliability ("
                + "bookingID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "venueID INTEGER NOT NULL, "
                + "date DATE NOT NULL, "
                + "time TIME NOT NULL "
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("Drop table if exists venuesAvaliability;");
            stmt.execute(sql);
            System.out.println(" Availability Table cleared successfully.");
        } catch (SQLException e) {
            System.out.println("Availability creation failed: " + e.getMessage());
        }
    }

    public static void clearOrdersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS orders ("
                + "orderID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "eventName String NOT NULL, "
                + "venueName String NOT NULL, "
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("Drop table if exists orders;");
            stmt.execute(sql);
            System.out.println(" orders Table cleared successfully.");
        } catch (SQLException e) {
            System.out.println("orders creation failed: " + e.getMessage());
        }
    }



    public static void main(String[] args) {
        clearUsersTable();
        clearClientsTable();
        clearEventsTable();
        clearVenuesTable();
        clearVenuesAvailabilityTable();
        clearOrdersTable();

        //book
        //order


    }
}
