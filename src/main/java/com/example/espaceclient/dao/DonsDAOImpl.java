package com.example.espaceclient.dao;

import com.example.espaceclient.model.Dons;
import com.example.espaceclient.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonsDAOImpl implements DonsDAO {

    @Override
    public List<Dons> findAll() {
        List<Dons> dons = new ArrayList<>();
        String query = "SELECT * FROM dons";

        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Dons don = new Dons(
                        resultSet.getInt("id_don"),
                        resultSet.getInt("id_client"),
                        resultSet.getObject("id_partenaire", Integer.class),
                        resultSet.getBigDecimal("montant"),
                        resultSet.getTimestamp("date_don").toLocalDateTime(),
                        resultSet.getString("status")
                );
                dons.add(don);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dons;
    }

    @Override
    public void save(Dons don) {
        String query = "INSERT INTO dons (id_client, id_partenaire, montant, date_don, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, don.getIdClient());
            if (don.getIdPartenaire() != null) {
                preparedStatement.setInt(2, don.getIdPartenaire());
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            preparedStatement.setBigDecimal(3, don.getMontant());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(don.getDateDon()));
            preparedStatement.setString(5, don.getStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Dons> findByClientId(int clientId) {
        List<Dons> dons = new ArrayList<>();
        String query = "SELECT * FROM dons WHERE id_client = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Dons don = new Dons(
                        resultSet.getInt("id_don"),
                        resultSet.getInt("id_client"),
                        resultSet.getObject("id_partenaire", Integer.class),
                        resultSet.getBigDecimal("montant"),
                        resultSet.getTimestamp("date_don").toLocalDateTime(),
                        resultSet.getString("status")
                );
                dons.add(don);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dons;
    }
}