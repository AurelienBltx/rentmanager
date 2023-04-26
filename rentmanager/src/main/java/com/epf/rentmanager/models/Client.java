package com.epf.rentmanager.models;

import java.time.LocalDate;


public class Client {

    private String prenom;
    private long id;
    private LocalDate naissance;
    private String nom;
    private String email;

    public Client(long id, String prenom, String nom, LocalDate naissance, String email) {
        this.id = id;
        this.prenom = prenom;
        this.naissance = naissance;
        this.nom = nom;
        this.email = email;
    }

    public Client(){
    }

    @Override
    public String toString() {
        return "Client{" +
                "prenom='" + prenom + '\'' +
                ", id=" + id +
                ", naissance=" + naissance +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getPrenom() {
        return prenom;
    }

    public long getId() {
        return id;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }
}
