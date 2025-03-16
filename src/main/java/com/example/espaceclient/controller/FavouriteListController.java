package com.example.espaceclient.controller;

import com.example.espaceclient.model.Favourite;
import com.example.espaceclient.service.FavouriteService;
import com.example.espaceclient.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class FavouriteListController {
    @FXML
    private ListView<Favourite> favouriteListView;

    private FavouriteService favouriteService = new FavouriteService();

    public void initialize() {
        int currentUserId = SessionManager.getInstance().getCurrentClient().getIdClient();
        List<Favourite> favourites = favouriteService.getFavouritesByUser(currentUserId);
        favouriteListView.getItems().setAll(favourites);

        favouriteListView.setCellFactory(new Callback<ListView<Favourite>, ListCell<Favourite>>() {
            @Override
            public ListCell<Favourite> call(ListView<Favourite> param) {
                return new ListCell<Favourite>() {
                    @Override
                    protected void updateItem(Favourite favourite, boolean empty) {
                        super.updateItem(favourite, empty);
                        if (empty || favourite == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/espaceclient/FavouriteCard.fxml"));
                                AnchorPane pane = loader.load();
                                FavouriteCardController controller = loader.getController();
                                controller.setFavourite(favourite);
                                controller.setParentController(FavouriteListController.this);
                                setGraphic(pane);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
            }
        });
    }

    public void removeFavourite(int idFavourite) {
        favouriteService.removeFavourite(idFavourite);
        // Refresh the list
        initialize();
    }

    public void showFavouriteDetails(Favourite favourite) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/espaceclient/FavouriteDetails.fxml"));
            AnchorPane pane = loader.load();
            FavouriteDetailsController controller = loader.getController();
            controller.setFavourite(favourite);
            // Assuming you have a method to switch the main content pane
            switchContent(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchContent(Node node) {
        // Implement this method to switch the content of the main pane
    }
}