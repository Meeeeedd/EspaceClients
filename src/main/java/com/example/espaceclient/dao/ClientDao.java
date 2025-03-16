package com.example.espaceclient.dao;

import com.example.espaceclient.model.Client;
import com.example.espaceclient.model.User;

import java.util.List;

public interface ClientDao {
    // Create Methods
    void createClient(Client client);
    void createUser(User user);

    // Read Methods
    Client getClientById(int clientId);
    User getUserById(int userId);
    List<Client> getAllClients();
    Client getClientByUserId(int userId);

    // Update Methods
    void updateClient(Client client);
    void updateUser(User user);
    void updateClientProfile(int userId, User user, Client client);

    // Delete Methods
    void deleteClient(int clientId);
    void deleteUser(int userId);

    // Utility Methods
    String getUserImage(int userId);
    void updateAvatar(int userId, String avatar);
}