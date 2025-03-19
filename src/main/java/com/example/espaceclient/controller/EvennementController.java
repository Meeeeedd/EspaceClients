package com.example.espaceclient.controller;

import com.example.espaceclient.dao.EvenementDAOImpl;
import com.example.espaceclient.model.Client;
import com.example.espaceclient.model.Evenement;
import com.example.espaceclient.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class EvennementController {
    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private TextField searchField;

    @FXML
    private VBox cardContainer; // Conteneur pour les cartes d'événements

    @FXML
    private Button addButton; // Bouton "Ajouter"

    private EvenementDAOImpl eventDao;
    private File selectedFile;

    @FXML
    public void initialize() {
        eventDao = new EvenementDAOImpl();

        // Récupérer l'ID du partenaire actuel depuis la session
        Client currentUser = SessionManager.getInstance().getCurrentClient();
        if (currentUser != null) {
            int idClient = currentUser.getIdClient();
            List<Evenement> evenements = eventDao.getEvenementsByClientId(idClient);
            afficherEvenements(evenements);
        } else {
            System.err.println("Erreur : l'utilisateur courant est null.");
        }
    }

    private void afficherEvenements(List<Evenement> evenements) {
        cardContainer.getChildren().clear(); // Effacer les cartes existantes

        for (Evenement evenement : evenements) {
            // Créer une carte pour chaque événement
            HBox eventCard = new HBox(10); // Espacement de 10px entre l'image et le texte
            eventCard.getStyleClass().add("event-card");

            // Associer l'objet Event à la carte
            eventCard.setUserData(evenement);

            // Ajouter une image (si disponible)
            String imageName = evenement.getImageName();
            ImageView eventImageView = new ImageView();
            eventImageView.setFitWidth(100);
            eventImageView.setFitHeight(100);
            if (imageName != null && !imageName.isEmpty()) {
                File imageFile = new File("uploads/" + imageName);
                System.out.println("Chemin de l'image: " + imageFile.getAbsolutePath());
                // Ajout d'un message de journalisation
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    eventImageView.setImage(image);
                    System.out.println("Image affichée pour l'événement: " + evenement.getTitre());
                } else {
                    System.out.println("Le fichier image n'existe pas: " + imageFile.getAbsolutePath());
                }
            } else {
                System.out.println("Aucune image pour l'événement: " + evenement.getTitre());
            }
            eventCard.getChildren().add(eventImageView);

            // Ajouter les détails de l'événement
            VBox detailsBox = new VBox(5); // Espacement de 5px entre les éléments de texte
            detailsBox.getStyleClass().add("event-details");

            // HBox pour aligner le titre et le MenuButton
            HBox titleBox = new HBox(10);
            titleBox.setAlignment(Pos.CENTER_LEFT);

            Label titleLabel = new Label(evenement.getTitre());
            titleLabel.getStyleClass().add("event-title");

            Label descriptionLabel = new Label(evenement.getDescription());
            descriptionLabel.getStyleClass().add("event-description");
            descriptionLabel.setWrapText(true);

            Label dateLabel = new Label(evenement.getDateEvenement().toString());
            dateLabel.getStyleClass().add("event-date");

            Label lieuLabel = new Label(evenement.getLieu());
            lieuLabel.getStyleClass().add("event-lieu");

            Label statusLabel = new Label(evenement.getStatus());
            statusLabel.getStyleClass().add("event-status");

            detailsBox.getChildren().addAll(titleBox, descriptionLabel, dateLabel, lieuLabel, statusLabel);
            eventCard.getChildren().add(detailsBox);

            // Ajouter la carte au conteneur
            cardContainer.getChildren().add(eventCard);
        }
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();
        List<Evenement> evenements = eventDao.searchEvents(keyword);
        afficherEvenements(evenements);
    }

    private String saveImageToUploads(File sourceFile) throws IOException {
        String uploadsDir = "uploads";
        File uploadsDirectory = new File(uploadsDir);
        if (!uploadsDirectory.exists()) {
            uploadsDirectory.mkdirs(); // Crée le dossier s'il n'existe pas
        }

        // Générer un nom de fichier unique pour éviter les conflits
        String fileName = sourceFile.getName();
        File destFile = new File(uploadsDirectory, fileName);

        // Copier le fichier dans le dossier uploads
        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Retourner uniquement le nom du fichier
        System.out.println("Image enregistrée: " + fileName); // Ajout d'un message de journalisation
        return fileName;
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour récupérer l'événement à partir de la carte
    private Evenement getEventFromCard(HBox eventCard) {
        // Récupérer l'objet Event associé à la carte via setUserData
        return (Evenement) eventCard.getUserData();
    }

}
