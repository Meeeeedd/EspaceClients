package com.example.espaceclient.controller;

import com.example.espaceclient.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SidebarController {

    @FXML
    private VBox sidebar;

    @FXML
    private Button toggleSidebarButton;

    private boolean isSidebarCollapsed = false;

    @FXML
    private void toggleSidebar() {
        if (isSidebarCollapsed) {
            sidebar.setPrefWidth(200);
            toggleSidebarButton.setText("☰");
        } else {
            sidebar.setPrefWidth(130);
            toggleSidebarButton.setText("☷");
        }
        isSidebarCollapsed = !isSidebarCollapsed;
    }

    @FXML
    private void navigateHome() {
        loadScreen("/com/example/espaceclient/welcome.fxml");
    }

    @FXML
    private void navigateProfile() {
        loadScreen("/com/example/espaceclient/profile.fxml");
    }

    public void navigateEvennements() {
        loadScreen("/com/example/espaceclient/EvenementView.fxml");
    }

    @FXML
    private void navigateBenevolats() {
        loadScreen("/com/example/espaceclient/volunteering.fxml");
    }

    @FXML
    private void navigateExperiences() {
        loadScreen("/com/example/espaceclient/experiences.fxml");
    }

    @FXML
    private void navigateDemandeAide() {
        loadScreen("/com/example/espaceclient/DemandeAideList.fxml");
    }

    @FXML
    private void navigateDons() {
        loadScreen("/com/example/espaceclient/DonView.fxml");
    }

    @FXML
    private void navigateFavoris() {
        loadScreen("/com/example/espaceclient/FavorisList.fxml");
    }

    @FXML
    private void navigateFavoris2() {
        loadScreen("/com/example/espaceclient/FavouriteList.fxml");
    }

    @FXML
    private void navigateParameters() {
        loadScreen("/com/example/espaceclient/settings.fxml");
    }

    private void loadScreen(String fxml) {
        try {
            MainController mainController = MainController.getInstance();
            mainController.loadScreen(fxml);
        } catch (Exception e) {
            System.err.println("Error loading screen: " + fxml);
            e.printStackTrace();
        }
    }

    @FXML
    private void logout() {
        SessionManager.getInstance().logout();
        redirectToLoginPage();
    }

    private void redirectToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/espaceclient/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) sidebar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}