package main.java.com.etatcivil.dao.interfaces;

import main.java.com.etatcivil.model.entities.Utilisateur;
import main.java.com.etatcivil.model.enums.RoleUtilisateur;

import java.util.List;
import java.util.Optional;

/**
 * Interface DAO pour la gestion des utilisateurs
 */
public interface IUtilisateurDAO {

    /**
     * Sauvegarde un utilisateur (create ou update)
     * @param utilisateur L'utilisateur à sauvegarder
     * @return L'utilisateur sauvegardé avec son ID
     * @throws DAOException En cas d'erreur
     */
    Utilisateur save(Utilisateur utilisateur) throws DAOException;

    /**
     * Trouve un utilisateur par son ID
     * @param id ID de l'utilisateur
     * @return Optional contenant l'utilisateur ou vide si non trouvé
     * @throws DAOException En cas d'erreur
     */
    Optional<Utilisateur> findById(int id) throws DAOException;

    /**
     * Trouve un utilisateur par son login
     * @param login Login de l'utilisateur
     * @return Optional contenant l'utilisateur ou vide si non trouvé
     * @throws DAOException En cas d'erreur
     */
    Optional<Utilisateur> findByLogin(String login) throws DAOException;

    /**
     * Authentifie un utilisateur
     * @param login Login de l'utilisateur
     * @param password Mot de passe
     * @return Optional contenant l'utilisateur si authentification réussie
     * @throws DAOException En cas d'erreur
     */
    Optional<Utilisateur> authenticate(String login, String password) throws DAOException;

    /**
     * Trouve tous les utilisateurs
     * @return Liste de tous les utilisateurs
     * @throws DAOException En cas d'erreur
     */
    List<Utilisateur> findAll() throws DAOException;

    /**
     * Trouve les utilisateurs par rôle
     * @param role Rôle recherché
     * @return Liste des utilisateurs ayant ce rôle
     * @throws DAOException En cas d'erreur
     */
    List<Utilisateur> findByRole(RoleUtilisateur role) throws DAOException;

    /**
     * Trouve les utilisateurs actifs
     * @return Liste des utilisateurs actifs
     * @throws DAOException En cas d'erreur
     */
    List<Utilisateur> findActifs() throws DAOException;

    /**
     * Met à jour le mot de passe d'un utilisateur
     * @param id ID de l'utilisateur
     * @param nouveauMotDePasse Nouveau mot de passe
     * @return true si mise à jour réussie
     * @throws DAOException En cas d'erreur
     */
    boolean updatePassword(int id, String nouveauMotDePasse) throws DAOException;

    /**
     * Active ou désactive un utilisateur
     * @param id ID de l'utilisateur
     * @param actif true pour activer, false pour désactiver
     * @return true si mise à jour réussie
     * @throws DAOException En cas d'erreur
     */
    boolean updateStatut(int id, boolean actif) throws DAOException;

    /**
     * Supprime un utilisateur par son ID
     * @param id ID de l'utilisateur à supprimer
     * @return true si suppression réussie
     * @throws DAOException En cas d'erreur
     */
    boolean delete(int id) throws DAOException;

    /**
     * Vérifie si un login existe déjà
     * @param login Login à vérifier
     * @return true si le login existe
     * @throws DAOException En cas d'erreur
     */
    boolean existsByLogin(String login) throws DAOException;

    /**
     * Compte le nombre total d'utilisateurs
     * @return Nombre d'utilisateurs
     * @throws DAOException En cas d'erreur
     */
    long count() throws DAOException;

    /**
     * Compte le nombre d'utilisateurs par rôle
     * @param role Rôle à compter
     * @return Nombre d'utilisateurs avec ce rôle
     * @throws DAOException En cas d'erreur
     */
    long countByRole(RoleUtilisateur role) throws DAOException;

    /**
     * Recherche des utilisateurs par nom ou prénom (recherche partielle)
     * @param critere Critère de recherche
     * @return Liste des utilisateurs correspondants
     * @throws DAOException En cas d'erreur
     */
    List<Utilisateur> rechercherParNom(String critere) throws DAOException;
}