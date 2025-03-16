package com.example.espaceclient.controller;

import com.example.espaceclient.model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EventListController {

    @FXML
    private VBox eventListContainer;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private TextField searchTitleField;

    @FXML
    private TextField searchDescriptionField;

    @FXML
    private DatePicker searchDatePicker;

    private ObservableList<Event> events;

    @FXML
    public void initialize() {
        // Load event data (this should be replaced with actual data loading logic)
        events = FXCollections.observableArrayList(loadEventData());

        // Sort events by date (default sorting)
        events.sort((e1, e2) -> e1.getDateLocation().compareTo(e2.getDateLocation()));

        displayEvents(events);

        filterComboBox.setOnAction(event -> handleSort());
        searchTitleField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());
        searchDescriptionField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());
        searchDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> handleSearch());
    }

    private List<Event> loadEventData() {
        // Dummy data for demonstration purposes
        // Replace this with actual data loading logic
        return List.of(
                new Event("/com/example/espaceclient/assets/false_god.png", "March 15, 2025 - New York", "Event Title 1", "Event Description 1"),
                new Event("/com/example/espaceclient/assets/false_god.png", "March 10, 2025 - Los Angeles", "Event Title 2", "Event Description 2")
        );
    }

    private void displayEvents(List<Event> events) {
        eventListContainer.getChildren().clear();
        for (Event event : events) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/espaceclient/EventCard.fxml"));
                Node eventCard = loader.load();

                EventCardController controller = loader.getController();
                controller.setEventData(event.getImage(), event.getDateLocation(), event.getTitle(), event.getDescription());

                eventListContainer.getChildren().add(eventCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleAjouter(ActionEvent event) {
        // Logic to handle adding new events
        // This can be a new scene or a pop-up to enter event details
        System.out.println("Ajouter button clicked");
    }

    @FXML
    private void handleSort() {
        String selectedFilter = filterComboBox.getValue();
        if (selectedFilter != null) {
            switch (selectedFilter) {
                case "Trier par date de depart":
                    events.sort((e1, e2) -> e1.getDateLocation().compareTo(e2.getDateLocation()));
                    break;
                case "Trier par nom":
                    events.sort((e1, e2) -> e1.getTitle().compareToIgnoreCase(e2.getTitle()));
                    break;
                default:
                    break;
            }
            displayEvents(events);
        }
    }

    @FXML
    private void handleSearch() {
        String titleKeyword = searchTitleField.getText().toLowerCase();
        String descriptionKeyword = searchDescriptionField.getText().toLowerCase();
        LocalDate date = searchDatePicker.getValue();

        List<Event> filteredEvents = events.stream()
                .filter(event -> (titleKeyword.isEmpty() || event.getTitle().toLowerCase().contains(titleKeyword)) &&
                        (descriptionKeyword.isEmpty() || event.getDescription().toLowerCase().contains(descriptionKeyword)) &&
                        (date == null || event.getDateLocation().contains(date.toString())))
                .collect(Collectors.toList());
        displayEvents(filteredEvents);
    }
}