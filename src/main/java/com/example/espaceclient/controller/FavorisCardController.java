package com.example.espaceclient.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class FavorisCardController {

    @FXML
    private ImageView heartIcon;

    @FXML
    private ImageView favorisImage;

    @FXML
    private Label favorisTitle;

    private int favorisId;
    private FavorisListController parentController;

    public void setFavorisData(int id, String image, String title, FavorisListController parentController) {
        // Set the favoris data to the UI components
        this.favorisId = id;
        this.parentController = parentController;

        InputStream imageStream = getClass().getResourceAsStream(image);
        if (imageStream != null) {
            favorisImage.setImage(new Image(imageStream));
        } else {
            favorisImage.setImage(new Image("C:/Users/ASUS/Desktop/Esprit/Project/EspaceClient/src/main/resources/com/example/espaceclient/assets/false god.jpg")); // Set a default image if the resource is not found
        }
        favorisTitle.setText(title);
        heartIcon.setImage(new Image(getClass().getResourceAsStream("/com/example/espaceclient/assets/favorite.png")));
    }

    @FXML
    private void handleDelete() {
        parentController.removeFavoris(favorisId);
    }

    @FXML
    private void showDetails() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/espaceclient/FavorisDetails.fxml"));
            AnchorPane root = loader.load();

            FavorisDetailsController detailsController = loader.getController();
            detailsController.setFavorisData(favorisImage.getImage(), favorisTitle.getText());

            Stage stage = new Stage();
            stage.setTitle("Favorite Details");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}