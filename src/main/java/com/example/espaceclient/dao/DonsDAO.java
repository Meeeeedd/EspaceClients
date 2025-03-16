package com.example.espaceclient.dao;

import com.example.espaceclient.model.Dons;

import java.util.List;

public interface DonsDAO {
    List<Dons> findAll();
    void save(Dons don);
    List<Dons> findByClientId(int clientId); // Method to fetch donations by client ID
}