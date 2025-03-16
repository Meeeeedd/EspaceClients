package com.example.espaceclient.controller;

import com.example.espaceclient.model.DemandeAide;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class DemandeAideListController {

    @FXML
    private GridPane demandeAideListContainer;

    @FXML
    public void initialize() {
        // Load demande aide data (this should be replaced with actual data loading logic)
        List<DemandeAide> demandeAideList = loadDemandeAideData();

        int column = 0;
        int row = 0;

        for (DemandeAide demandeAide : demandeAideList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/espaceclient/DemandeAideCard.fxml"));
                Node demandeAideCard = loader.load();

                DemandeAideCardController controller = loader.getController();
                controller.setDemandeAideData(
                        demandeAide.getTitle(),
                        demandeAide.getDescription(),
                        demandeAide.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        demandeAide.getStatus()
                );

                // Add click event to show details
                demandeAideCard.setOnMouseClicked(event -> showDemandeAideDetails(demandeAide));

                demandeAideListContainer.add(demandeAideCard, column, row);

                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<DemandeAide> loadDemandeAideData() {
        // Dummy data for demonstration purposes
        // Replace this with actual data loading logic
        return List.of(
                new DemandeAide("Need Emotional Support", "I am going through a tough time and need someone to talk to.", LocalDateTime.now().minusDays(2), "Pending"),
                new DemandeAide("Seeking Financial Advice", "Looking for guidance on managing my finances better.", LocalDateTime.now().minusDays(1), "Resolved"),
                new DemandeAide("Help with Work-Life Balance", "Struggling to balance my work and personal life.", LocalDateTime.now().minusHours(5), "In Progress"),
                new DemandeAide("Dealing with Anxiety", "Need help coping with anxiety and stress.", LocalDateTime.now().minusDays(3), "Pending"),
                new DemandeAide("Career Guidance", "Looking for advice on career advancement.", LocalDateTime.now().minusDays(4), "Resolved"),
                new DemandeAide("Relationship Advice", "Need help with relationship issues.", LocalDateTime.now().minusDays(6), "In Progress")
        );
    }

    private void showDemandeAideDetails(DemandeAide demandeAide) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Demande d'Aide Details");
        alert.setHeaderText(demandeAide.getTitle());
        alert.setContentText(
                "Description: " + demandeAide.getDescription() + "\n" +
                        "Date: " + demandeAide.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n" +
                        "Status: " + demandeAide.getStatus()
        );

        ButtonType markAsResolvedButton = new ButtonType("Mark as Resolved");
        alert.getButtonTypes().add(markAsResolvedButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == markAsResolvedButton) {
            demandeAide.setStatus("Resolved");
            // Refresh the list to reflect the status change
            initialize();
        }
    }
}