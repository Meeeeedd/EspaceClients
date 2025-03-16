package com.example.espaceclient.controller;

import com.example.espaceclient.model.Favourite;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class FavouriteDetailsController {
    @FXML
    private Label detailsLabel;

    private Favourite favourite;
    private BorderPane mainPane;

    public void setFavourite(Favourite favourite) {
        this.favourite = favourite;
        detailsLabel.setText("Type: " + favourite.getTypeEntite() + "\nID: " + favourite.getIdEntite());
    }

    public void setMainPane(BorderPane mainPane) {
        this.mainPane = mainPane;
    }

    @FXML
    public void handleBack() {
        // Go back to the list view
        mainPane.setCenter(null);
    }
}