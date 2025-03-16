package com.example.espaceclient.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import com.example.espaceclient.utils.SessionManager;

public class NavbarController {

    @FXML
    private ImageView logo;

    @FXML
    private Label navbarTitle;

    @FXML
    private TextField searchBar;

    @FXML
    private Button notificationButton;

    @FXML
    private ToggleButton darkModeToggle;

    @FXML
    private MenuButton profileMenuButton;

    @FXML
    private MenuItem profileMenuItem;

    @FXML
    private MenuItem logoutMenuItem;

    @FXML
    private void toggleDarkMode() {
        MainController mainController = MainController.getInstance();
        mainController.toggleDarkMode();
    }

    @FXML
    private void navigateProfile() {
        MainController mainController = MainController.getInstance();
        mainController.loadScreen("/com/example/espaceclient/profile.fxml");
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
            Stage stage = (Stage) profileMenuButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}