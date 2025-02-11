package com.idk.demo.Models.DatabasesInteract;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

import static com.idk.demo.Models.DatabasesInteract.DatabaseConnection.connect;

public class CreateTables {
    // Create a table if it doesn't exist
    public static void createUsersTable() {

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
            stmt.execute(sql);
            stmt.executeUpdate(sqli);
            stmt.executeUpdate(sqlii);

            System.out.println("User created successfully.");
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                System.out.println("User failed: User already exists!");
            } else {
                System.out.println("User creation failed: " + e.getMessage());            }
        }
    }

    public static void createClientsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS clients ("
                + "clientID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "clientName TEXT UNIQUE NOT NULL "
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
//            stmt.execute("Drop table if exists users;");
            stmt.execute(sql);
            System.out.println("Clients created successfully.");
        } catch (SQLException e) {
            System.out.println("Clients creation failed: " + e.getMessage());
        }


    }

    public static void createEventsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS events ("
                + "eventID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "clientName TEXT UNIQUE NOT NULL, "
                + "eventName TEXT UNIQUE NOT NULL, "
                + "mainArtist TEXT NOT NULL, "
                + "date DATE NOT NULL, "
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
//            stmt.execute("Drop table if exists users;");
            stmt.execute(sql);
            System.out.println("Events Table created successfully.");
        } catch (SQLException e) {
            System.out.println("Events creation failed: " + e.getMessage());
        }
    }

    public static void createVenuesTable() {

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
//            stmt.execute("Drop table if exists users;");
            stmt.execute(sql);
            System.out.println("Venues Table created successfully.");
        } catch (SQLException e) {
            System.out.println("Venue creation failed: " + e.getMessage());
        }
    }

    public static void createVenuesAvailabilityTable() {
        String sql = "CREATE TABLE IF NOT EXISTS venuesAvaliability ("
                + "bookingID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "venueID INTEGER NOT NULL, "
                + "date DATE NOT NULL, "
                + "time TIME NOT NULL "
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
//            stmt.execute("Drop table if exists users;");
            stmt.execute(sql);
            System.out.println("Venues Availability Table created successfully.");
        } catch (SQLException e) {
            System.out.println("Availability creation failed: " + e.getMessage());
        }
    }

        public static void createOrdersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS orders ("
                + "orderID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "eventName String NOT NULL, "
                + "venueName String NOT NULL, "
                + "orderDate DATE NOT NULL, "
                + "duration INTEGER NOT NULL, "
                + "price FLOAT NOT NULL, "
                + "commission FLOAT NOT NULL "
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
//            stmt.execute("Drop table if exists orders;");
            stmt.execute(sql);
            System.out.println(" orders Table created successfully.");
        } catch (SQLException e) {
            System.out.println("orders creation failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createUsersTable();
        createClientsTable();
        createEventsTable();
        createVenuesTable();
        createVenuesAvailabilityTable();
        createOrdersTable();

        //book
        //order


    }
}
