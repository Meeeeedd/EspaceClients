package com.example.espaceclient.model;

public class Client extends User {
    private int idClient;
    private String adresse;
    private int points_fidelite;
    private String photo_CIN;
    private String autres_documents; // Assuming JSON is stored as a String

    public Client() {
        super();
    }

    public Client(User user, String adresse, int points_fidelite, String photo_CIN, String autres_documents) {
        super(user.getId(), user.getNom(), user.getPrenom(), user.getEmail(), user.getMotDePasse(), user.getTelephone(),
                user.getDate_inscription(), user.getTypeUtilisateur(), user.getAvatar(), user.isStatutVerification(), user.getStatus());
        this.idClient = user.getId();
        this.adresse = adresse;
        this.points_fidelite = points_fidelite;
        this.photo_CIN = photo_CIN;
        this.autres_documents = autres_documents;
    }

    public Client(int idClient, String nom, String prenom, String email, String mot_de_passe, String telephone, String adresse, String photo_CIN) {
        super(nom, prenom, email, mot_de_passe, telephone);
        this.idClient = idClient;
        this.adresse = adresse;
        this.points_fidelite = 0; // Default value
        this.photo_CIN = photo_CIN;
    }

    public Client(String nom, String prenom, String email, String mot_de_passe, String telephone, String adresse, String photo_CIN) {
        super(nom, prenom, email, mot_de_passe, telephone);
        this.adresse = adresse;
        this.points_fidelite = 0; // Default value
        this.photo_CIN = photo_CIN;
    }

    // Getters and Setters
    public int getIdClient() { return idClient; }
    public void setIdClient(int idClient) { this.idClient = idClient; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public int getPointsFidelite() { return points_fidelite; }
    public void setPointsFidelite(int points_fidelite) { this.points_fidelite = points_fidelite; }

    public String getPhotoCIN() { return photo_CIN; }
    public void setPhotoCIN(String photo_CIN) { this.photo_CIN = photo_CIN; }

    public String getAutresDocuments() { return autres_documents; }
    public void setAutresDocuments(String autres_documents) { this.autres_documents = autres_documents; }

    @Override
    public String toString() {
        return super.toString() +
                "Client{" +
                "idClient=" + idClient +
                ", adresse='" + adresse + '\'' +
                ", points_fidelite=" + points_fidelite +
                ", photo_CIN='" + photo_CIN + '\'' +
                ", autres_documents='" + autres_documents + '\'' +
                '}';
    }
}