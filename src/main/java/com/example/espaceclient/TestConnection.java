package com.example.espaceclient;

import com.example.espaceclient.utils.DatabaseConnection;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.connect();
        if (conn != null) {
            System.out.println("Connected to database successfully!");
        }
    }
}
