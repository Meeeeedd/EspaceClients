package com.example.espaceclient.dao;

import com.example.espaceclient.model.Evenement;
import com.example.espaceclient.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementDAOImpl implements EvenementDAO {

    private static final String SELECT_EVENEMENT_BY_ID = "SELECT * FROM evenements WHERE id_evenement = ?";
    private static final String SELECT_ALL_EVENEMENTS = "SELECT * FROM evenements";
    private static final String SELECT_EVENEMENTS_BY_CLIENT_ID = "SELECT * FROM evenements WHERE id_partenaire = ?";
    private static final String SEARCH_EVENTS_SQL = "SELECT * FROM evenements WHERE titre LIKE ? " +
            "OR description LIKE ?";

    @Override
    public Evenement getEvenementById(int idEvenement) {
        Evenement event = null;
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENEMENT_BY_ID)) {
            preparedStatement.setInt(1, idEvenement);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    event = new Evenement();
                    event.setIdEvenement(rs.getInt("id_evenement"));
                    event.setTitre(rs.getString("titre"));
                    event.setDescription(rs.getString("description"));
                    event.setDateEvenement(rs.getTimestamp("date_evenement").toLocalDateTime());
                    event.setLieu(rs.getString("lieu"));
                    event.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }

    @Override
    public List<Evenement> getAllEvenements() {
        List<Evenement> evenements = new ArrayList<>();
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EVENEMENTS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Evenement event = new Evenement();
                int idEvenement = rs.getInt("idEvenement");
                String titre = rs.getString("titre");
                String description = rs.getString("description");
                Date dateEvenement = rs.getDate("dateEvenement");
                String lieu = rs.getString("lieu");
                int idPartenaire = rs.getInt("idPartenaire");
                String status = rs.getString("status");
                String imageName = rs.getString("imageName");
                evenements.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evenements;
    }

    @Override
    public List<Evenement> searchEvents(String keyword) {
        List<Evenement> events = new ArrayList<>();
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_EVENTS_SQL)) {

            String searchKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, searchKeyword);
            preparedStatement.setString(2, searchKeyword);

            System.out.println("Requête SQL exécutée : " + SEARCH_EVENTS_SQL);
            System.out.println("Mot-clé de recherche : " + searchKeyword);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Evenement event = new Evenement();
                    event.setIdEvenement(rs.getInt("id_evenement"));
                    event.setTitre(rs.getString("titre"));
                    event.setDescription(rs.getString("description"));
                    event.setDateEvenement(rs.getTimestamp("date_evenement").toLocalDateTime());
                    event.setLieu(rs.getString("lieu"));
                    event.setStatus(rs.getString("status"));
                    events.add(event);

                    System.out.println("Événement trouvé : " + event.getTitre() + " - " + event.getLieu());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public List<Evenement> getEvenementsByClientId(int clientId) {
        List<Evenement> evenements = new ArrayList<>();
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENEMENTS_BY_CLIENT_ID)) {
            preparedStatement.setInt(1, clientId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Evenement event = new Evenement();
                event.setIdEvenement(rs.getInt("id_evenement"));
                event.setTitre(rs.getString("titre"));
                event.setDescription(rs.getString("description"));
                event.setDateEvenement(rs.getTimestamp("date_evenement").toLocalDateTime());
                event.setLieu(rs.getString("lieu"));
                event.setStatus(rs.getString("status"));
                event.setImageName(rs.getString("imageName"));
                evenements.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evenements;
    }
}