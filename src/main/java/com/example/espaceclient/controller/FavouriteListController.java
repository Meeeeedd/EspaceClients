package com.example.espaceclient.controller;

import com.example.espaceclient.model.Favourite;
import com.example.espaceclient.service.FavouriteService;
import com.example.espaceclient.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.util.Callback;

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
                        } else {
                            setText(favourite.toString());
                        }
                    }
                };
            }
        });
    }
}