package main.java.com.etatcivil.service;


import main.java.com.etatcivil.dao.impl.ActeDAOImpl;
import main.java.com.etatcivil.dao.interfaces.DAOException;
import main.java.com.etatcivil.model.entities.Acte;
import main.java.com.etatcivil.model.entities.ActeDeces;
import main.java.com.etatcivil.model.entities.ActeMariage;
import main.java.com.etatcivil.model.entities.ActeNaissance;
import main.java.com.etatcivil.model.enums.Sexe;
import main.java.com.etatcivil.util.DateUtil;
import main.java.com.etatcivil.util.NumeroGenerator;
import main.java.com.etatcivil.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service pour la gestion des actes d'état civil
 */
public class ActeService {

    private final ActeDAOImpl acteDAO;
    private final AuthenticationService authService;

    public ActeService(AuthenticationService authService) {
        this.acteDAO = new ActeDAOImpl();
        this.authService = authService;
    }

    /**
     * Crée un acte de naissance
     */
    public ActeNaissance creerActeNaissance(String nomEnfant, String prenomEnfant, Sexe sexe,
                                            LocalDate dateNaissance, String lieuNaissance,
                                            String nomPere, String prenomPere,
                                            String nomMere, String prenomMere,
                                            String lieuEnregistrement) {

        // Vérifier les permissions
        if (!authService.canCreateActe()) {
            System.out.println("❌ Permission insuffisante pour créer un acte");
            return null;
        }

        // Validations des données
        if (!validerDonneesNaissance(nomEnfant, prenomEnfant, sexe, dateNaissance,
                lieuNaissance, nomMere, prenomMere, lieuEnregistrement)) {
            return null;
        }

        try {
            // Créer l'acte
            ActeNaissance acte = new ActeNaissance();
            acte.setNumero(NumeroGenerator.genererNumeroNaissance());
            acte.setDateEnregistrement(LocalDate.now());
            acte.setLieuEnregistrement(lieuEnregistrement);
            acte.setIdAgent(authService.getUtilisateurConnecte().getId());

            // Données spécifiques à la naissance
            acte.setNomEnfant(ValidationUtil.nettoyerNom(nomEnfant));
            acte.setPrenomEnfant(ValidationUtil.nettoyerNom(prenomEnfant));
            acte.setSexe(sexe);
            acte.setDateNaissance(dateNaissance);
            acte.setLieuNaissance(lieuNaissance);
            acte.setNomPere(nomPere != null ? ValidationUtil.nettoyerNom(nomPere) : null);
            acte.setPrenomPere(prenomPere != null ? ValidationUtil.nettoyerNom(prenomPere) : null);
            acte.setNomMere(ValidationUtil.nettoyerNom(nomMere));
            acte.setPrenomMere(ValidationUtil.nettoyerNom(prenomMere));

            // Sauvegarder en base
            ActeNaissance acteSauvegarde = acteDAO.saveNaissance(acte);

            System.out.println("✅ Acte de naissance créé avec succès");
            System.out.println("   Numéro: " + acteSauvegarde.getNumero());
            System.out.println("   Enfant: " + acteSauvegarde.getNomCompletEnfant());

            return acteSauvegarde;

        } catch (DAOException e) {
            System.err.println("❌ Erreur lors de la création de l'acte de naissance: " + e.getMessage());
            return null;
        }
    }

    /**
     * Crée un acte de mariage
     */
    public ActeMariage creerActeMariage(String nomEpoux, String prenomEpoux,
                                        String nomEpouse, String prenomEpouse,
                                        LocalDate dateMariage, String lieuMariage,
                                        String regimeMatrimonial, String lieuEnregistrement) {

        // Vérifier les permissions
        if (!authService.canCreateActe()) {
            System.out.println("❌ Permission insuffisante pour créer un acte");
            return null;
        }

        // Validations des données
        if (!validerDonneesMariage(nomEpoux, prenomEpoux, nomEpouse, prenomEpouse,
                dateMariage, lieuMariage, lieuEnregistrement)) {
            return null;
        }

        try {
            // Créer l'acte
            ActeMariage acte = new ActeMariage();
            acte.setNumero(NumeroGenerator.genererNumeroMariage());
            acte.setDateEnregistrement(LocalDate.now());
            acte.setLieuEnregistrement(lieuEnregistrement);
            acte.setIdAgent(authService.getUtilisateurConnecte().getId());

            // Données spécifiques au mariage
            acte.setNomEpoux(ValidationUtil.nettoyerNom(nomEpoux));
            acte.setPrenomEpoux(ValidationUtil.nettoyerNom(prenomEpoux));
            acte.setNomEpouse(ValidationUtil.nettoyerNom(nomEpouse));
            acte.setPrenomEpouse(ValidationUtil.nettoyerNom(prenomEpouse));
            acte.setDateMariage(dateMariage);
            acte.setLieuMariage(lieuMariage);
            acte.setRegimeMatrimonial(regimeMatrimonial != null ? regimeMatrimonial : "Communauté de biens");

            // Sauvegarder en base
            ActeMariage acteSauvegarde = acteDAO.saveMariage(acte);

            System.out.println("✅ Acte de mariage créé avec succès");
            System.out.println("   Numéro: " + acteSauvegarde.getNumero());
            System.out.println("   Époux: " + acteSauvegarde.getNomCompletEpoux() +
                    " et " + acteSauvegarde.getNomCompletEpouse());

            return acteSauvegarde;

        } catch (DAOException e) {
            System.err.println("❌ Erreur lors de la création de l'acte de mariage: " + e.getMessage());
            return null;
        }
    }

