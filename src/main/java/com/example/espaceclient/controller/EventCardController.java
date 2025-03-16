package com.example.espaceclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class EventCardController {

    @FXML
    private ImageView eventImage;
    @FXML
    private Label eventDateLocation;
    @FXML
    private Label eventTitle;
    @FXML
    private Label eventDescription;
    @FXML
    private Button attendButton;
    @FXML
    private MenuButton optionsButton;

    public void setEventData(String image, String dateLocation, String title, String description) {
        // Set the event data to the UI components
        InputStream imageStream = getClass().getResourceAsStream(image);
        if (imageStream != null) {
            eventImage.setImage(new Image(imageStream));
        } else {
            eventImage.setImage(new Image("C:/Users/ASUS/Desktop/Esprit/Project/EspaceClient/src/main/resources/com/example/espaceclient/assets/false god.jpg")); // Set a default image if the resource is not found
        }
        eventDateLocation.setText(dateLocation);
        eventTitle.setText(title);
        eventDescription.setText(description);
    }

    @FXML
    protected void handleAttendEvent() {
        // Handle event attendance logic
    }

    @FXML
    protected void handleModifyEvent() {
        // Handle event modification logic
    }

    @FXML
    protected void handleDeleteEvent() {
        // Handle event deletion logic
    }
}