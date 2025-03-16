package com.example.espaceclient.controller;

import com.example.espaceclient.model.Favourite;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class FavouriteCardController {
    @FXML
    private Label favouriteLabel;

    private Favourite favourite;
    private FavouriteListController parentController;

    public void setFavourite(Favourite favourite) {
        this.favourite = favourite;
        favouriteLabel.setText(favourite.getTypeEntite() + " - " + favourite.getIdEntite());
    }

    public void setParentController(FavouriteListController parentController) {
        this.parentController = parentController;
    }

    @FXML
    public void handleRemoveFavourite() {
        parentController.removeFavourite(favourite.getIdFavourite());
    }

    @FXML
    public void handleCardClick(MouseEvent event) {
        parentController.showFavouriteDetails(favourite);
    }
}