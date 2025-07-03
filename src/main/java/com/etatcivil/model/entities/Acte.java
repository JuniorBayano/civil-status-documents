package main.java.com.etatcivil.model.entities;

import main.java.com.etatcivil.model.enums.TypeActe;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Acte {

    public enum StatutActe {
        EN_ATTENTE("En attente"),
        SIGNE("Signé"),
        ARCHIVE("Archivé");

        private final String libelle;

        StatutActe(String libelle) {
            this.libelle = libelle;
        }

        public String getLibelle() {
            return libelle;
        }

        @Override
        public String toString() {
            return libelle;
        }
    }

    // Attributs communs à tous les actes
    protected int id;
    protected String numero;
    protected TypeActe type;
    protected LocalDate dateEnregistrement;
    protected String lieuEnregistrement;
    protected int idAgent;
    protected LocalDateTime dateCreation;
    protected StatutActe statut;

    // Constructeurs
    public Acte() {
        this.dateCreation = LocalDateTime.now();
        this.statut = StatutActe.EN_ATTENTE;
        this.dateEnregistrement = LocalDate.now();
    }

    public Acte(String numero, TypeActe type, LocalDate dateEnregistrement,
                String lieuEnregistrement, int idAgent) {
        this();
        this.numero = numero;
        this.type = type;
        this.dateEnregistrement = dateEnregistrement;
        this.lieuEnregistrement = lieuEnregistrement;
        this.idAgent = idAgent;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TypeActe getType() {
        return type;
    }

    public void setType(TypeActe type) {
        this.type = type;
    }

    public LocalDate getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(LocalDate dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    public String getLieuEnregistrement() {
        return lieuEnregistrement;
    }

    public void setLieuEnregistrement(String lieuEnregistrement) {
        this.lieuEnregistrement = lieuEnregistrement;
    }

    public int getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public StatutActe getStatut() {
        return statut;
    }

    public void setStatut(StatutActe statut) {
        this.statut = statut;
    }

    // Méthodes abstraites à implémenter par les classes filles
    public abstract String genererExtrait();
    public abstract String getInformationsSpecifiques();
    public abstract boolean validerDonnees();

    // Méthodes communes
    public boolean isSigne() {
        return statut == StatutActe.SIGNE;
    }

    public boolean isArchive() {
        return statut == StatutActe.ARCHIVE;
    }

    public boolean isEnAttente() {
        return statut == StatutActe.EN_ATTENTE;
    }

    public void signer() {
        this.statut = StatutActe.SIGNE;
    }

    public void archiver() {
        this.statut = StatutActe.ARCHIVE;
    }

    @Override
    public String toString() {
        return String.format("Acte{id=%d, numero='%s', type=%s, date=%s, lieu='%s', statut=%s}",
                id, numero, type, dateEnregistrement, lieuEnregistrement, statut);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Acte acte = (Acte) obj;
        return id == acte.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}