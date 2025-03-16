package com.example.espaceclient.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    private static final String URL = "jdbc:mysql://localhost:3306/db_miravia";
    private static final String USER = "root"; // Default XAMPP username
    private static final String PASSWORD = ""; // Default XAMPP password is empty
    private static final int MAX_RETRIES = 3; // Maximum number of retries

    private DatabaseConnection() {
        // Private constructor to prevent instantiation
    }

    public static Connection connect() {
        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                logger.info("Database connection successful.");
                return connection;
            } catch (SQLException e) {
                retries++;
                logger.error("Database connection failed: {}. Attempt {}/{}", e.getMessage(), retries, MAX_RETRIES);
                if (retries >= MAX_RETRIES) {
                    logger.error("Max retries reached. Unable to connect to the database.");
                    return null;
                }
                try {
                    Thread.sleep(2000); // Wait before retrying
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    logger.error("Thread interrupted during sleep: {}", ie.getMessage());
                    return null;
                }
            }
        }
        return null;
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed.");
            } catch (SQLException e) {
                logger.error("Failed to close the database connection: {}", e.getMessage());
            }
        }
    }
}