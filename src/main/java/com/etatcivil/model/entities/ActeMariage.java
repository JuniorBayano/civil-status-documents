package main.java.com.etatcivil.model.entities;


import main.java.com.etatcivil.model.entities.Acte;
import main.java.com.etatcivil.model.enums.TypeActe;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ActeMariage extends Acte {

    private String nomEpoux;
    private String prenomEpoux;
    private String nomEpouse;
    private String prenomEpouse;
    private LocalDate dateMariage;
    private String lieuMariage;
    private String regimeMatrimonial;
    private String temoin1Nom;
    private String temoin1Prenom;
    private String temoin2Nom;
    private String temoin2Prenom;
    private String numeroRegistre;

    // Constructeurs
    public ActeMariage() {
        super();
        this.type = TypeActe.MARIAGE;
        this.regimeMatrimonial = "Communauté de biens"; // Valeur par défaut
    }

    public ActeMariage(String numero, LocalDate dateEnregistrement, String lieuEnregistrement,
                       int idAgent, String nomEpoux, String prenomEpoux, String nomEpouse,
                       String prenomEpouse, LocalDate dateMariage, String lieuMariage) {
        super(numero, TypeActe.MARIAGE, dateEnregistrement, lieuEnregistrement, idAgent);
        this.nomEpoux = nomEpoux;
        this.prenomEpoux = prenomEpoux;
        this.nomEpouse = nomEpouse;
        this.prenomEpouse = prenomEpouse;
        this.dateMariage = dateMariage;
        this.lieuMariage = lieuMariage;
        this.regimeMatrimonial = "Communauté de biens";
    }

    // Getters et Setters
    public String getNomEpoux() {
        return nomEpoux;
    }

    public void setNomEpoux(String nomEpoux) {
        this.nomEpoux = nomEpoux;
    }

    public String getPrenomEpoux() {
        return prenomEpoux;
    }

    public void setPrenomEpoux(String prenomEpoux) {
        this.prenomEpoux = prenomEpoux;
    }

    public String getNomEpouse() {
        return nomEpouse;
    }

    public void setNomEpouse(String nomEpouse) {
        this.nomEpouse = nomEpouse;
    }

    public String getPrenomEpouse() {
        return prenomEpouse;
    }

    public void setPrenomEpouse(String prenomEpouse) {
        this.prenomEpouse = prenomEpouse;
    }

    public LocalDate getDateMariage() {
        return dateMariage;
    }

    public void setDateMariage(LocalDate dateMariage) {
        this.dateMariage = dateMariage;
    }

    public String getLieuMariage() {
        return lieuMariage;
    }

    public void setLieuMariage(String lieuMariage) {
        this.lieuMariage = lieuMariage;
    }

    public String getRegimeMatrimonial() {
        return regimeMatrimonial;
    }

    public void setRegimeMatrimonial(String regimeMatrimonial) {
        this.regimeMatrimonial = regimeMatrimonial;
    }

    public String getTemoin1Nom() {
        return temoin1Nom;
    }

    public void setTemoin1Nom(String temoin1Nom) {
        this.temoin1Nom = temoin1Nom;
    }

    public String getTemoin1Prenom() {
        return temoin1Prenom;
    }

    public void setTemoin1Prenom(String temoin1Prenom) {
        this.temoin1Prenom = temoin1Prenom;
    }

    public String getTemoin2Nom() {
        return temoin2Nom;
    }

    public void setTemoin2Nom(String temoin2Nom) {
        this.temoin2Nom = temoin2Nom;
    }

    public String getTemoin2Prenom() {
        return temoin2Prenom;
    }

    public void setTemoin2Prenom(String temoin2Prenom) {
        this.temoin2Prenom = temoin2Prenom;
    }

    public String getNumeroRegistre() {
        return numeroRegistre;
    }

    public void setNumeroRegistre(String numeroRegistre) {
        this.numeroRegistre = numeroRegistre;
    }

    // Méthodes utilitaires
    public String getNomCompletEpoux() {
        return prenomEpoux + " " + nomEpoux;
    }

    public String getNomCompletEpouse() {
        return prenomEpouse + " " + nomEpouse;
    }

    public String getTemoin1Complet() {
        return (temoin1Prenom != null && temoin1Nom != null) ?
                temoin1Prenom + " " + temoin1Nom : "Non renseigné";
    }

    public String getTemoin2Complet() {
        return (temoin2Prenom != null && temoin2Nom != null) ?
                temoin2Prenom + " " + temoin2Nom : "Non renseigné";
    }

    // Implémentation des méthodes abstraites
    @Override
    public String genererExtrait() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder extrait = new StringBuilder();
        extrait.append("=".repeat(60)).append("\n");
        extrait.append("               EXTRAIT D'ACTE DE MARIAGE\n");
        extrait.append("=".repeat(60)).append("\n\n");

        extrait.append("Numéro d'acte : ").append(numero).append("\n");
        extrait.append("Date d'enregistrement : ").append(dateEnregistrement.format(formatter)).append("\n");
        extrait.append("Lieu d'enregistrement : ").append(lieuEnregistrement).append("\n\n");

        extrait.append("INFORMATIONS SUR LE MARIAGE :\n");
        extrait.append("Date du mariage : ").append(dateMariage.format(formatter)).append("\n");
        extrait.append("Lieu du mariage : ").append(lieuMariage).append("\n");
        extrait.append("Régime matrimonial : ").append(regimeMatrimonial).append("\n\n");

        extrait.append("ÉPOUX :\n");
        extrait.append("Nom et prénom : ").append(getNomCompletEpoux()).append("\n\n");

        extrait.append("ÉPOUSE :\n");
        extrait.append("Nom et prénom : ").append(getNomCompletEpouse()).append("\n\n");

        extrait.append("TÉMOINS :\n");
        extrait.append("Témoin 1 : ").append(getTemoin1Complet()).append("\n");
        extrait.append("Témoin 2 : ").append(getTemoin2Complet()).append("\n\n");

        if (numeroRegistre != null) {
            extrait.append("Numéro de registre : ").append(numeroRegistre).append("\n");
        }

        extrait.append("\nStatut : ").append(statut.getLibelle()).append("\n");
        extrait.append("=".repeat(60));

        return extrait.toString();
    }

    @Override
    public String getInformationsSpecifiques() {
        return String.format("Mariage: %s et %s, le %s à %s",
                getNomCompletEpoux(), getNomCompletEpouse(), dateMariage, lieuMariage);
    }

    @Override
    public boolean validerDonnees() {
        return nomEpoux != null && !nomEpoux.trim().isEmpty() &&
                prenomEpoux != null && !prenomEpoux.trim().isEmpty() &&
                nomEpouse != null && !nomEpouse.trim().isEmpty() &&
                prenomEpouse != null && !prenomEpouse.trim().isEmpty() &&
                dateMariage != null &&
                lieuMariage != null && !lieuMariage.trim().isEmpty() &&
                regimeMatrimonial != null && !regimeMatrimonial.trim().isEmpty() &&
                dateMariage.isBefore(LocalDate.now().plusDays(1)); // Pas de mariage dans le futur
    }

    @Override
    public String toString() {
        return String.format("ActeMariage{numero='%s', époux='%s et %s', date=%s, statut=%s}",
                numero, getNomCompletEpoux(), getNomCompletEpouse(), dateMariage, statut);
    }
}