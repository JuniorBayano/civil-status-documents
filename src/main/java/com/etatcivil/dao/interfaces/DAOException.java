package main.java.com.etatcivil.dao.interfaces;

/**
 * Exception personnalisée pour les erreurs de la couche DAO
 */
public class DAOException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructeur par défaut
     */
    public DAOException() {
        super();
    }

    /**
     * Constructeur avec message
     * @param message Message d'erreur
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructeur avec cause
     * @param cause Cause de l'exception
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructeur avec message et cause
     * @param message Message d'erreur
     * @param cause Cause de l'exception
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crée une DAOException à partir d'une SQLException
     * @param operation Opération qui a échoué
     * @param cause SQLException d'origine
     * @return DAOException formatée
     */
    public static DAOException fromSQLException(String operation, Throwable cause) {
        String message = String.format("Erreur lors de l'opération '%s': %s",
                operation, cause.getMessage());
        return new DAOException(message, cause);
    }

    /**
     * Crée une DAOException pour un objet non trouvé
     * @param type Type d'objet (ex: "Utilisateur", "Acte")
     * @param critere Critère de recherche (ex: "ID=123", "login=admin")
     * @return DAOException formatée
     */
    public static DAOException objetNonTrouve(String type, String critere) {
        String message = String.format("%s non trouvé avec le critère: %s", type, critere);
        return new DAOException(message);
    }

    /**
     * Crée une DAOException pour une contrainte violée
     * @param contrainte Nom de la contrainte
     * @param valeur Valeur qui pose problème
     * @return DAOException formatée
     */
    public static DAOException contrainteViolee(String contrainte, String valeur) {
        String message = String.format("Contrainte '%s' violée pour la valeur: %s",
                contrainte, valeur);
        return new DAOException(message);
    }
}