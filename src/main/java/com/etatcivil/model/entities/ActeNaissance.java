package main.java.com.etatcivil.model.entities;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import main.java.com.etatcivil.model.enums.Sexe;
import main.java.com.etatcivil.model.enums.TypeActe;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        return "EXTRAT D'ACTE DE NAISSANCE\n" +
                "Numéro: " + getNumero() + "\n" +
                "Enfant: " + getPrenomEnfant() + " " + getNomEnfant() + "\n" +
                "Né(e) le: " + getDateNaissance() + " à " + getLieuNaissance() + "\n" +
                "Fils/Fille de: " + (getPrenomPere() != null ? getPrenomPere() + " " + getNomPere() : "[non déclaré]") +
                " et " + getPrenomMere() + " " + getNomMere() + "\n" +
                "Enregistré le: " + getDateEnregistrement() + " à " + getLieuEnregistrement() + "\n" +
                "Statut: " + getStatut();
    }

    public void genererExtraitPDF() {
        Document document = new Document();
        String nomFichier = "Extrait_" + getNumero() + ".pdf";

        try {
            PdfWriter.getInstance(document, new FileOutputStream(nomFichier));
            document.open();

            // Le contenu : réutilise exactement ta méthode genererExtrait()
            document.add(new Paragraph(genererExtrait()));

            System.out.println("✅ PDF généré : " + nomFichier);

            // Ouverture automatique dans Aperçu (macOS)
            File fichier = new File(nomFichier);
            if (fichier.exists()) {
                Desktop.getDesktop().open(fichier);
            }

        } catch (DocumentException | IOException e) {
            System.out.println("❌ Erreur PDF : " + e.getMessage());
        } finally {
            document.close();
        }
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