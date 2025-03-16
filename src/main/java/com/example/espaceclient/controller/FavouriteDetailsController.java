package com.example.espaceclient.controller;

import com.example.espaceclient.model.Favourite;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FavouriteDetailsController {
    @FXML
    private Label detailsLabel;

    private Favourite favourite;

    public void setFavourite(Favourite favourite) {
        this.favourite = favourite;
        detailsLabel.setText(favourite.toString());
    }

    @FXML
    public void handleBack() {
        // Handle the back action, e.g., go back to the list view
    }
}