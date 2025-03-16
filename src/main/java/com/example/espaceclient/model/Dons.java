package com.example.espaceclient.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Dons {
    private int idDon;
    private int idClient;
    private Integer idPartenaire; // Nullable if donation is to the organization
    private BigDecimal montant;
    private LocalDateTime dateDon;
    private String status;

    // Default constructor
    public Dons() {}

    // Parameterized constructor
    public Dons(int idDon, int idClient, Integer idPartenaire, BigDecimal montant, LocalDateTime dateDon, String status) {
        this.idDon = idDon;
        this.idClient = idClient;
        this.idPartenaire = idPartenaire;
        this.montant = montant;
        this.dateDon = dateDon;
        this.status = status;
    }

    // Overloaded constructor without idPartenaire
    public Dons(int idDon, int idClient, BigDecimal montant, LocalDateTime dateDon, String status) {
        this.idDon = idDon;
        this.idClient = idClient;
        this.montant = montant;
        this.dateDon = dateDon;
        this.status = status;
    }

    // Getters and Setters
    public int getIdDon() {
        return idDon;
    }

    public void setIdDon(int idDon) {
        this.idDon = idDon;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Integer getIdPartenaire() {
        return idPartenaire;
    }

    public void setIdPartenaire(Integer idPartenaire) {
        this.idPartenaire = idPartenaire;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDateTime getDateDon() {
        return dateDon;
    }

    public void setDateDon(LocalDateTime dateDon) {
        this.dateDon = dateDon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Dons{" +
                "idDon=" + idDon +
                ", idClient=" + idClient +
                ", idPartenaire=" + idPartenaire +
                ", montant=" + montant +
                ", dateDon=" + dateDon +
                ", status='" + status + '\'' +
                '}';
    }
}