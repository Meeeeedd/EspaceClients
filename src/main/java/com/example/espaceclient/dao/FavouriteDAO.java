package com.example.espaceclient.dao;

import com.example.espaceclient.model.Favourite;

import java.util.List;

public interface FavouriteDAO {
    List<Favourite> getFavouritesByUser(int userId);
    void addFavourite(Favourite favourite);
    void removeFavourite(int idFavourite);
    Favourite getFavouriteByUserAndEntity(int userId, int entityId, String entityType);
}
