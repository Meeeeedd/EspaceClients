package com.example.espaceclient.controller;

import com.example.espaceclient.model.Dons;
import com.example.espaceclient.service.DonsService;
import com.example.espaceclient.utils.SessionManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class DonsFXController {
    private static final Logger logger = LoggerFactory.getLogger(DonsFXController.class);

    @FXML
    private TableView<Dons> donsTable;

    @FXML
    private TableColumn<Dons, Integer> idDonColumn;

    @FXML
    private TableColumn<Dons, Integer> idClientColumn;

    @FXML
    private TableColumn<Dons, Integer> idPartenaireColumn;

    @FXML
    private TableColumn<Dons, BigDecimal> montantColumn;

    @FXML
    private TableColumn<Dons, LocalDateTime> dateDonColumn;

    @FXML
    private TableColumn<Dons, String> statusColumn;

    @FXML
    private TextField amountField;

    @FXML
    private GridPane donationForm;

    private final DonsService donsService = new DonsService();

    @FXML
    public void initialize() {
        if (SessionManager.getInstance().getCurrentClient() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No client is logged in. Please log in and try again.");
            return;
        }

        setupTableColumns();
        refreshDonationsTable();
    }

    private void setupTableColumns() {
        idDonColumn.setCellValueFactory(new PropertyValueFactory<>("idDon"));
        idClientColumn.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        idPartenaireColumn.setCellValueFactory(new PropertyValueFactory<>("idPartenaire"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        dateDonColumn.setCellValueFactory(new PropertyValueFactory<>("dateDon"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @FXML
    private void handleDonateButtonAction() {
        donationForm.setVisible(true);
    }

    @FXML
    private void handleSubmitDonationAction() {
        String amountText = amountField.getText().trim();
        if (amountText.isEmpty()) {
            handleValidationError("Please enter a donation amount.");
            return;
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(amountText);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                handleValidationError("Donation amount must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            handleValidationError("Invalid amount. Please enter a valid number.");
            return;
        }

        createDonation(amount);
    }

    private void handleValidationError(String message) {
        showAlert(Alert.AlertType.ERROR, "Validation Error", message);
        amountField.requestFocus();
        amountField.setStyle("-fx-border-color: red;");
    }

    private void createDonation(BigDecimal amount) {
        if (SessionManager.getInstance().getCurrentClient() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No client is logged in. Please log in and try again.");
            return;
        }

        Dons don = new Dons();
        don.setIdClient(SessionManager.getInstance().getCurrentClient().getIdClient());
        don.setMontant(amount);
        don.setDateDon(LocalDateTime.now());
        don.setStatus("en attente");

        try {
            donsService.createDon(don);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Donation successful!");
            resetForm();
            refreshDonationsTable();
        } catch (Exception e) {
            logger.error("Error creating donation: {}", e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while processing your donation. Please try again.");
        }
    }

    private void resetForm() {
        amountField.clear();
        amountField.setStyle(null);
        donationForm.setVisible(false);
    }

    private void refreshDonationsTable() {
        try {
            List<Dons> donsList = donsService.getDonsByClientId(SessionManager.getInstance().getCurrentClient().getIdClient());
            donsTable.setItems(FXCollections.observableArrayList(donsList));
        } catch (Exception e) {
            logger.error("Error fetching donations: {}", e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while fetching donations. Please try again.");
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