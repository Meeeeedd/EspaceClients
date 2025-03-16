package com.example.espaceclient.service;

import com.example.espaceclient.model.Donation;
import com.example.espaceclient.utils.DatabaseConnection;
import com.example.espaceclient.utils.SessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonationService {
    public List<Donation> getDonationsByUser(int userId) {
        List<Donation> donations = new ArrayList<>();
        String query = "SELECT * FROM dons WHERE id_client = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Donation donation = new Donation();
                donation.setIdDon(resultSet.getInt("id_don"));
                donation.setIdClient(resultSet.getInt("id_client"));
                donation.setIdPartenaire(resultSet.getInt("id_partenaire"));
                donation.setMontant(resultSet.getDouble("montant"));
                donation.setDateDon(resultSet.getDate("date_don"));
                donation.setStatus(resultSet.getString("status"));
                donations.add(donation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donations;
    }
}