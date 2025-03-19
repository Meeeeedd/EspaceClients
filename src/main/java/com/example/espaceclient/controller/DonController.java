package com.example.espaceclient.controller;

import com.example.espaceclient.dao.DonDao;
import com.example.espaceclient.dao.DonDaoImpl;
import com.example.espaceclient.model.Client;
import com.example.espaceclient.model.Don;
import com.example.espaceclient.utils.SessionManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DonController {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Don> tableView;
    @FXML
    private TableColumn<Don, Integer> indexColumn;
    @FXML
    private TableColumn<Don, String> clientNameColumn;
    @FXML
    private TableColumn<Don, BigDecimal> montantColumn;
    @FXML
    private TableColumn<Don, LocalDateTime> dateDonColumn;
    private DonDao donDao;

    public DonController() {
        this.donDao = new DonDaoImpl();
    }

    @FXML
    private void initialize() {
        indexColumn.setCellValueFactory(cellData -> {
            // This sets the index for each item in the table
            return new ReadOnlyObjectWrapper<>(tableView.getItems().indexOf(cellData.getValue()) + 1);
        });

        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        dateDonColumn.setCellValueFactory(new PropertyValueFactory<>("dateDon"));

        dateDonColumn.setCellFactory(column -> new TableCell<Don, LocalDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            @Override
            protected void updateItem(LocalDateTime date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty || date == null ? "" : formatter.format(date));
            }
        });

        // Align columns to center
        alignColumnContentToCenter(indexColumn);
        alignColumnContentToCenter(clientNameColumn);
        alignColumnContentToCenter(montantColumn);
        alignColumnContentToCenter(dateDonColumn);

        loadDons();
    }

    private void loadDons() {
        // Récupérer l'ID du client actuel depuis la session
        Client currentUser = SessionManager.getInstance().getCurrentClient();
        if (currentUser != null) {
            int clientId = currentUser.getId();
            List<Don> dons = donDao.listDonsByClient(clientId);
            dons.forEach(don -> don.setClientName(donDao.findClientNameById(don.getIdClient())));
            ObservableList<Don> observableDons = FXCollections.observableArrayList(dons);
            tableView.setItems(observableDons);
        }
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();
        if (keyword != null && !keyword.isEmpty()) {
            List<Don> dons = donDao.searchDons(keyword);
            ObservableList<Don> observableDons = FXCollections.observableArrayList(dons);
            tableView.setItems(observableDons);
        } else {
            loadDons();
        }
    }

    @FXML
    private void handleAddDon() {
        // Récupérer l'ID du client actuel depuis la session
        Client currentUser = SessionManager.getInstance().getCurrentClient();

        // Créer une nouvelle fenêtre modale
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Ajouter un don");
        dialogStage.initModality(Modality.APPLICATION_MODAL); // Rendre la fenêtre modale

        // Créer les champs du formulaire
        TextField clientNameField = new TextField();
        clientNameField.setPromptText("Nom Client");
        clientNameField.setText(currentUser.getNom());
        clientNameField.getStyleClass().add("text-field");

        TextField montantField = new TextField();
        montantField.setPromptText("Montant");
        montantField.getStyleClass().add("text-field");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Date de Don");
        datePicker.setValue(LocalDate.now());
        datePicker.setPrefWidth(400); // Largeur fixe pour le champ Date
        datePicker.getStyleClass().add("date-picker");

        // Boutons pour valider ou annuler
        Button ajouterButton = new Button("Ajouter");
        Button annulerButton = new Button("Annuler");

        // Appliquer les classes CSS
        ajouterButton.getStyleClass().add("ajouter-button");
        annulerButton.getStyleClass().add("annuler-button");

        // Gérer l'action du bouton "Ajouter"
        ajouterButton.setOnAction(e -> {
            String clientName = clientNameField.getText();
            BigDecimal montant = new BigDecimal(montantField.getText());
            LocalDateTime dateDon = datePicker.getValue().atStartOfDay();

            if (currentUser != null) {
                int clientId = currentUser.getId();
                int idClient = donDao.findClientIdByName(clientName);

                Don don = new Don();
                don.setIdClient(idClient);
                don.setClientName(clientName);
                don.setMontant(montant);
                don.setDateDon(dateDon);
                don.setIdPartenaire(clientId);

                donDao.insertDon(don);

                loadDons();
                dialogStage.close();
            }
        });

        // Gérer l'action du bouton "Annuler"
        annulerButton.setOnAction(e -> dialogStage.close());

        // Créer un HBox pour les boutons et les centrer
        HBox buttonBox = new HBox(10); // Espacement de 10px entre les boutons
        buttonBox.setAlignment(Pos.CENTER); // Centrer les boutons
        buttonBox.getChildren().addAll(annulerButton, ajouterButton);

        // Titre de la fenêtre modale
        Label titleLabel = new Label("Ajouter un don");
        titleLabel.getStyleClass().add("title-label");

        // Centrer le titre dans un HBox
        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER); // Centrer le titre
        titleBox.getChildren().add(titleLabel);

        // Organiser les champs dans un VBox
        VBox vbox = new VBox(15);
        vbox.setPadding(new javafx.geometry.Insets(20));
        vbox.getStyleClass().add("modal-vbox");
        vbox.getChildren().addAll(
                titleBox, // Ajouter le HBox contenant le titre centré
                new Label("Nom Client :"), clientNameField,
                new Label("Montant :"), montantField,
                new Label("Date de Don :"), datePicker,
                buttonBox // Ajouter le HBox des boutons
        );

        // Créer une scène et afficher la fenêtre modale
        Scene scene = new Scene(vbox, 400, 450); // Ajuster la taille de la fenêtre
        scene.getStylesheets().add(getClass().getResource("/com/example/espaceclient/styles/style.css").toExternalForm());

        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    // Méthode pour aligner le contenu d'une colonne au centre
    private <T> void alignColumnContentToCenter(TableColumn<Don, T> column) {
        column.setCellFactory(tc -> new TableCell<Don, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.toString());
                    setGraphic(null);
                    setStyle("-fx-alignment: CENTER;"); // Aligner le texte au centre
                }
            }
        });
    }
}