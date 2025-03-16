package com.example.espaceclient.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.espaceclient.service.AuthService;
import com.example.espaceclient.service.AuthService.AuthResponse;
import com.example.espaceclient.utils.SessionManager;
import com.example.espaceclient.model.Client;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField passwordTextField;
    @FXML private Button loginButton;
    @FXML private CheckBox rememberMeCheckBox;
    @FXML private Text loginFeedback;
    @FXML private ProgressIndicator loadingIndicator;
    @FXML private Text emailError;
    @FXML private Text passwordError;
    @FXML private Button toggleButton;

    private boolean isPasswordVisible = false;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @FXML
    private void initialize() {
        // Add listeners to clear error messages when user starts typing
        emailField.textProperty().addListener((observable, oldValue, newValue) -> clearError(emailError));
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> clearError(passwordError));
        passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> clearError(passwordError));
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = isPasswordVisible ? passwordTextField.getText() : passwordField.getText();

        if (!validateInput(email, password)) {
            return;
        }

        showLoading(true);

        new Thread(() -> {
            AuthResponse response = AuthService.loginUser(email, password);

            Platform.runLater(() -> {
                showLoading(false);

                if (response.isSuccess()) {
                    Client currentClient = AuthService.getUserAndClientDetails(email);
                    if (currentClient != null) {
                        SessionManager.getInstance().setCurrentClient(currentClient);
                        showFeedback(response.getMessage(), Alert.AlertType.INFORMATION);
                        redirectToMainPage();
                    } else {
                        showFeedback("Failed to retrieve client details.", Alert.AlertType.ERROR);
                    }
                } else {
                    showFeedback(response.getMessage(), Alert.AlertType.ERROR);
                }
            });
        }).start();
    }

    private boolean validateInput(String email, String password) {
        if (!isValidEmail(email)) {
            emailError.setText("Invalid email. Please enter a valid email address.");
            return false;
        }

        if (password.isEmpty()) {
            passwordError.setText("Password cannot be empty.");
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    private void showFeedback(String message, Alert.AlertType alertType) {
        loginFeedback.setText(message);
        loginFeedback.setStyle(alertType == Alert.AlertType.ERROR ? "-fx-fill: red;" : "-fx-fill: green;");
    }

    @FXML
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            switchToPasswordField();
        } else {
            switchToTextField();
        }
        isPasswordVisible = !isPasswordVisible;
    }

    private void switchToPasswordField() {
        passwordField.setText(passwordTextField.getText());
        passwordField.setVisible(true);
        passwordField.setManaged(true);
        passwordTextField.setVisible(false);
        passwordTextField.setManaged(false);
        toggleButton.setText("👁");
    }

    private void switchToTextField() {
        passwordTextField.setText(passwordField.getText());
        passwordTextField.setVisible(true);
        passwordTextField.setManaged(true);
        passwordField.setVisible(false);
        passwordField.setManaged(false);
        toggleButton.setText("🔒");
    }

    @FXML
    private void handleForgotPassword() {
        showFeedback("Forgot password functionality is not implemented yet.", Alert.AlertType.INFORMATION);
    }

    private void redirectToMainPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/espaceclient/Main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Main Page");
            stage.show();
        } catch (IOException e) {
            showFeedback("Error loading the main page.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/espaceclient/Register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Register");
            stage.show();
        } catch (IOException e) {
            showFeedback("Error loading the registration page.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showLoading(boolean isLoading) {
        loadingIndicator.setVisible(isLoading);
        loginButton.setDisable(isLoading);
    }

    private void clearError(Text errorText) {
        errorText.setText("");
    }
}