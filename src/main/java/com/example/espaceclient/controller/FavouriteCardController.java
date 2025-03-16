package com.example.espaceclient.controller;

import com.example.espaceclient.model.Favourite;
import com.example.espaceclient.service.FavouriteService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FavouriteCardController {
    @FXML
    private Label favouriteLabel;

    private FavouriteService favouriteService = new FavouriteService();

    private Favourite favourite;

    public void setFavourite(Favourite favourite) {
        this.favourite = favourite;
        favouriteLabel.setText(favourite.toString());
    }

    @FXML
    public void handleRemoveFavourite() {
        favouriteService.removeFavourite(favourite.getIdFavourite());
        // Update the UI accordingly
    }
}