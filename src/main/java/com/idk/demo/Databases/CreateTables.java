package com.idk.demo.Databases;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.idk.demo.Databases.DatabaseConnection.connect;

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
        String sqli = "INSERT INTO users (username, password, position) VALUES (\"Vichie\", \"bbb\", \"Manager\")";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("Drop table if exists users;");
            stmt.execute(sql);
            stmt.executeUpdate(sqli);

            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
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
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }


    }

    public static void createEventsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS events ("
                + "eventID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "clientID INTEGER NOT NULL, "
                + "eventName TEXT UNIQUE NOT NULL, "
                + "mainArtist TEXT NOT NULL, "
                + "supportingArtist TEXT NOT NULL, "
                + "eventType TEXT NOT NULL, "
                + "audienceSize INTEGER NOT NULL, "
                + "category TEXT NOT NULL "
//                + "FOREIGN KEY (clientID) REFERENCES clients(clientID) ON DELETE CASCADE"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
//            stmt.execute("Drop table if exists users;");
            stmt.execute(sql);
            System.out.println("Events Table created successfully.");
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
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
            System.out.println("Table creation failed: " + e.getMessage());
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
            System.out.println("Venues Avaliability Table created successfully.");
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }
    }



    public static void main(String[] args) {
        createUsersTable();
        createClientsTable();
        createEventsTable();
        createVenuesTable();
        createVenuesAvailabilityTable();

        //book
        //order


    }
}
