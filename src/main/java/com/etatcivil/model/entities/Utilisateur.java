package main.java.com.etatcivil.model.entities;

import main.java.com.etatcivil.model.enums.RoleUtilisateur;

import java.time.LocalDateTime;


public class Utilisateur {

    private int id;
    private String nom;
    private String prenom;
    private RoleUtilisateur role;
    private String login;
    private String password;
    private LocalDateTime dateCreation;
    private boolean actif;

    // Constructeurs
    public Utilisateur() {
        this.dateCreation = LocalDateTime.now();
        this.actif = true;
    }

    public Utilisateur(String nom, String prenom, RoleUtilisateur role, String login, String password) {
        this();
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.login = login;
        this.password = password;
    }

    public Utilisateur(int id, String nom, String prenom, RoleUtilisateur role, String login, String password,
                       LocalDateTime dateCreation, boolean actif) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.login = login;
        this.password = password;
        this.dateCreation = dateCreation;
        this.actif = actif;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public RoleUtilisateur getRole() {
        return role;
    }

    public void setRole(RoleUtilisateur role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    // Méthodes utilitaires
    public String getNomComplet() {
        return prenom + " " + nom;
    }

    public boolean isAgent() {
        return role == RoleUtilisateur.AGENT;
    }

    public boolean isChef() {
        return role == RoleUtilisateur.CHEF;
    }

    public boolean isInvite() {
        return role == RoleUtilisateur.INVITE;
    }

    @Override
    public String toString() {
        return String.format("Utilisateur{id=%d, nom='%s', prenom='%s', role=%s, login='%s', actif=%s}",
                id, nom, prenom, role, login, actif);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Utilisateur that = (Utilisateur) obj;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}