    /**
     * Crée un acte de décès
     */
    public ActeDeces creerActeDeces(String nomDefunt, String prenomDefunt,
                                    LocalDate dateDeces, String lieuDeces, String causeDeces,
                                    String declarantNom, String declarantPrenom, String lienDeclarant,
                                    String lieuEnregistrement) {

        // Vérifier les permissions
        if (!authService.canCreateActe()) {
            System.out.println("❌ Permission insuffisante pour créer un acte");
            return null;
        }

        // Validations des données
        if (!validerDonneesDeces(nomDefunt, prenomDefunt, dateDeces, lieuDeces,
                declarantNom, declarantPrenom, lienDeclarant, lieuEnregistrement)) {
            return null;
        }

        try {
            // Créer l'acte
            ActeDeces acte = new ActeDeces();
            acte.setNumero(NumeroGenerator.genererNumeroDeces());
            acte.setDateEnregistrement(LocalDate.now());
            acte.setLieuEnregistrement(lieuEnregistrement);
            acte.setIdAgent(authService.getUtilisateurConnecte().getId());

            // Données spécifiques au décès
            acte.setNomDefunt(ValidationUtil.nettoyerNom(nomDefunt));
            acte.setPrenomDefunt(ValidationUtil.nettoyerNom(prenomDefunt));
            acte.setDateDeces(dateDeces);
            acte.setLieuDeces(lieuDeces);
            acte.setCauseDeces(causeDeces);
            acte.setDeclarantNom(ValidationUtil.nettoyerNom(declarantNom));
            acte.setDeclarantPrenom(ValidationUtil.nettoyerNom(declarantPrenom));
            acte.setLienDeclarant(lienDeclarant);

            // Sauvegarder en base
            ActeDeces acteSauvegarde = acteDAO.saveDeces(acte);

            System.out.println("✅ Acte de décès créé avec succès");
            System.out.println("   Numéro: " + acteSauvegarde.getNumero());
            System.out.println("   Défunt: " + acteSauvegarde.getNomCompletDefunt());

            return acteSauvegarde;

        } catch (DAOException e) {
            System.err.println("❌ Erreur lors de la création de l'acte de décès: " + e.getMessage());
            return null;
        }
    }

