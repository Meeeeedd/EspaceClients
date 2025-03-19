package com.example.espaceclient.dao;

import com.example.espaceclient.model.Favourite;
import com.example.espaceclient.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavouriteDAOImpl implements FavouriteDAO {
    public List<Favourite> getFavouritesByUser(int userId) {
        List<Favourite> favourites = new ArrayList<>();
        String sql = "SELECT * FROM favoris WHERE id_utilisateur = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Favourite favourite = new Favourite();
                favourite.setIdFavourite(resultSet.getInt("id_favori"));
                favourite.setIdUtilisateur(resultSet.getInt("id_utilisateur"));
                favourite.setTypeEntite(resultSet.getString("type_entite"));
                favourite.setIdEntite(resultSet.getInt("id_entite"));
                favourites.add(favourite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favourites;
    }

    public void addFavourite(Favourite favourite) {
        String sql = "INSERT INTO favoris (id_utilisateur, type_entite, id_entite) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, favourite.getIdUtilisateur());
            statement.setString(2, favourite.getTypeEntite());
            statement.setInt(3, favourite.getIdEntite());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFavourite(int idFavourite) {
        String sql = "DELETE FROM favoris WHERE id_favori = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idFavourite);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Favourite getFavouriteByUserAndEntity(int userId, int entityId, String entityType) {
        Favourite favourite = null;
        String sql = "SELECT * FROM favoris WHERE id_utilisateur = ? AND id_entite = ? AND type_entite = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, entityId);
            statement.setString(3, entityType);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                favourite = new Favourite();
                favourite.setIdFavourite(resultSet.getInt("id_favori"));
                favourite.setIdUtilisateur(resultSet.getInt("id_utilisateur"));
                favourite.setTypeEntite(resultSet.getString("type_entite"));
                favourite.setIdEntite(resultSet.getInt("id_entite"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favourite;
    }
}