package com.example.espaceclient.tests;

import com.example.espaceclient.model.User;
import com.example.espaceclient.service.AuthService;

import java.sql.SQLIntegrityConstraintViolationException;

public class TestAuth {
    public static void main(String[] args) throws SQLIntegrityConstraintViolationException {
        User user = new User("Ben Nayma", "Mohamed", "MedBenNayma2025@example.com", "1234", "25577542");
        boolean registered = AuthService.registerUser(user);
        if (registered) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Registration failed!");
        }
    }
}
