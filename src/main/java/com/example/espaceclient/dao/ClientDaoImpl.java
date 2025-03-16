package com.example.espaceclient.dao;

import com.example.espaceclient.model.Client;
import com.example.espaceclient.model.User;
import com.example.espaceclient.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDaoImpl implements ClientDao {
    private static final Logger LOGGER = Logger.getLogger(ClientDaoImpl.class.getName());

    @Override
    public void createClient(Client client) {
        String query = "INSERT INTO clients (id_client, adresse, points_fidelite, photo_CIN, autres_documents) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, client.getIdClient());
            preparedStatement.setString(2, client.getAdresse());
            preparedStatement.setInt(3, client.getPointsFidelite());
            preparedStatement.setString(4, client.getPhotoCIN());
            preparedStatement.setString(5, client.getAutresDocuments());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding client", e);
        }
    }

    @Override
    public void createUser(User user) {
        String query = "INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, telephone, type_utilisateur, avatar, statut_verification, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getMotDePasse());
            preparedStatement.setString(5, user.getTelephone());
            preparedStatement.setString(6, user.getTypeUtilisateur());
            preparedStatement.setString(7, user.getAvatar());
            preparedStatement.setString(8, user.isStatutVerification() ? "validé" : "en attente");
            preparedStatement.setString(9, user.getStatus());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding user", e);
        }
    }

    @Override
    public Client getClientById(int clientId) {
        String query = "SELECT c.*, u.nom, u.prenom, u.email, u.telephone, u.date_inscription, u.type_utilisateur, u.avatar, u.statut_verification, u.status " +
                "FROM clients c JOIN utilisateurs u ON c.id_client = u.id_utilisateur WHERE c.id_client = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id_utilisateur"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("mot_de_passe"),
                        resultSet.getString("telephone"),
                        resultSet.getDate("date_inscription"),
                        resultSet.getString("type_utilisateur"),
                        resultSet.getString("avatar"),
                        resultSet.getString("statut_verification").equals("validé"),
                        resultSet.getString("status")
                );
                return new Client(
                        user,
                        resultSet.getString("adresse"),
                        resultSet.getInt("points_fidelite"),
                        resultSet.getString("photo_CIN"),
                        resultSet.getString("autres_documents")
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching client by ID", e);
        }
        return null;
    }

    @Override
    public User getUserById(int userId) {
        String query = "SELECT * FROM utilisateurs WHERE id_utilisateur = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id_utilisateur"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("mot_de_passe"),
                        resultSet.getString("telephone"),
                        resultSet.getDate("date_inscription"),
                        resultSet.getString("type_utilisateur"),
                        resultSet.getString("avatar"),
                        resultSet.getString("statut_verification").equals("validé"),
                        resultSet.getString("status")
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching user by ID", e);
        }
        return null;
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT c.*, u.nom, u.prenom, u.email, u.telephone, u.date_inscription, u.type_utilisateur, u.avatar, u.statut_verification, u.status " +
                "FROM clients c JOIN utilisateurs u ON c.id_client = u.id_utilisateur";
        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id_utilisateur"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("mot_de_passe"),
                        resultSet.getString("telephone"),
                        resultSet.getDate("date_inscription"),
                        resultSet.getString("type_utilisateur"),
                        resultSet.getString("avatar"),
                        resultSet.getString("statut_verification").equals("validé"),
                        resultSet.getString("status")
                );
                clients.add(new Client(
                        user,
                        resultSet.getString("adresse"),
                        resultSet.getInt("points_fidelite"),
                        resultSet.getString("photo_CIN"),
                        resultSet.getString("autres_documents")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all clients", e);
        }
        return clients;
    }

    @Override
    public Client getClientByUserId(int userId) {
        String query = "SELECT c.*, u.nom, u.prenom, u.email, u.telephone, u.date_inscription, u.type_utilisateur, u.avatar, u.statut_verification, u.status " +
                "FROM clients c JOIN utilisateurs u ON c.id_client = u.id_utilisateur WHERE u.id_utilisateur = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id_utilisateur"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("mot_de_passe"),
                        resultSet.getString("telephone"),
                        resultSet.getDate("date_inscription"),
                        resultSet.getString("type_utilisateur"),
                        resultSet.getString("avatar"),
                        resultSet.getString("statut_verification").equals("validé"),
                        resultSet.getString("status")
                );
                return new Client(
                        user,
                        resultSet.getString("adresse"),
                        resultSet.getInt("points_fidelite"),
                        resultSet.getString("photo_CIN"),
                        resultSet.getString("autres_documents")
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching client by user ID", e);
        }
        return null;
    }

    @Override
    public void updateClient(Client client) {
        String updateClientQuery = "UPDATE clients SET adresse = ?, points_fidelite = ?, photo_CIN = ?, autres_documents = ? WHERE id_client = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateClientQuery)) {
            preparedStatement.setString(1, client.getAdresse());
            preparedStatement.setInt(2, client.getPointsFidelite());
            preparedStatement.setString(3, client.getPhotoCIN());
            preparedStatement.setString(4, client.getAutresDocuments());
            preparedStatement.setInt(5, client.getIdClient());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating client", e);
        }
    }

    @Override
    public void updateUser(User user) {
        String updateUserQuery = "UPDATE utilisateurs SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, telephone = ?, avatar = ?, statut_verification = ?, status = ? WHERE id_utilisateur = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery)) {
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getMotDePasse());
            preparedStatement.setString(5, user.getTelephone());
            preparedStatement.setString(6, user.getAvatar());
            preparedStatement.setString(7, user.isStatutVerification() ? "validé" : "en attente");
            preparedStatement.setString(8, user.getStatus());
            preparedStatement.setInt(9, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating user", e);
        }
    }

    @Override
    public void updateClientProfile(int userId, User user, Client client) {
        String updateUserQuery = "UPDATE utilisateurs SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, telephone = ?, avatar = ?, statut_verification = ?, status = ? WHERE id_utilisateur = ?";
        String updateClientQuery = "UPDATE clients SET adresse = ?, points_fidelite = ?, photo_CIN = ?, autres_documents = ? WHERE id_client = ?";

        try (Connection connection = DatabaseConnection.connect()) {
            connection.setAutoCommit(false);

            try (PreparedStatement userStatement = connection.prepareStatement(updateUserQuery);
                 PreparedStatement clientStatement = connection.prepareStatement(updateClientQuery)) {

                // Update utilisateurs table
                userStatement.setString(1, user.getNom());
                userStatement.setString(2, user.getPrenom());
                userStatement.setString(3, user.getEmail());
                userStatement.setString(4, user.getMotDePasse());
                userStatement.setString(5, user.getTelephone());
                userStatement.setString(6, user.getAvatar());
                userStatement.setString(7, user.isStatutVerification() ? "validé" : "en attente");
                userStatement.setString(8, user.getStatus());
                userStatement.setInt(9, userId);
                userStatement.executeUpdate();

                // Update clients table
                clientStatement.setString(1, client.getAdresse());
                clientStatement.setInt(2, client.getPointsFidelite());
                clientStatement.setString(3, client.getPhotoCIN());
                clientStatement.setString(4, client.getAutresDocuments());
                clientStatement.setInt(5, client.getIdClient());
                clientStatement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                LOGGER.log(Level.SEVERE, "Error updating profile", e);
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating profile", e);
        }
    }

    @Override
    public void deleteClient(int clientId) {
        String query = "DELETE FROM clients WHERE id_client = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clientId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting client", e);
        }
    }

    @Override
    public void deleteUser(int userId) {
        String query = "DELETE FROM utilisateurs WHERE id_utilisateur = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting user", e);
        }
    }

    @Override
    public String getUserImage(int userId) {
        String imageName = null;
        String query = "SELECT avatar FROM utilisateurs WHERE id_utilisateur = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                imageName = resultSet.getString("avatar");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching user image", e);
        }
        return imageName;
    }

    @Override
    public void updateAvatar(int userId, String avatar) {
        String query = "UPDATE utilisateurs SET avatar = ? WHERE id_utilisateur = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, avatar);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating avatar", e);
        }
    }
}