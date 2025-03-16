package com.example.espaceclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FavorisDetailsController {

    @FXML
    private ImageView imageView;

    @FXML
    private Label titleLabel;

    public void setFavorisData(Image image, String title) {
        imageView.setImage(image);
        titleLabel.setText(title);
    }
}