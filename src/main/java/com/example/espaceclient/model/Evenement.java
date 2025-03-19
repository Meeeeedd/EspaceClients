package com.example.espaceclient.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Evenement {
    private int idEvenement;
    private String titre;
    private String description;
    private LocalDateTime dateEvenement;
    private String lieu;
    private int idPartenaire;
    private String status;
    private String imageName;

    // Constructors
    public Evenement() {}

    // Getters and Setters
    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateEvenement() {
        return dateEvenement;
    }

    public void setDateEvenement(LocalDateTime dateEvenement) {
        this.dateEvenement = dateEvenement;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getIdPartenaire() {
        return idPartenaire;
    }

    public void setIdPartenaire(int idPartenaire) {
        this.idPartenaire = idPartenaire;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    // toString method
    @Override
    public String toString() {
        return "Evenement{" +
                "idEvenement=" + idEvenement +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", dateEvenement=" + dateEvenement +
                ", lieu='" + lieu + '\'' +
                ", idPartenaire=" + idPartenaire +
                ", status='" + status + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}