package main.java.com.etatcivil.model.entities;


import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import main.java.com.etatcivil.model.enums.TypeActe;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        return "EXTRAT D'ACTE DE DECES\n" +
                "Numéro: " + getNumero() + "\n" +
                "Défunt: " + getPrenomDefunt() + " " + getNomDefunt() + "\n" +
                "Décédé le: " + getDateDeces() + " à " + getLieuDeces() +
                (getCauseDeces() != null ? " (" + getCauseDeces() + ")" : "") + "\n" +
                "Déclaré par: " + getDeclarantPrenom() + " " + getDeclarantNom() +
                " (" + getLienDeclarant() + ")\n" +
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