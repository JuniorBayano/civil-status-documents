package main.java.com.etatcivil.service;



import main.java.com.etatcivil.dao.impl.UtilisateurDAOImpl;
import main.java.com.etatcivil.dao.interfaces.DAOException;
import main.java.com.etatcivil.model.entities.Utilisateur;
import main.java.com.etatcivil.model.enums.RoleUtilisateur;
import main.java.com.etatcivil.util.ValidationUtil;

import java.util.Optional;

/**
 * Service d'authentification et de gestion des sessions utilisateur
 */
public class AuthenticationService {

    private final UtilisateurDAOImpl utilisateurDAO;
    private Utilisateur utilisateurConnecte;

    public AuthenticationService() {
        this.utilisateurDAO = new UtilisateurDAOImpl();
        this.utilisateurConnecte = null;
    }

    /**
     * Authentifie un utilisateur
     * @param login Login de l'utilisateur
     * @param password Mot de passe
     * @return true si authentification réussie
     */
    public boolean authenticate(String login, String password) {
        try {
            // Validation des paramètres
            if (!ValidationUtil.isNonVide(login) || !ValidationUtil.isNonVide(password)) {
                System.out.println("❌ Login et mot de passe requis");
                return false;
            }

            // Tentative d'authentification
            Optional<Utilisateur> utilisateur = utilisateurDAO.authenticate(login.trim(), password);

            if (utilisateur.isPresent()) {
                this.utilisateurConnecte = utilisateur.get();
                System.out.println("✅ Connexion réussie pour: " + utilisateurConnecte.getNomComplet());
                System.out.println("   Rôle: " + utilisateurConnecte.getRole().getLibelle());
                return true;
            } else {
                System.out.println("❌ Login ou mot de passe incorrect");
                return false;
            }

        } catch (DAOException e) {
            System.err.println("❌ Erreur lors de l'authentification: " + e.getMessage());
            return false;
        }
    }

    /**
     * Déconnecte l'utilisateur actuel
     */
    public void logout() {
        if (utilisateurConnecte != null) {
            System.out.println("👋 Déconnexion de: " + utilisateurConnecte.getNomComplet());
            utilisateurConnecte = null;
        }
    }

    /**
     * Vérifie si un utilisateur est connecté
     * @return true si un utilisateur est connecté
     */
    public boolean isUserConnected() {
        return utilisateurConnecte != null;
    }

    /**
     * Retourne l'utilisateur connecté
     * @return Utilisateur connecté ou null
     */
    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    /**
     * Vérifie si l'utilisateur connecté a un rôle spécifique
     * @param role Rôle à vérifier
     * @return true si l'utilisateur a ce rôle
     */
    public boolean hasRole(RoleUtilisateur role) {
        return utilisateurConnecte != null && utilisateurConnecte.getRole() == role;
    }

    /**
     * Vérifie si l'utilisateur connecté est un agent
     * @return true si agent
     */
    public boolean isAgent() {
        return hasRole(RoleUtilisateur.AGENT);
    }

    /**
     * Vérifie si l'utilisateur connecté est un chef
     * @return true si chef
     */
    public boolean isChef() {
        return hasRole(RoleUtilisateur.CHEF);
    }

    /**
     * Vérifie si l'utilisateur connecté est un invité
     * @return true si invité
     */
    public boolean isInvite() {
        return hasRole(RoleUtilisateur.INVITE);
    }

    /**
     * Vérifie les permissions pour créer un acte
     * @return true si autorisé
     */
    public boolean canCreateActe() {
        return isAgent() || isChef();
    }

    /**
     * Vérifie les permissions pour modifier un acte
     * @param idAgentCreateur ID de l'agent qui a créé l'acte
     * @return true si autorisé
     */
    public boolean canModifyActe(int idAgentCreateur) {
        if (isChef()) {
            return true; // Le chef peut tout modifier
        }

        if (isAgent() && utilisateurConnecte != null) {
            return utilisateurConnecte.getId() == idAgentCreateur; // L'agent peut modifier ses propres actes
        }

        return false;
    }

    /**
     * Vérifie les permissions pour signer un acte
     * @return true si autorisé
     */
    public boolean canSignActe() {
        return isChef();
    }

    /**
     * Vérifie les permissions pour consulter tous les actes
     * @return true si autorisé
     */
    public boolean canViewAllActes() {
        return isChef() || isAgent();
    }

