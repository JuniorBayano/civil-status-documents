package main.java.com.etatcivil.dao.interfaces;

import main.java.com.etatcivil.model.entities.Acte;
import main.java.com.etatcivil.model.entities.ActeDeces;
import main.java.com.etatcivil.model.entities.ActeMariage;
import main.java.com.etatcivil.model.entities.ActeNaissance;
import main.java.com.etatcivil.model.enums.TypeActe;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface DAO pour la gestion des actes d'état civil
 */
public interface IActeDAO {

    /**
     * Sauvegarde un acte de naissance
     * @param acte L'acte de naissance à sauvegarder
     * @return L'acte sauvegardé avec son ID
     * @throws DAOException En cas d'erreur
     */
    ActeNaissance saveNaissance(ActeNaissance acte) throws DAOException;

    /**
     * Sauvegarde un acte de mariage
     * @param acte L'acte de mariage à sauvegarder
     * @return L'acte sauvegardé avec son ID
     * @throws DAOException En cas d'erreur
     */
    ActeMariage saveMariage(ActeMariage acte) throws DAOException;

    /**
     * Sauvegarde un acte de décès
     * @param acte L'acte de décès à sauvegarder
     * @return L'acte sauvegardé avec son ID
     * @throws DAOException En cas d'erreur
     */
    ActeDeces saveDeces(ActeDeces acte) throws DAOException;

    /**
     * Trouve un acte par son ID
     * @param id ID de l'acte
     * @return Optional contenant l'acte ou vide si non trouvé
     * @throws DAOException En cas d'erreur
     */
    Optional<Acte> findById(int id) throws DAOException;

    /**
     * Trouve un acte par son numéro
     * @param numero Numéro de l'acte
     * @return Optional contenant l'acte ou vide si non trouvé
     * @throws DAOException En cas d'erreur
     */
    Optional<Acte> findByNumero(String numero) throws DAOException;

    /**
     * Trouve une naissance par ID
     * @param id ID de l'acte de naissance
     * @return Optional contenant l'acte de naissance
     * @throws DAOException En cas d'erreur
     */
    Optional<ActeNaissance> findNaissanceById(int id) throws DAOException;

    /**
     * Trouve un mariage par ID
     * @param id ID de l'acte de mariage
     * @return Optional contenant l'acte de mariage
     * @throws DAOException En cas d'erreur
     */
    Optional<ActeMariage> findMariageById(int id) throws DAOException;

    /**
     * Trouve un décès par ID
     * @param id ID de l'acte de décès
     * @return Optional contenant l'acte de décès
     * @throws DAOException En cas d'erreur
     */
    Optional<ActeDeces> findDecesById(int id) throws DAOException;

    /**
     * Trouve tous les actes
     * @return Liste de tous les actes
     * @throws DAOException En cas d'erreur
     */
    List<Acte> findAll() throws DAOException;

    /**
     * Trouve les actes par type
     * @param type Type d'acte
     * @return Liste des actes de ce type
     * @throws DAOException En cas d'erreur
     */
    List<Acte> findByType(TypeActe type) throws DAOException;

    /**
     * Trouve les actes par agent
     * @param idAgent ID de l'agent
     * @return Liste des actes créés par cet agent
     * @throws DAOException En cas d'erreur
     */
    List<Acte> findByAgent(int idAgent) throws DAOException;

    /**
     * Trouve les actes par période
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @return Liste des actes dans cette période
     * @throws DAOException En cas d'erreur
     */
    List<Acte> findByPeriode(LocalDate dateDebut, LocalDate dateFin) throws DAOException;

    /**
     * Trouve les actes par lieu d'enregistrement
     * @param lieu Lieu d'enregistrement
     * @return Liste des actes enregistrés dans ce lieu
     * @throws DAOException En cas d'erreur
     */
    List<Acte> findByLieu(String lieu) throws DAOException;

    /**
     * Recherche d'actes de naissance par nom/prénom
     * @param nom Nom de l'enfant (recherche partielle)
     * @param prenom Prénom de l'enfant (recherche partielle)
     * @return Liste des actes de naissance correspondants
     * @throws DAOException En cas d'erreur
     */
    List<ActeNaissance> rechercherNaissance(String nom, String prenom) throws DAOException;

    /**
     * Recherche d'actes de mariage par nom des époux
     * @param nomEpoux Nom de l'époux (recherche partielle)
     * @param nomEpouse Nom de l'épouse (recherche partielle)
     * @return Liste des actes de mariage correspondants
     * @throws DAOException En cas d'erreur
     */
    List<ActeMariage> rechercherMariage(String nomEpoux, String nomEpouse) throws DAOException;

    /**
     * Recherche d'actes de décès par nom du défunt
     * @param nom Nom du défunt (recherche partielle)
     * @param prenom Prénom du défunt (recherche partielle)
     * @return Liste des actes de décès correspondants
     * @throws DAOException En cas d'erreur
     */
    List<ActeDeces> rechercherDeces(String nom, String prenom) throws DAOException;

    /**
     * Met à jour le statut d'un acte
     * @param id ID de l'acte
     * @param nouveauStatut Nouveau statut
     * @return true si mise à jour réussie
     * @throws DAOException En cas d'erreur
     */
    boolean updateStatut(int id, Acte.StatutActe nouveauStatut) throws DAOException;

    /**
     * Supprime un acte par son ID
     * @param id ID de l'acte à supprimer
     * @return true si suppression réussie
     * @throws DAOException En cas d'erreur
     */
    boolean delete(int id) throws DAOException;

    /**
     * Vérifie si un numéro d'acte existe déjà
     * @param numero Numéro à vérifier
     * @return true si le numéro existe
     * @throws DAOException En cas d'erreur
     */
    boolean existsByNumero(String numero) throws DAOException;

    /**
     * Compte le nombre total d'actes
     * @return Nombre d'actes
     * @throws DAOException En cas d'erreur
     */
    long count() throws DAOException;

    /**
     * Compte le nombre d'actes par type
     * @param type Type d'acte
     * @return Nombre d'actes de ce type
     * @throws DAOException En cas d'erreur
     */
    long countByType(TypeActe type) throws DAOException;

    /**
     * Statistiques mensuelles par type d'acte
     * @param annee Année
     * @param mois Mois (1-12)
     * @return Map des statistiques par type
     * @throws DAOException En cas d'erreur
     */
    Map<TypeActe, Long> getStatistiquesMensuelles(int annee, int mois) throws DAOException;

    /**
     * Statistiques annuelles par type d'acte
     * @param annee Année
     * @return Map des statistiques par type
     * @throws DAOException En cas d'erreur
     */
    Map<TypeActe, Long> getStatistiquesAnnuelles(int annee) throws DAOException;
}