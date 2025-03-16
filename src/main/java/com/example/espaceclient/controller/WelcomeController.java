package com.example.espaceclient.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private Label welcomeLabel;

    @FXML
    protected void handleGetStarted() {
        welcomeLabel.setText("Welcome to client space!");
    }

    @FXML
    protected void showPersonalInfo() {
        navigateTo("profile.fxml");
    }

    @FXML
    protected void showFavorites() {
        navigateTo("FavorisList.fxml");
    }

    @FXML
    protected void showDonations() {
        navigateTo("donations.fxml");
    }

    @FXML
    protected void showVolunteering() {
        navigateTo("volunteering.fxml");
    }

    @FXML
    protected void showHelpRequests() {
        navigateTo("DemandeAideList.fxml");
    }

    @FXML
    protected void showEvents() {
        navigateTo("EventList.fxml");
    }

    @FXML
    protected void showExperiences() {
        navigateTo("experiences.fxml");
    }

    @FXML
    protected void showSettings() {
        navigateTo("settings.fxml");
    }

    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/espaceclient/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}