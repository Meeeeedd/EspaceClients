package com.example.espaceclient.dao;

import com.example.espaceclient.model.Don;
import java.util.List;

public interface DonDao {
    void insertDon(Don don);
    List<Don> listDonsByClient(int idClient);
    String findClientNameById(int idClient);
    int findClientIdByName(String clientName);
    List<Don> searchDons(String keyword);

}