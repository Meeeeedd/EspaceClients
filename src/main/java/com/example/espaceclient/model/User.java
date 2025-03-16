package com.example.espaceclient.model;

import java.util.Date;

public class User {
    private int id_utilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String mot_de_passe;
    private String telephone;
    private Date date_inscription;
    private String type_utilisateur;
    private String avatar;
    private boolean statut_verification;
    private String status;

    public User() {}

    public User(int id_utilisateur, String nom, String prenom, String email, String mot_de_passe, String telephone,
                Date date_inscription, String type_utilisateur, String avatar, boolean statut_verification, String status) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.telephone = telephone;
        this.date_inscription = date_inscription;
        this.type_utilisateur = type_utilisateur;
        this.avatar = avatar;
        this.statut_verification = statut_verification;
        this.status = status;
    }

    public User(String nom, String prenom, String email, String mot_de_passe, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.telephone = telephone;
        setTypeUtilisateur("client"); // Default value
    }

    // Getters and Setters
    public int getId() { return id_utilisateur; }
    public void setId(int id_utilisateur) { this.id_utilisateur = id_utilisateur; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return mot_de_passe; }
    public void setMotDePasse(String mot_de_passe) { this.mot_de_passe = mot_de_passe; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public Date getDate_inscription() { return date_inscription; }
    public void setDate_inscription(Date date_inscription) { this.date_inscription = date_inscription; }

    public String getTypeUtilisateur() { return type_utilisateur; }
    public void setTypeUtilisateur(String type_utilisateur) { this.type_utilisateur = type_utilisateur; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public boolean isStatutVerification() { return statut_verification; }
    public void setStatutVerification(boolean statut_verification) { this.statut_verification = statut_verification; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id_utilisateur +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", telephone='" + telephone + '\'' +
                ", date_inscription=" + date_inscription +
                ", type_utilisateur='" + type_utilisateur + '\'' +
                ", avatar='" + avatar + '\'' +
                ", statut_verification=" + statut_verification +
                ", status='" + status + '\'' +
                '}';
    }
}