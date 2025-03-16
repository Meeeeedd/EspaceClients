package com.example.espaceclient.controller;

import com.example.espaceclient.model.Dons;
import com.example.espaceclient.service.DonsService;
import com.example.espaceclient.utils.SessionManager;

import java.util.List;

public class DonsController {
    private final DonsService donsService;

    public DonsController() {
        this.donsService = new DonsService();
    }

    public List<Dons> getAllDons() {
        return donsService.getAllDons();
    }

    public void createDon(Dons don) {
        donsService.createDon(don);
    }

    public List<Dons> getClientDons() {
        int clientId = SessionManager.getInstance().getCurrentClient().getIdClient();
        return donsService.getDonsByClientId(clientId);
    }
}