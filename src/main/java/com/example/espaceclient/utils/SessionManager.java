package com.example.espaceclient.utils;

import com.example.espaceclient.model.Client;

public class SessionManager {
    private static SessionManager instance;
    private Client currentClient;

    private SessionManager() { }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setCurrentClient(Client client) {
        this.currentClient = client;
        System.out.println("Client set to: " + client);
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public void logout() {
        this.currentClient = null;
    }
}