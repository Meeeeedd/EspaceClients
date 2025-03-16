package com.example.espaceclient.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.espaceclient.utils.SessionManager;

import java.io.IOException;

public class MainController {

    @FXML
    private VBox sidebar;

    @FXML
    private StackPane mainContent;

    @FXML
    private Button toggleSidebarButton;

    @FXML
    private MenuButton profileMenuButton;

    private boolean isSidebarCollapsed = false;

    private static MainController instance;

    public MainController() {
        instance = this;
    }

    public static MainController getInstance() {
        return instance;
    }

    @FXML
    private void toggleSidebar() {
        if (isSidebarCollapsed) {
            sidebar.setPrefWidth(200);
            toggleSidebarButton.setText("☰");
        } else {
            sidebar.setPrefWidth(90);
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
    private void navigateBenevolats(ActionEvent event) {
        loadScreen("/com/example/espaceclient/volunteering.fxml");
    }

    @FXML
    private void navigateExperiences(ActionEvent event) {
        loadScreen("/com/example/espaceclient/experiences.fxml");
    }

    @FXML
    private void navigateDemandeAide(ActionEvent event) {
        loadScreen("/com/example/espaceclient/DemandeAideList.fxml");
    }

    @FXML
    private void navigateDons(ActionEvent event) {
        loadScreen("/com/example/espaceclient/donations.fxml");
    }

    @FXML
    private void navigateFavoris(ActionEvent event) {
        loadScreen("/com/example/espaceclient/FavorisList.fxml");
    }

    @FXML
    private void navigateParameters(ActionEvent event) {
        loadScreen("/com/example/espaceclient/settings.fxml");
    }

    public void loadScreen(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Node screen = loader.load();
            mainContent.getChildren().setAll(screen);
        } catch (IOException e) {
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
            Stage stage = (Stage) mainContent.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void toggleDarkMode() {
        Scene scene = mainContent.getScene();
        scene.getStylesheets().clear();
        if (scene.getStylesheets().contains(getClass().getResource("/com/example/espaceclient/styles/dark-mode.css").toExternalForm())) {
            scene.getStylesheets().remove(getClass().getResource("/com/example/espaceclient/styles/dark-mode.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/com/example/espaceclient/styles/main.css").toExternalForm());
        } else {
            scene.getStylesheets().remove(getClass().getResource("/com/example/espaceclient/styles/main.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/com/example/espaceclient/styles/dark-mode.css").toExternalForm());
        }
    }
}