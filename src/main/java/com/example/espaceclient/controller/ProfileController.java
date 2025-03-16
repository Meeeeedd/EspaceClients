package com.example.espaceclient.controller;

import com.example.espaceclient.model.Client;
import com.example.espaceclient.service.UserService;
import com.example.espaceclient.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ProfileController {

    @FXML
    private Label nameLabel, prenomLabel, emailLabel, telephoneLabel, adresseLabel, pointsFideliteLabel, photoCINLabel;
    @FXML
    private ImageView profileImageView;
    @FXML
    private Button uploadButton;

    private Client currentClient = SessionManager.getInstance().getCurrentClient();
    private File selectedImageFile = null;
    private static final String DEFAULT_IMAGE_URL = "https://i.pinimg.com/originals/0d/64/98/0d64989794b1a4c9d89bff571d3d5842.jpg";

    @FXML
    private void initialize() {
        if (currentClient != null) {
            populateClientData(currentClient);
        }
    }

    @FXML
    private void handleEditProfile() {
        navigateTo("EditProfile.fxml");
    }

    @FXML
    private void handleUploadProfilePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Profile Picture");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            selectedImageFile = selectedFile;
            loadImage(selectedImageFile, profileImageView);

            // Save profile image to the database
            String feedbackMessage = UserService.saveProfileImage(currentClient.getIdClient(), selectedImageFile);
            if (!feedbackMessage.startsWith("Profile image updated successfully")) {
                showAlert(Alert.AlertType.ERROR, "File Error", feedbackMessage);
            } else {
                currentClient.setAvatar(selectedImageFile.getAbsolutePath());
                SessionManager.getInstance().setCurrentClient(currentClient);
            }
        }
    }

    private void populateClientData(Client client) {
        nameLabel.setText("Nom: " + client.getNom());
        prenomLabel.setText("Prénom: " + client.getPrenom());
        emailLabel.setText("Email: " + client.getEmail());
        telephoneLabel.setText("Téléphone: " + client.getTelephone());
        adresseLabel.setText("Adresse: " + client.getAdresse());
        pointsFideliteLabel.setText("Points de Fidélité: " + client.getPointsFidelite());
        photoCINLabel.setText("Photo CIN: " + client.getPhotoCIN());
        if (client.getAvatar() != null && !client.getAvatar().isEmpty()) {
            File file = new File(client.getAvatar());
            if (file.exists()) {
                loadImage(file, profileImageView);
            } else {
                loadDefaultImage();
            }
        } else {
            loadDefaultImage();
        }
    }

    private void loadDefaultImage() {
        Image image = new Image(DEFAULT_IMAGE_URL);
        profileImageView.setImage(image);
    }

    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/espaceclient/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) nameLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load the requested page.");
        }
    }

    private void loadImage(File file, ImageView imageView) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            Image image = new Image(inputStream);
            imageView.setImage(image);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "File Error", "Could not load image file.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}