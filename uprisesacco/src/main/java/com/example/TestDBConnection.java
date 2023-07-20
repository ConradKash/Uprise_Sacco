package com.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost/uprise_sacco";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "free 123";

    public static void main(String[] args) {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                System.out.println("Connection to the database successful.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found. Make sure it is in your classpath.");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }
    }
}
