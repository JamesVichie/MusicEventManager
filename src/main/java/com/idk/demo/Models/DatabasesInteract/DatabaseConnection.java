package com.idk.demo.Models.DatabasesInteract;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL = "jdbc:sqlite:mydatabase.db"; // Database file

	// Establish connection
	public static Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL);
			System.out.println(".");
		} catch (SQLException e) {
			System.out.println("Connection failed: " + e.getMessage());
		}
		return conn;
	}
}
