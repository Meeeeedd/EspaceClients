package com.example.espaceclient.service;

import com.example.espaceclient.dao.FavouriteDAOImpl;
import com.example.espaceclient.model.Favourite;

import java.util.List;

public class FavouriteService {
    private FavouriteDAOImpl favouriteDAO = new FavouriteDAOImpl();

    public List<Favourite> getFavouritesByUser(int userId) {
        return favouriteDAO.getFavouritesByUser(userId);
    }

    public void addFavourite(Favourite favourite) {
        favouriteDAO.addFavourite(favourite);
    }

    public void removeFavourite(int idFavourite) {
        favouriteDAO.removeFavourite(idFavourite);
    }

    public Favourite getFavouriteByUserAndEntity(int userId, int entityId, String entityType) {
        return favouriteDAO.getFavouriteByUserAndEntity(userId, entityId, entityType);
    }
}