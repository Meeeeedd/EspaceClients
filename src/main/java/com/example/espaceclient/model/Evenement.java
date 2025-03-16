package com.example.espaceclient.model;

import java.util.Date;

public class Evenement {
    private int idEvenement;
    private String titre;
    private String description;
    private Date dateEvenement;
    private String lieu;
    private int idPartenaire;
    private String status;

    // Constructors
    public Evenement() {}

    public Evenement(int idEvenement, String titre, String description, Date dateEvenement, String lieu, int idPartenaire, String status) {
        this.idEvenement = idEvenement;
        this.titre = titre;
        this.description = description;
        this.dateEvenement = dateEvenement;
        this.lieu = lieu;
        this.idPartenaire = idPartenaire;
        this.status = status;
    }

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

    public Date getDateEvenement() {
        return dateEvenement;
    }

    public void setDateEvenement(Date dateEvenement) {
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
                '}';
    }
}