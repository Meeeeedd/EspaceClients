package com.example.espaceclient.model;

public class UserValidator {

    public static boolean isValid(User user) {
        return isValidName(user.getNom()) &&
                isValidName(user.getPrenom()) &&
                isValidEmail(user.getEmail()) &&
                isValidPassword(user.getMotDePasse()) &&
                isValidPhoneNumber(user.getTelephone());
    }

    public static boolean isValidName(String name) {
        return name != null && name.trim().length() >= 2;
    }

    public static boolean isValidEmail(String email) {
        // Improved email validation pattern
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }

    public static boolean isValidPassword(String password) {
        // Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one number
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && password.matches(passwordRegex);
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{8}");
    }

    public static double calculatePasswordStrength(String password) {
        int strength = 0;
        if (password.length() >= 8) strength++;
        if (password.matches(".*[A-Z].*")) strength++;
        if (password.matches(".*[a-z].*")) strength++;
        if (password.matches(".*\\d.*")) strength++;
        if (password.matches(".*[!@#$%^&*()].*")) strength++;

        return strength / 5.0;
    }
}