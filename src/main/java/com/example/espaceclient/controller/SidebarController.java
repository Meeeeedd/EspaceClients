package com.example.espaceclient.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

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

    @FXML
    private void navigateEvennements() {
        loadScreen("/com/example/espaceclient/EventList.fxml");
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
        loadScreen("/com/example/espaceclient/donations.fxml");
    }

    @FXML
    public void navigateDons2() {
        loadScreen("/com/example/espaceclient/Dons.fxml");
    }
    @FXML
    private void navigateFavoris() {
        loadScreen("/com/example/espaceclient/FavorisList.fxml");
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


}