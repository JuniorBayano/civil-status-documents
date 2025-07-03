package main.java.com.etatcivil.model.entities;


import main.java.com.etatcivil.model.enums.TypeActe;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ActeDeces extends Acte {

    private String nomDefunt;
    private String prenomDefunt;
    private LocalDate dateDeces;
    private String lieuDeces;
    private String causeDeces;
    private String declarantNom;
    private String declarantPrenom;
    private String lienDeclarant;
    private String numeroRegistre;

    // Constructeurs
    public ActeDeces() {
        super();
        this.type = TypeActe.DECES;
    }

    public ActeDeces(String numero, LocalDate dateEnregistrement, String lieuEnregistrement,
                     int idAgent, String nomDefunt, String prenomDefunt, LocalDate dateDeces,
                     String lieuDeces, String declarantNom, String declarantPrenom, String lienDeclarant) {
        super(numero, TypeActe.DECES, dateEnregistrement, lieuEnregistrement, idAgent);
        this.nomDefunt = nomDefunt;
        this.prenomDefunt = prenomDefunt;
        this.dateDeces = dateDeces;
        this.lieuDeces = lieuDeces;
        this.declarantNom = declarantNom;
        this.declarantPrenom = declarantPrenom;
        this.lienDeclarant = lienDeclarant;
    }

    // Getters et Setters
    public String getNomDefunt() {
        return nomDefunt;
    }

    public void setNomDefunt(String nomDefunt) {
        this.nomDefunt = nomDefunt;
    }

    public String getPrenomDefunt() {
        return prenomDefunt;
    }

    public void setPrenomDefunt(String prenomDefunt) {
        this.prenomDefunt = prenomDefunt;
    }

    public LocalDate getDateDeces() {
        return dateDeces;
    }

    public void setDateDeces(LocalDate dateDeces) {
        this.dateDeces = dateDeces;
    }

    public String getLieuDeces() {
        return lieuDeces;
    }

    public void setLieuDeces(String lieuDeces) {
        this.lieuDeces = lieuDeces;
    }

    public String getCauseDeces() {
        return causeDeces;
    }

    public void setCauseDeces(String causeDeces) {
        this.causeDeces = causeDeces;
    }

    public String getDeclarantNom() {
        return declarantNom;
    }

    public void setDeclarantNom(String declarantNom) {
        this.declarantNom = declarantNom;
    }

    public String getDeclarantPrenom() {
        return declarantPrenom;
    }

    public void setDeclarantPrenom(String declarantPrenom) {
        this.declarantPrenom = declarantPrenom;
    }

    public String getLienDeclarant() {
        return lienDeclarant;
    }

    public void setLienDeclarant(String lienDeclarant) {
        this.lienDeclarant = lienDeclarant;
    }

    public String getNumeroRegistre() {
        return numeroRegistre;
    }

    public void setNumeroRegistre(String numeroRegistre) {
        this.numeroRegistre = numeroRegistre;
    }

    // Méthodes utilitaires
    public String getNomCompletDefunt() {
        return prenomDefunt + " " + nomDefunt;
    }

    public String getNomCompletDeclarant() {
        return declarantPrenom + " " + declarantNom;
    }

    // Implémentation des méthodes abstraites
    @Override
    public String genererExtrait() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder extrait = new StringBuilder();
        extrait.append("=".repeat(60)).append("\n");
        extrait.append("               EXTRAIT D'ACTE DE DÉCÈS\n");
        extrait.append("=".repeat(60)).append("\n\n");

        extrait.append("Numéro d'acte : ").append(numero).append("\n");
        extrait.append("Date d'enregistrement : ").append(dateEnregistrement.format(formatter)).append("\n");
        extrait.append("Lieu d'enregistrement : ").append(lieuEnregistrement).append("\n\n");

        extrait.append("INFORMATIONS SUR LE DÉFUNT :\n");
        extrait.append("Nom et prénom : ").append(getNomCompletDefunt()).append("\n");
        extrait.append("Date de décès : ").append(dateDeces.format(formatter)).append("\n");
        extrait.append("Lieu de décès : ").append(lieuDeces).append("\n");

        if (causeDeces != null && !causeDeces.trim().isEmpty()) {
            extrait.append("Cause du décès : ").append(causeDeces).append("\n");
        }

        extrait.append("\nDÉCLARATION :\n");
        extrait.append("Déclaré par : ").append(getNomCompletDeclarant()).append("\n");
        extrait.append("Lien avec le défunt : ").append(lienDeclarant).append("\n\n");

        if (numeroRegistre != null) {
            extrait.append("Numéro de registre : ").append(numeroRegistre).append("\n");
        }

        extrait.append("\nStatut : ").append(statut.getLibelle()).append("\n");
        extrait.append("=".repeat(60));

        return extrait.toString();
    }

    @Override
    public String getInformationsSpecifiques() {
        return String.format("Défunt: %s, décédé(e) le %s à %s",
                getNomCompletDefunt(), dateDeces, lieuDeces);
    }

    @Override
    public boolean validerDonnees() {
        return nomDefunt != null && !nomDefunt.trim().isEmpty() &&
                prenomDefunt != null && !prenomDefunt.trim().isEmpty() &&
                dateDeces != null &&
                lieuDeces != null && !lieuDeces.trim().isEmpty() &&
                declarantNom != null && !declarantNom.trim().isEmpty() &&
                declarantPrenom != null && !declarantPrenom.trim().isEmpty() &&
                lienDeclarant != null && !lienDeclarant.trim().isEmpty() &&
                dateDeces.isBefore(LocalDate.now().plusDays(1)); // Pas de décès dans le futur
    }

    @Override
    public String toString() {
        return String.format("ActeDeces{numero='%s', défunt='%s', date=%s, statut=%s}",
                numero, getNomCompletDefunt(), dateDeces, statut);
    }
}