    /**
     * Vérifie les permissions pour gérer les utilisateurs
     * @return true si autorisé
     */
    public boolean canManageUsers() {
        return isChef();
    }

    /**
     * Vérifie les permissions pour voir les statistiques
     * @return true si autorisé
     */
    public boolean canViewStatistics() {
        return isChef();
    }

    /**
     * Change le mot de passe de l'utilisateur connecté
     * @param ancienMotDePasse Ancien mot de passe
     * @param nouveauMotDePasse Nouveau mot de passe
     * @return true si changement réussi
     */
    public boolean changerMotDePasse(String ancienMotDePasse, String nouveauMotDePasse) {
        if (!isUserConnected()) {
            System.out.println("❌ Aucun utilisateur connecté");
            return false;
        }

        // Vérifier l'ancien mot de passe
        if (!utilisateurConnecte.getPassword().equals(ancienMotDePasse)) {
            System.out.println("❌ Ancien mot de passe incorrect");
            return false;
        }

        // Valider le nouveau mot de passe
        if (!ValidationUtil.isPasswordValide(nouveauMotDePasse)) {
            System.out.println("❌ Le nouveau mot de passe doit contenir au moins 6 caractères");
            return false;
        }

        try {
            boolean success = utilisateurDAO.updatePassword(utilisateurConnecte.getId(), nouveauMotDePasse);
            if (success) {
                utilisateurConnecte.setPassword(nouveauMotDePasse);
                System.out.println("✅ Mot de passe changé avec succès");
            }
            return success;

        } catch (DAOException e) {
            System.err.println("❌ Erreur lors du changement de mot de passe: " + e.getMessage());
            return false;
        }
    }

    /**
     * Créer un nouvel utilisateur (réservé au chef)
     * @param nom Nom
     * @param prenom Prénom
     * @param role Rôle
     * @param login Login
     * @param password Mot de passe
     * @return Utilisateur créé ou null en cas d'erreur
     */
    public Utilisateur creerUtilisateur(String nom, String prenom, RoleUtilisateur role,
                                        String login, String password) {
        if (!canManageUsers()) {
            System.out.println("❌ Permission insuffisante pour créer un utilisateur");
            return null;
        }

        // Validations
        if (!ValidationUtil.isNomValide(nom)) {
            System.out.println("❌ Nom invalide");
            return null;
        }

        if (!ValidationUtil.isPrenomValide(prenom)) {
            System.out.println("❌ Prénom invalide");
            return null;
        }

        if (!ValidationUtil.isLoginValide(login)) {
            System.out.println("❌ Login invalide (3-20 caractères alphanumériques)");
            return null;
        }

        if (!ValidationUtil.isPasswordValide(password)) {
            System.out.println("❌ Mot de passe invalide (minimum 6 caractères)");
            return null;
        }

        try {
            // Vérifier si le login existe déjà
            if (utilisateurDAO.existsByLogin(login)) {
                System.out.println("❌ Ce login existe déjà");
                return null;
            }

            // Créer l'utilisateur
            Utilisateur nouvelUtilisateur = new Utilisateur(
                    ValidationUtil.nettoyerNom(nom),
                    ValidationUtil.nettoyerNom(prenom),
                    role,
                    login,
                    password
            );

            Utilisateur utilisateurCree = utilisateurDAO.save(nouvelUtilisateur);
            System.out.println("✅ Utilisateur créé: " + utilisateurCree.getNomComplet());

            return utilisateurCree;

        } catch (DAOException e) {
            System.err.println("❌ Erreur lors de la création de l'utilisateur: " + e.getMessage());
            return null;
        }
    }

    /**
     * Affiche les informations de l'utilisateur connecté
     */
    public void afficherInfosUtilisateur() {
        if (utilisateurConnecte != null) {
            System.out.println("\n👤 UTILISATEUR CONNECTÉ:");
            System.out.println("   Nom: " + utilisateurConnecte.getNomComplet());
            System.out.println("   Login: " + utilisateurConnecte.getLogin());
            System.out.println("   Rôle: " + utilisateurConnecte.getRole().getLibelle());
            System.out.println("   Statut: " + (utilisateurConnecte.isActif() ? "Actif" : "Inactif"));
            System.out.println("   Membre depuis: " + utilisateurConnecte.getDateCreation().toLocalDate());
        } else {
            System.out.println("❌ Aucun utilisateur connecté");
        }
    }
}