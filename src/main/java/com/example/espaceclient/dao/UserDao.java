package com.example.espaceclient.dao;

import com.example.espaceclient.model.User;
import com.example.espaceclient.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public User getUserByEmail(String email) {
        Connection connection = DatabaseConnection.connect();
        if (connection == null) return null;

        try {
            String query = "SELECT * FROM utilisateurs WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("mot_de_passe"),
                        resultSet.getString("telephone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(connection);
        }
        return null;
    }

    public boolean saveUser(User user) {
        Connection connection = DatabaseConnection.connect();
        if (connection == null) return false;

        try {
            String query = "UPDATE utilisateurs SET nom = ?, prenom = ?, mot_de_passe = ?, telephone = ? WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getNom());
            statement.setString(2, user.getPrenom());
            statement.setString(3, user.getMotDePasse());
            statement.setString(4, user.getTelephone());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(connection);
        }
        return false;
    }
}