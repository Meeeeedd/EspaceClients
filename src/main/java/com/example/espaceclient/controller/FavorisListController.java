package com.example.espaceclient.controller;

import com.example.espaceclient.model.Favoris;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FavorisListController {

    @FXML
    private GridPane favorisListContainer;

    @FXML
    private TextField searchField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    private List<Favoris> favorisList = new ArrayList<>();
    private List<Favoris> filteredFavorisList = new ArrayList<>();

    private int column = 0;
    private int row = 0;

    @FXML
    public void initialize() {
        // Load favoris data (this should be replaced with actual data loading logic)
        favorisList = loadFavorisData();
        filteredFavorisList.addAll(favorisList);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterFavoris(newValue));
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> filterFavorisByDate());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> filterFavorisByDate());

        displayFavoris();
    }

    private List<Favoris> loadFavorisData() {
        // Dummy data for demonstration purposes
        // Replace this with actual data loading logic
        return List.of(
                new Favoris(1, "Sample Title 1", "/com/example/espaceclient/assets/false_god.png", LocalDate.now()),
                new Favoris(2, "Sample Title 2", "/com/example/espaceclient/assets/false_god.png", LocalDate.now().minusDays(1)),
                new Favoris(3, "Sample Title 3", "/com/example/espaceclient/assets/false_god.png", LocalDate.now().minusDays(2)),
                new Favoris(4, "Sample Title 4", "/com/example/espaceclient/assets/false_god.png", LocalDate.now().minusDays(3)),
                new Favoris(5, "Sample Title 5", "/com/example/espaceclient/assets/false_god.png", LocalDate.now().minusDays(4)),
                new Favoris(6, "Sample Title 6", "/com/example/espaceclient/assets/false_god.png", LocalDate.now().minusDays(5))
        );
    }

    private void displayFavoris() {
        favorisListContainer.getChildren().clear();
        column = 0;
        row = 0;

        for (Favoris favoris : filteredFavorisList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/espaceclient/FavorisCard.fxml"));
                Node favorisCard = loader.load();

                FavorisCardController controller = loader.getController();
                controller.setFavorisData(favoris.getId(), favoris.getImage(), favoris.getTitle(), this);

                favorisListContainer.add(favorisCard, column, row);

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

    public void removeFavoris(int id) {
        favorisList = favorisList.stream().filter(f -> f.getId() != id).collect(Collectors.toList());
        filterFavoris(searchField.getText());
    }

    private void filterFavoris(String query) {
        filteredFavorisList = favorisList.stream()
                .filter(f -> f.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        displayFavoris();
    }

    private void filterFavorisByDate() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate == null && endDate == null) {
            filteredFavorisList = new ArrayList<>(favorisList);
        } else if (startDate != null && endDate == null) {
            filteredFavorisList = favorisList.stream()
                    .filter(f -> !f.getDate().isBefore(startDate))
                    .collect(Collectors.toList());
        } else if (startDate == null) {
            filteredFavorisList = favorisList.stream()
                    .filter(f -> !f.getDate().isAfter(endDate))
                    .collect(Collectors.toList());
        } else {
            filteredFavorisList = favorisList.stream()
                    .filter(f -> !f.getDate().isBefore(startDate) && !f.getDate().isAfter(endDate))
                    .collect(Collectors.toList());
        }
        displayFavoris();
    }

    @FXML
    private void sortFavorisByTitle() {
        filteredFavorisList.sort((f1, f2) -> f1.getTitle().compareToIgnoreCase(f2.getTitle()));
        displayFavoris();
    }

    @FXML
    private void sortFavorisByDate() {
        filteredFavorisList.sort((f1, f2) -> f1.getDate().compareTo(f2.getDate()));
        displayFavoris();
    }
}