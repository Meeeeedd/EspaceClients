package com.example.espaceclient.controller;

import com.example.espaceclient.model.Donation;
import com.example.espaceclient.service.DonationService;
import com.example.espaceclient.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.List;

public class DonationsController {

    @FXML
    private ListView<String> donationsList;

    private DonationService donationService = new DonationService();

    @FXML
    public void initialize() {
        if (SessionManager.getInstance().getCurrentClient() == null) {
            showAlert("Error", "User is not logged in. Please log in to view donations.");
            return;
        }
        loadDonations();
    }

    private void loadDonations() {
        int userId = SessionManager.getInstance().getCurrentClient().getId();
        List<Donation> donations = donationService.getDonationsByUser(userId);

        for (Donation donation : donations) {
            donationsList.getItems().add("Donation ID: " + donation.getIdDon() +
                    ", Amount: " + donation.getMontant() +
                    ", Date: " + donation.getDateDon() +
                    ", Status: " + donation.getStatus());
        }
    }

    @FXML
    private void handleDonate() {
        // Handle donate button click
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}