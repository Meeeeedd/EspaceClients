package com.example.espaceclient.dao;

import com.example.espaceclient.model.Evenement;
import java.util.List;

public interface EvenementDAO {
    Evenement getEvenementById(int idEvenement);
    List<Evenement> getAllEvenements();
    List<Evenement> getEvenementsByClientId(int clientId);
    List<Evenement> searchEvents(String keyword);
}