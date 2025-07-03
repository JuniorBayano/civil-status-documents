package main.java.com.etatcivil.model.entities;

import main.java.com.etatcivil.model.enums.Sexe;
import main.java.com.etatcivil.model.enums.TypeActe;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ActeNaissance extends Acte {

    private String nomEnfant;
    private String prenomEnfant;
    private Sexe sexe;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String nomPere;
    private String prenomPere;
    private String nomMere;
    private String prenomMere;
    private String numeroRegistre;

    // Constructeurs
    public ActeNaissance() {
        super();
        this.type = TypeActe.NAISSANCE;
    }

    public ActeNaissance(String numero, LocalDate dateEnregistrement, String lieuEnregistrement,
                         int idAgent, String nomEnfant, String prenomEnfant, Sexe sexe,
                         LocalDate dateNaissance, String lieuNaissance, String nomPere,
                         String prenomPere, String nomMere, String prenomMere) {
        super(numero, TypeActe.NAISSANCE, dateEnregistrement, lieuEnregistrement, idAgent);
        this.nomEnfant = nomEnfant;
        this.prenomEnfant = prenomEnfant;
        this.sexe = sexe;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.nomPere = nomPere;
        this.prenomPere = prenomPere;
        this.nomMere = nomMere;
        this.prenomMere = prenomMere;
    }

    // Getters et Setters
    public String getNomEnfant() {
        return nomEnfant;
    }

    public void setNomEnfant(String nomEnfant) {
        this.nomEnfant = nomEnfant;
    }

    public String getPrenomEnfant() {
        return prenomEnfant;
    }

    public void setPrenomEnfant(String prenomEnfant) {
        this.prenomEnfant = prenomEnfant;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getNomPere() {
        return nomPere;
    }

    public void setNomPere(String nomPere) {
        this.nomPere = nomPere;
    }

    public String getPrenomPere() {
        return prenomPere;
    }

    public void setPrenomPere(String prenomPere) {
        this.prenomPere = prenomPere;
    }

    public String getNomMere() {
        return nomMere;
    }

    public void setNomMere(String nomMere) {
        this.nomMere = nomMere;
    }

    public String getPrenomMere() {
        return prenomMere;
    }

    public void setPrenomMere(String prenomMere) {
        this.prenomMere = prenomMere;
    }

    public String getNumeroRegistre() {
        return numeroRegistre;
    }

    public void setNumeroRegistre(String numeroRegistre) {
        this.numeroRegistre = numeroRegistre;
    }

    // Méthodes utilitaires
    public String getNomCompletEnfant() {
        return prenomEnfant + " " + nomEnfant;
    }

    public String getNomCompletPere() {
        return (prenomPere != null && nomPere != null) ? prenomPere + " " + nomPere : "Non déclaré";
    }

    public String getNomCompletMere() {
        return prenomMere + " " + nomMere;
    }

    // Implémentation des méthodes abstraites
    @Override
    public String genererExtrait() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder extrait = new StringBuilder();
        extrait.append("=".repeat(60)).append("\n");
        extrait.append("               EXTRAIT D'ACTE DE NAISSANCE\n");
        extrait.append("=".repeat(60)).append("\n\n");

        extrait.append("Numéro d'acte : ").append(numero).append("\n");
        extrait.append("Date d'enregistrement : ").append(dateEnregistrement.format(formatter)).append("\n");
        extrait.append("Lieu d'enregistrement : ").append(lieuEnregistrement).append("\n\n");

        extrait.append("INFORMATIONS SUR L'ENFANT :\n");
        extrait.append("Nom et prénom : ").append(getNomCompletEnfant()).append("\n");
        extrait.append("Sexe : ").append(sexe.getLibelle()).append("\n");
        extrait.append("Date de naissance : ").append(dateNaissance.format(formatter)).append("\n");
        extrait.append("Lieu de naissance : ").append(lieuNaissance).append("\n\n");

        extrait.append("FILIATION :\n");
        extrait.append("Père : ").append(getNomCompletPere()).append("\n");
        extrait.append("Mère : ").append(getNomCompletMere()).append("\n\n");

        if (numeroRegistre != null) {
            extrait.append("Numéro de registre : ").append(numeroRegistre).append("\n");
        }

        extrait.append("\nStatut : ").append(statut.getLibelle()).append("\n");
        extrait.append("=".repeat(60));

        return extrait.toString();
    }

    @Override
    public String getInformationsSpecifiques() {
        return String.format("Enfant: %s, né(e) le %s à %s",
                getNomCompletEnfant(), dateNaissance, lieuNaissance);
    }

    @Override
    public boolean validerDonnees() {
        return nomEnfant != null && !nomEnfant.trim().isEmpty() &&
                prenomEnfant != null && !prenomEnfant.trim().isEmpty() &&
                sexe != null &&
                dateNaissance != null &&
                lieuNaissance != null && !lieuNaissance.trim().isEmpty() &&
                nomMere != null && !nomMere.trim().isEmpty() &&
                prenomMere != null && !prenomMere.trim().isEmpty() &&
                dateNaissance.isBefore(LocalDate.now().plusDays(1)); // Pas de naissance dans le futur
    }

    @Override
    public String toString() {
        return String.format("ActeNaissance{numero='%s', enfant='%s', date=%s, statut=%s}",
                numero, getNomCompletEnfant(), dateNaissance, statut);
    }
}