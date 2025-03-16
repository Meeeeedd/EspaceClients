package com.example.espaceclient.service;

import com.example.espaceclient.model.Client;
import com.example.espaceclient.utils.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Base64;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Saves or updates a client's profile in the database.
     *
     * @param client The client object containing updated profile data.
     * @return A string indicating success or a specific error message.
     */
    public static String saveUser(Client client) {
        String updateClientQuery = "UPDATE clients SET adresse = ? WHERE id_client = ?";
        String updateUserQuery = "UPDATE utilisateurs SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, telephone = ?, avatar = ? WHERE id_utilisateur = ?";

        try (Connection connection = DatabaseConnection.connect()) {
            connection.setAutoCommit(false);

            try (PreparedStatement clientStatement = connection.prepareStatement(updateClientQuery);
                 PreparedStatement userStatement = connection.prepareStatement(updateUserQuery)) {

                // Update clients table
                clientStatement.setString(1, client.getAdresse());
                clientStatement.setInt(2, client.getIdClient());
                clientStatement.executeUpdate();

                // Update utilisateurs table
                userStatement.setString(1, client.getNom());
                userStatement.setString(2, client.getPrenom());
                userStatement.setString(3, client.getEmail());
                userStatement.setString(4, client.getMotDePasse());
                userStatement.setString(5, client.getTelephone());
                userStatement.setString(6, client.getAvatar());
                userStatement.setInt(7, client.getIdClient());
                userStatement.executeUpdate();

                connection.commit();
                return "Profile updated successfully.";
            } catch (SQLException e) {
                connection.rollback();
                logger.error("Error updating profile: ", e);
                return "Error updating profile: " + e.getMessage();
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle specific constraint violations
            if (e.getMessage().contains("Duplicate entry")) {
                if (e.getMessage().contains("telephone")) {
                    return "The phone number is already in use. Please use a different phone number.";
                } else if (e.getMessage().contains("email")) {
                    return "The email address is already in use. Please use a different email.";
                }
            }
            logger.error("Database constraint violation: ", e);
            return "A database constraint was violated. Please check your input.";

        } catch (SQLException e) {
            // Handle generic SQL exceptions
            logger.error("Error connecting to the database: ", e);
            return "An error occurred while saving your profile. Please try again later.";

        } catch (Exception e) {
            // Handle any other unexpected exceptions
            logger.error("Unexpected error: ", e);
            return "An unexpected error occurred. Please contact support.";
        }
    }

    /**
     * Saves the uploaded profile image to the database.
     *
     * @param clientId The ID of the client.
     * @param imageFile The image file to be uploaded.
     * @return A string indicating success or a specific error message.
     */
    public static String saveProfileImage(int clientId, File imageFile) {
        String sql = "UPDATE utilisateurs SET avatar = ? WHERE id_utilisateur = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Convert image file to Base64 string
            byte[] imageBytes = new byte[(int) imageFile.length()];
            try (FileInputStream fis = new FileInputStream(imageFile)) {
                fis.read(imageBytes);
            }
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // Set parameters for the SQL query
            pstmt.setString(1, base64Image);
            pstmt.setInt(2, clientId);

            // Execute the update
            pstmt.executeUpdate();

            return "Profile image updated successfully.";

        } catch (SQLException | IOException e) {
            logger.error("Error saving profile image: ", e);
            return "An error occurred while saving the profile image. Please try again later.";
        }
    }
}