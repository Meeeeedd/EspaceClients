package com.example.espaceclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DemandeAideCardController {

    @FXML
    private Label demandeTitle;

    @FXML
    private Label demandeDescription;

    @FXML
    private Label demandeDate;

    @FXML
    private Label demandeStatus;

    public void setDemandeAideData(String title, String description, String date, String status) {
        demandeTitle.setText(title);
        demandeDescription.setText(description);
        demandeDate.setText(date);
        demandeStatus.setText(status);
    }
}