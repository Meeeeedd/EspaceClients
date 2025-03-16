package com.example.espaceclient.service;

import com.example.espaceclient.model.Client;
import com.example.espaceclient.model.User;
import com.example.espaceclient.utils.DatabaseConnection;
import com.example.espaceclient.utils.PasswordUtils;

import java.sql.*;

public class AuthService {

    public static boolean registerUser(User user) throws SQLIntegrityConstraintViolationException {
        // Hash the user's password before saving it to the database
        String hashedPassword = PasswordUtils.hashPassword(user.getMotDePasse());
        String userSql = "INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, telephone, type_utilisateur) VALUES (?, ?, ?, ?, ?, ?)";
        String clientSql = "INSERT INTO clients (id_client) VALUES (?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement userPstmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement clientPstmt = conn.prepareStatement(clientSql)) {

            conn.setAutoCommit(false); // Start transaction

            // Insert into utilisateurs table
            userPstmt.setString(1, user.getNom());
            userPstmt.setString(2, user.getPrenom());
            userPstmt.setString(3, user.getEmail());
            userPstmt.setString(4, hashedPassword);
            userPstmt.setString(5, user.getTelephone());
            userPstmt.setString(6, user.getTypeUtilisateur());

            userPstmt.executeUpdate();

            // Get the generated user ID
            try (ResultSet generatedKeys = userPstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);

                    // Insert into clients table
                    clientPstmt.setInt(1, userId);
                    clientPstmt.executeUpdate();
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            conn.commit(); // Commit transaction
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw e; // Re-throw the exception to be handled in RegisterController
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static AuthResponse loginUser(String email, String password) {
        String sql = "SELECT * FROM utilisateurs WHERE email = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("mot_de_passe");
                if (PasswordUtils.verifyPassword(password, storedHashedPassword)) {
                    return new AuthResponse(true, "Login successful.");
                } else {
                    return new AuthResponse(false, "Incorrect password. Please try again.");
                }
            } else {
                return new AuthResponse(false, "No account found with this email. Please register first.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new AuthResponse(false, "An unexpected error occurred during login. Please try again later.");
        }
    }

    public static User getUserDetails(String email) {
        String sql = "SELECT * FROM utilisateurs WHERE email = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getInt("id_utilisateur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("telephone"),
                        rs.getDate("date_inscription"),
                        rs.getString("type_utilisateur"),
                        rs.getString("avatar"),
                        rs.getBoolean("statut_verification"),
                        rs.getString("status")
                );
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Client getUserAndClientDetails(String email) {
        String sql = "SELECT u.*, c.adresse, c.points_fidelite, c.photo_CIN, c.autres_documents " +
                "FROM utilisateurs u " +
                "JOIN clients c ON u.id_utilisateur = c.id_client " +
                "WHERE u.email = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Client client = new Client(
                        rs.getInt("id_utilisateur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("telephone"),
                        rs.getString("adresse"),
                        rs.getString("photo_CIN")
                );

                // Set additional fields from the clients table
                client.setId(rs.getInt("id_utilisateur"));
                client.setDate_inscription(rs.getDate("date_inscription"));
                client.setTypeUtilisateur(rs.getString("type_utilisateur"));
                client.setAvatar(rs.getString("avatar"));
                client.setStatus(rs.getString("status"));
                client.setPointsFidelite(rs.getInt("points_fidelite"));
                client.setAutresDocuments(rs.getString("autres_documents"));

                return client;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static class AuthResponse {
        private boolean success;
        private String message;

        public AuthResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}