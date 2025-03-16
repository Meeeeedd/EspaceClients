package com.example.espaceclient.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.espaceclient.model.User;
import com.example.espaceclient.model.UserValidator;
import com.example.espaceclient.service.AuthService;
import com.example.espaceclient.utils.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    // FXML Components
    @FXML private TextField nameField, prenomField, emailField, telephoneField;
    @FXML private PasswordField passwordField, confirmPasswordField;
    @FXML private TextField passwordFieldVisible, confirmPasswordFieldVisible;
    @FXML private ProgressBar passwordStrengthBar;
    @FXML private Text nameError, prenomError, emailError, passwordError, confirmPasswordError, telephoneError, feedbackMessage;
    @FXML private ProgressIndicator loadingIndicator;
    @FXML private Button registerButton, loginLink, togglePasswordButton, toggleConfirmPasswordButton;

    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @FXML
    private void initialize() {
        logger.info("Initializing RegisterController...");

        // Initialize password strength bar
        passwordStrengthBar.setVisible(false);

        // Add listeners to clear error messages when user starts typing
        nameField.textProperty().addListener((observable, oldValue, newValue) -> nameError.setText(""));
        prenomField.textProperty().addListener((observable, oldValue, newValue) -> prenomError.setText(""));
        emailField.textProperty().addListener((observable, oldValue, newValue) -> emailError.setText(""));
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> passwordError.setText(""));
        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> confirmPasswordError.setText(""));
        telephoneField.textProperty().addListener((observable, oldValue, newValue) -> telephoneError.setText(""));

        // Add listener for password strength
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                passwordStrengthBar.setVisible(false);
            } else {
                passwordStrengthBar.setVisible(true);
                updatePasswordStrength(newValue);
            }
        });

        // Initialize password visibility
        passwordFieldVisible.setManaged(false);
        passwordFieldVisible.setVisible(false);
        confirmPasswordFieldVisible.setManaged(false);
        confirmPasswordFieldVisible.setVisible(false);
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            clearMessages();

            String name = nameField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String password = isPasswordVisible ? passwordFieldVisible.getText() : passwordField.getText();
            String confirmPassword = isConfirmPasswordVisible ? confirmPasswordFieldVisible.getText() : confirmPasswordField.getText();
            String telephone = telephoneField.getText();

            if (!validateInput(name, prenom, email, password, confirmPassword, telephone)) {
                return;
            }

            showLoading(true);
            new Thread(() -> performRegistration(name, prenom, email, password, telephone)).start();
        } catch (Exception e) {
            logger.error("Unexpected error in handleRegister: ", e);
            feedbackMessage.setText("An unexpected error occurred. Please try again later.");
        }
    }

    private void performRegistration(String name, String prenom, String email, String password, String telephone) {
        try {
            User newUser = new User(name, prenom, email, password, telephone);
            boolean isRegistered = AuthService.registerUser(newUser);

            Platform.runLater(() -> {
                showLoading(false);
                if (isRegistered) {
                    feedbackMessage.setText("Registration successful! Redirecting to login page...");
                    navigateToLogin();
                } else {
                    feedbackMessage.setText("Registration failed. Please try again.");
                }
            });
        } catch (SQLIntegrityConstraintViolationException e) {
            Platform.runLater(() -> {
                showLoading(false);
                feedbackMessage.setText("A user with this email or telephone number already exists.");
            });
            logger.error("Registration error: ", e);
        } catch (Exception e) {
            Platform.runLater(() -> {
                showLoading(false);
                feedbackMessage.setText("An unexpected error occurred. Please try again later.");
            });
            logger.error("Registration error: ", e);
        }
    }

    private void showLoading(boolean isLoading) {
        loadingIndicator.setVisible(isLoading);
        registerButton.setDisable(isLoading);
    }

    private void clearMessages() {
        nameError.setText("");
        prenomError.setText("");
        emailError.setText("");
        passwordError.setText("");
        confirmPasswordError.setText("");
        telephoneError.setText("");
        feedbackMessage.setText("");
    }

    private boolean validateInput(String name, String prenom, String email, String password, String confirmPassword, String telephone) {
        boolean isValid = true;

        if (!UserValidator.isValidName(name)) {
            nameError.setText("Name must be at least 2 characters long and cannot be empty.");
            isValid = false;
        }
        if (!UserValidator.isValidName(prenom)) {
            prenomError.setText("Prenom must be at least 2 characters long and cannot be empty.");
            isValid = false;
        }
        if (!UserValidator.isValidEmail(email)) {
            emailError.setText("Please enter a valid email address (e.g., example@domain.com).");
            isValid = false;
        }
        if (!UserValidator.isValidPassword(password)) {
            passwordError.setText("Password must be at least 8 characters long and include uppercase, lowercase, and numbers.");
            isValid = false;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordError.setText("Passwords do not match.");
            isValid = false;
        }
        if (!UserValidator.isValidPhoneNumber(telephone)) {
            telephoneError.setText("Telephone number must be exactly 8 digits.");
            isValid = false;
        }

        return isValid;
    }

    private void updatePasswordStrength(String password) {
        double strength = UserValidator.calculatePasswordStrength(password);
        passwordStrengthBar.setProgress(strength);
        if (strength < 0.3) {
            passwordStrengthBar.setStyle("-fx-accent: red;");
        } else if (strength < 0.7) {
            passwordStrengthBar.setStyle("-fx-accent: orange;");
        } else {
            passwordStrengthBar.setStyle("-fx-accent: green;");
        }
    }

    @FXML
    private void togglePasswordVisibility(ActionEvent event) {
        if (isPasswordVisible) {
            // Switch to password field
            passwordField.setText(passwordFieldVisible.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordFieldVisible.setVisible(false);
            passwordFieldVisible.setManaged(false);
            togglePasswordButton.setText("👁"); // Eye icon for hidden password
        } else {
            // Switch to text field
            passwordFieldVisible.setText(passwordField.getText());
            passwordFieldVisible.setVisible(true);
            passwordFieldVisible.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            togglePasswordButton.setText("🔒"); // Lock icon for visible password
        }
        isPasswordVisible = !isPasswordVisible;
    }

    @FXML
    private void toggleConfirmPasswordVisibility(ActionEvent event) {
        if (isConfirmPasswordVisible) {
            // Switch to password field
            confirmPasswordField.setText(confirmPasswordFieldVisible.getText());
            confirmPasswordField.setVisible(true);
            confirmPasswordField.setManaged(true);
            confirmPasswordFieldVisible.setVisible(false);
            confirmPasswordFieldVisible.setManaged(false);
            toggleConfirmPasswordButton.setText("👁"); // Eye icon for hidden password
        } else {
            // Switch to text field
            confirmPasswordFieldVisible.setText(confirmPasswordField.getText());
            confirmPasswordFieldVisible.setVisible(true);
            confirmPasswordFieldVisible.setManaged(true);
            confirmPasswordField.setVisible(false);
            confirmPasswordField.setManaged(false);
            toggleConfirmPasswordButton.setText("🔒"); // Lock icon for visible password
        }
        isConfirmPasswordVisible = !isConfirmPasswordVisible;
    }

    private void navigateToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/espaceclient/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.error("Failed to load login page: ", e);
            feedbackMessage.setText("Failed to navigate to the login page. Please try again.");
        }
    }

    @FXML
    private void navigateToLogin(ActionEvent event) {
        navigateToLogin();
    }
}