package com.example.espaceclient.model;

public class Favourite {
    private int idFavourite;
    private int idUtilisateur;
    private String typeEntite;
    private int idEntite;

    // Constructors
    public Favourite() {}

    public Favourite(int idFavourite, int idUtilisateur, String typeEntite, int idEntite) {
        this.idFavourite = idFavourite;
        this.idUtilisateur = idUtilisateur;
        this.typeEntite = typeEntite;
        this.idEntite = idEntite;
    }

    // Getters and Setters
    public int getIdFavourite() {
        return idFavourite;
    }

    public void setIdFavourite(int idFavourite) {
        this.idFavourite = idFavourite;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getTypeEntite() {
        return typeEntite;
    }

    public void setTypeEntite(String typeEntite) {
        this.typeEntite = typeEntite;
    }

    public int getIdEntite() {
        return idEntite;
    }

    public void setIdEntite(int idEntite) {
        this.idEntite = idEntite;
    }

    // toString method
    @Override
    public String toString() {
        return "Favourite{" +
                "idFavourite=" + idFavourite +
                ", idUtilisateur=" + idUtilisateur +
                ", typeEntite='" + typeEntite + '\'' +
                ", idEntite=" + idEntite +
                '}';
    }
}