    /**
     * Recherche un acte par son numéro
     */
    public Optional<Acte> rechercherActeParNumero(String numero) {
        try {
            return acteDAO.findByNumero(numero);
        } catch (DAOException e) {
            System.err.println("❌ Erreur lors de la recherche: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Signe un acte (réservé au chef)
     */
    public boolean signerActe(int idActe) {
        if (!authService.canSignActe()) {
            System.out.println("❌ Seul le chef d'état civil peut signer les actes");
            return false;
        }

        try {
            boolean success = acteDAO.updateStatut(idActe, Acte.StatutActe.SIGNE);
            if (success) {
                System.out.println("✅ Acte signé avec succès");
            } else {
                System.out.println("❌ Acte non trouvé");
            }
            return success;

        } catch (DAOException e) {
            System.err.println("❌ Erreur lors de la signature: " + e.getMessage());
            return false;
        }
    }

    /**
     * Génère l'extrait d'un acte
     */
    public String genererExtrait(String numeroActe) {
        try {
            Optional<Acte> acte = acteDAO.findByNumero(numeroActe);
            if (acte.isPresent()) {
                Acte monActe = acte.get();

                // Génère et ouvre le PDF
                if (monActe instanceof ActeNaissance) {
                    ((ActeNaissance) monActe).genererExtraitPDF();
                } else if (monActe instanceof ActeMariage) {
                    ((ActeMariage) monActe).genererExtraitPDF();
                } else if (monActe instanceof ActeDeces) {
                    ((ActeDeces) monActe).genererExtraitPDF();
                }

                return "✅ PDF généré avec succès.";
            } else {
                return "❌ Acte non trouvé avec le numéro: " + numeroActe;
            }
        } catch (DAOException e) {
            return "❌ Erreur lors de la génération de l'extrait: " + e.getMessage();
        }
    }


    /**
     * Liste tous les actes (selon les permissions)
     */
    public List<Acte> listerActes() {
        if (!authService.canViewAllActes()) {
            System.out.println("❌ Permission insuffisante pour voir tous les actes");
            return List.of();
        }

        try {
            return acteDAO.findAll();
        } catch (DAOException e) {
            System.err.println("❌ Erreur lors de la récupération des actes: " + e.getMessage());
            return List.of();
        }
    }

    // Méthodes de validation privées

    private boolean validerDonneesNaissance(String nomEnfant, String prenomEnfant, Sexe sexe,
                                            LocalDate dateNaissance, String lieuNaissance,
                                            String nomMere, String prenomMere, String lieuEnregistrement) {

        if (!ValidationUtil.isNomValide(nomEnfant)) {
            System.out.println("❌ Nom de l'enfant invalide");
            return false;
        }

        if (!ValidationUtil.isPrenomValide(prenomEnfant)) {
            System.out.println("❌ Prénom de l'enfant invalide");
            return false;
        }

        if (sexe == null) {
            System.out.println("❌ Sexe requis");
            return false;
        }

        if (!DateUtil.isDateNaissanceValide(dateNaissance)) {
            System.out.println("❌ Date de naissance invalide");
            return false;
        }

        if (!ValidationUtil.isLieuValide(lieuNaissance)) {
            System.out.println("❌ Lieu de naissance invalide");
            return false;
        }

        if (!ValidationUtil.isNomValide(nomMere)) {
            System.out.println("❌ Nom de la mère invalide");
            return false;
        }

        if (!ValidationUtil.isPrenomValide(prenomMere)) {
            System.out.println("❌ Prénom de la mère invalide");
            return false;
        }

        if (!ValidationUtil.isLieuValide(lieuEnregistrement)) {
            System.out.println("❌ Lieu d'enregistrement invalide");
            return false;
        }

        return true;
    }

    private boolean validerDonneesMariage(String nomEpoux, String prenomEpoux,
                                          String nomEpouse, String prenomEpouse,
                                          LocalDate dateMariage, String lieuMariage,
                                          String lieuEnregistrement) {

        if (!ValidationUtil.isNomValide(nomEpoux) || !ValidationUtil.isPrenomValide(prenomEpoux)) {
            System.out.println("❌ Nom ou prénom de l'époux invalide");
            return false;
        }

        if (!ValidationUtil.isNomValide(nomEpouse) || !ValidationUtil.isPrenomValide(prenomEpouse)) {
            System.out.println("❌ Nom ou prénom de l'épouse invalide");
            return false;
        }

        if (!DateUtil.isDateMariageValide(dateMariage)) {
            System.out.println("❌ Date de mariage invalide");
            return false;
        }

        if (!ValidationUtil.isLieuValide(lieuMariage)) {
            System.out.println("❌ Lieu de mariage invalide");
            return false;
        }

        if (!ValidationUtil.isLieuValide(lieuEnregistrement)) {
            System.out.println("❌ Lieu d'enregistrement invalide");
            return false;
        }

        return true;
    }

    /**
     * Valide un acte (réservé au chef)
     */
    public boolean validerActe(int idActe) {
        if (!authService.canSignActe()) {
            System.out.println("❌ Seul le chef d'état civil peut valider les actes");
            return false;
        }

        try {
            boolean success = acteDAO.updateStatut(idActe, Acte.StatutActe.SIGNE);
            if (success) {
                System.out.println("✅ Acte validé avec succès");
            } else {
                System.out.println("❌ Acte non trouvé");
            }
            return success;

        } catch (DAOException e) {
            System.err.println("❌ Erreur lors de la validation: " + e.getMessage());
            return false;
        }
    }

    private boolean validerDonneesDeces(String nomDefunt, String prenomDefunt,
                                        LocalDate dateDeces, String lieuDeces,
                                        String declarantNom, String declarantPrenom,
                                        String lienDeclarant, String lieuEnregistrement) {

        if (!ValidationUtil.isNomValide(nomDefunt) || !ValidationUtil.isPrenomValide(prenomDefunt)) {
            System.out.println("❌ Nom ou prénom du défunt invalide");
            return false;
        }

        if (!DateUtil.isDateDecesValide(dateDeces)) {
            System.out.println("❌ Date de décès invalide");
            return false;
        }

        if (!ValidationUtil.isLieuValide(lieuDeces)) {
            System.out.println("❌ Lieu de décès invalide");
            return false;
        }

        if (!ValidationUtil.isNomValide(declarantNom) || !ValidationUtil.isPrenomValide(declarantPrenom)) {
            System.out.println("❌ Nom ou prénom du déclarant invalide");
            return false;
        }

        if (!ValidationUtil.isNonVide(lienDeclarant)) {
            System.out.println("❌ Lien avec le défunt requis");
            return false;
        }

        if (!ValidationUtil.isLieuValide(lieuEnregistrement)) {
            System.out.println("❌ Lieu d'enregistrement invalide");
            return false;
        }

        return true;
    }
    public Map<String, Long> getStatistiquesParTypeEtPeriode(int annee, Optional<Integer> mois) {
        return acteDAO.getStatistiquesParTypeEtPeriode(annee, mois);
    }




}