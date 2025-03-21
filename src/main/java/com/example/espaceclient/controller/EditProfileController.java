package com.example.espaceclient.controller;

import com.example.espaceclient.model.Client;
import com.example.espaceclient.utils.PasswordUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.espaceclient.model.UserValidator;
import com.example.espaceclient.service.UserService;
import com.example.espaceclient.utils.SessionManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

public class EditProfileController {

    @FXML private TextField nameField, prenomField, emailField, telephoneField, adresseField;
    @FXML private PasswordField passwordField, confirmPasswordField;
    @FXML private TextField passwordTextField, confirmPasswordTextField;
    @FXML private ProgressBar passwordStrengthBar;
    @FXML private Label passwordStrengthLabel, feedbackLabel;
    @FXML private Button togglePasswordButton, uploadImageButton;
    @FXML private ImageView profileImageView;

    private boolean isPasswordVisible = false;
    private File selectedImageFile = null;

    @FXML
    private void initialize() {
        // Load client data from session
        Client currentClient = SessionManager.getInstance().getCurrentClient();
        if (currentClient != null) {
            populateClientData(currentClient);
        }

        // Initialize the progress bar
        passwordStrengthBar.setProgress(0); // Set initial progress to 0
        passwordStrengthLabel.setText(""); // Clear the label initially

        // Add password strength listener
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> updatePasswordStrength(newValue));
        passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> updatePasswordStrength(newValue));
    }

    private void populateClientData(Client client) {
        // Populate fields with client data
        nameField.setText(client.getNom());
        prenomField.setText(client.getPrenom());
        emailField.setText(client.getEmail());
        telephoneField.setText(client.getTelephone());
        adresseField.setText(client.getAdresse());

        // Load profile image if available
        if (client.getAvatar() != null && !client.getAvatar().isEmpty()) {
            File file = new File(client.getAvatar());
            if (file.exists()) {
                loadImage(file, profileImageView);
            }
        }

        // Do not display the actual password (for security reasons)
        passwordField.setText(""); // Leave password field empty
        passwordTextField.setText(""); // Leave password text field empty
        confirmPasswordField.setText(""); // Leave confirm password field empty
        confirmPasswordTextField.setText(""); // Leave confirm password text field empty
    }

    @FXML
    private void handleSaveProfile() {
        Client currentClient = SessionManager.getInstance().getCurrentClient();
        if (currentClient == null) {
            showFeedback("Aucun client connecté.", "error");
            return;
        }

        // Validate inputs
        String name = nameField.getText() != null ? nameField.getText().trim() : "";
        String prenom = prenomField.getText() != null ? prenomField.getText().trim() : "";
        String email = emailField.getText() != null ? emailField.getText().trim() : "";
        String phone = telephoneField.getText() != null ? telephoneField.getText().trim() : "";
        String adresse = adresseField.getText() != null ? adresseField.getText().trim() : "";
        String password = passwordField.getText() != null ? passwordField.getText().trim() : "";
        String confirmPassword = confirmPasswordField.getText() != null ? confirmPasswordField.getText().trim() : "";

        // Check if any changes were made
        if (name.equals(currentClient.getNom()) &&
                prenom.equals(currentClient.getPrenom()) &&
                email.equals(currentClient.getEmail()) &&
                phone.equals(currentClient.getTelephone()) &&
                adresse.equals(currentClient.getAdresse()) &&
                password.isEmpty() && confirmPassword.isEmpty() &&
                selectedImageFile == null) {
            showFeedback("Aucune modification n'a été effectuée.", "info");
            return;
        }

        // Validate inputs
        if (!UserValidator.isValidName(name) || !UserValidator.isValidName(prenom)) {
            showFeedback("Le nom et le prénom doivent comporter au moins 2 caractères.", "error");
            return;
        }

        if (!UserValidator.isValidEmail(email)) {
            showFeedback("Adresse e-mail invalide.", "error");
            return;
        }

        if (!UserValidator.isValidPhoneNumber(phone)) {
            showFeedback("Le numéro de téléphone doit comporter 8 chiffres.", "error");
            return;
        }

        if (!password.isEmpty() && !UserValidator.isValidPassword(password)) {
            showFeedback("Le mot de passe doit comporter au moins 8 caractères, incluant des majuscules, des minuscules et un chiffre.", "error");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showFeedback("Les mots de passe ne correspondent pas.", "error");
            return;
        }

        // Update client data
        currentClient.setNom(name);
        currentClient.setPrenom(prenom);
        currentClient.setEmail(email);
        currentClient.setTelephone(phone);
        currentClient.setAdresse(adresse);

        if (!password.isEmpty()) {
            currentClient.setMotDePasse(PasswordUtils.hashPassword(password)); // Hash password
        }

        // Save profile image if selected
        if (selectedImageFile != null) {
            String feedbackMessage = UserService.saveProfileImage(currentClient.getIdClient(), selectedImageFile);
            if (!feedbackMessage.startsWith("Image de profil mise à jour avec succès")) {
                showFeedback(feedbackMessage, "error");
                return;
            }
            currentClient.setAvatar(selectedImageFile.getAbsolutePath());
        }

        // Save to database and get feedback message
        try {
            String feedbackMessage = UserService.saveUser(currentClient);
            if (feedbackMessage.startsWith("Profil mis à jour avec succès")) {
                SessionManager.getInstance().setCurrentClient(currentClient);
                showFeedback(feedbackMessage, "success");
                // Navigate back to Main.fxml and load Profile.fxml
                navigateToMainAndLoadProfile();
            } else {
                showFeedback(feedbackMessage, "error");
            }
        } catch (Exception e) {
            showFeedback("Erreur inattendue lors de la mise à jour du profil.", "error");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUploadProfilePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Télécharger une photo de profil");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            selectedImageFile = selectedFile;
            loadImage(selectedImageFile, profileImageView);
        }
    }

    private void loadImage(File file, ImageView imageView) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            Image image = new Image(inputStream);
            imageView.setImage(image);
        } catch (IOException e) {
            showFeedback("Erreur lors du chargement du fichier image.", "error");
            e.printStackTrace();
        }
    }

    private void showFeedback(String message, String type) {
        feedbackLabel.setText(message);
        switch (type) {
            case "error":
                feedbackLabel.setStyle("-fx-text-fill: #FF4444;");
                break;
            case "success":
                feedbackLabel.setStyle("-fx-text-fill: #2D9596;");
                break;
            case "info":
                feedbackLabel.setStyle("-fx-text-fill: #265073;");
                break;
        }
    }

    private void navigateToMainAndLoadProfile() {
        try {
            // Load Main.fxml
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/com/example/espaceclient/Main.fxml"));
            Parent root = mainLoader.load();
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));

            // Load Profile.fxml into the main content area
            MainController mainController = mainLoader.getController();
            mainController.loadScreen("/com/example/espaceclient/Profile.fxml");
        } catch (IOException e) {
            showFeedback("Erreur lors de la navigation vers la page principale.", "error");
            e.printStackTrace();
        }
    }

    @FXML
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Switch to PasswordField
            passwordField.setText(passwordTextField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordTextField.setVisible(false);
            passwordTextField.setManaged(false);

            confirmPasswordField.setText(confirmPasswordTextField.getText());
            confirmPasswordField.setVisible(true);
            confirmPasswordField.setManaged(true);
            confirmPasswordTextField.setVisible(false);
            confirmPasswordTextField.setManaged(false);

            togglePasswordButton.setText("👁");
        } else {
            // Switch to TextField
            passwordTextField.setText(passwordField.getText());
            passwordTextField.setVisible(true);
            confirmPasswordTextField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);

            confirmPasswordTextField.setText(confirmPasswordField.getText());
            confirmPasswordTextField.setVisible(true);
            confirmPasswordTextField.setManaged(true);
            confirmPasswordField.setVisible(false);
            confirmPasswordField.setManaged(false);

            togglePasswordButton.setText("🔒");
        }
        isPasswordVisible = !isPasswordVisible;
    }

    private void updatePasswordStrength(String password) {
        double strength = calculatePasswordStrength(password);
        passwordStrengthBar.setProgress(strength);

        // Dynamically set the style based on password strength
        if (strength < 0.4) {
            passwordStrengthBar.setStyle("-fx-accent: #ff4444;"); // Red for weak
            passwordStrengthLabel.setText("Faible");
        } else if (strength < 0.7) {
            passwordStrengthBar.setStyle("-fx-accent: #ffa500;"); // Orange for medium
            passwordStrengthLabel.setText("Moyen");
        } else {
            passwordStrengthBar.setStyle("-fx-accent: #2D9596;"); // Teal for strong
            passwordStrengthLabel.setText("Fort");
        }
    }

    private double calculatePasswordStrength(String password) {
        int strength = 0;

        // Check password length
        if (password.length() >= 8) strength++;
        if (password.length() >= 12) strength++;

        // Check for uppercase letters
        if (password.matches(".*[A-Z].*")) strength++;

        // Check for lowercase letters
        if (password.matches(".*[a-z].*")) strength++;

        // Check for numbers
        if (password.matches(".*\\d.*")) strength++;

        // Check for special characters
        if (password.matches(".*[!@#$%^&*()].*")) strength++;

        // Normalize strength to a value between 0 and 1
        return strength / 5.0;
    }

    @FXML
    private void handleBack() {
        navigateTo("Main.fxml");
    }

    private void navigateTo(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/espaceclient/" + fxmlFile)));
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}