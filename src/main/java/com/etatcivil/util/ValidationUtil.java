package main.java.com.etatcivil.util;

import java.util.regex.Pattern;

/**
 * Classe utilitaire pour la validation des données dans le système d'état civil
 */
public class ValidationUtil {

    // Expressions régulières pour les validations
    private static final Pattern PATTERN_NOM = Pattern.compile("^[a-zA-ZÀ-ÿ\\s\\-']{2,50}$");
    private static final Pattern PATTERN_PRENOM = Pattern.compile("^[a-zA-ZÀ-ÿ\\s\\-']{2,50}$");
    private static final Pattern PATTERN_LOGIN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    private static final Pattern PATTERN_NUMERO_ACTE = Pattern.compile("^[A-Z][0-9]{7}$"); // Ex: N1234567
    private static final Pattern PATTERN_LIEU = Pattern.compile("^[a-zA-ZÀ-ÿ\\s\\-',0-9]{2,100}$");
    private static final Pattern PATTERN_REGISTRE = Pattern.compile("^[A-Z0-9\\-]{3,20}$");

    /**
     * Valide un nom ou prénom
     * @param nom Le nom à valider
     * @return true si valide
     */
    public static boolean isNomValide(String nom) {
        return nom != null && PATTERN_NOM.matcher(nom.trim()).matches();
    }

    /**
     * Valide un prénom
     * @param prenom Le prénom à valider
     * @return true si valide
     */
    public static boolean isPrenomValide(String prenom) {
        return prenom != null && PATTERN_PRENOM.matcher(prenom.trim()).matches();
    }

    /**
     * Valide un login utilisateur
     * @param login Le login à valider
     * @return true si valide (3-20 caractères alphanumériques et underscore)
     */
    public static boolean isLoginValide(String login) {
        return login != null && PATTERN_LOGIN.matcher(login.trim()).matches();
    }

    /**
     * Valide un mot de passe
     * @param password Le mot de passe à valider
     * @return true si valide (minimum 6 caractères)
     */
    public static boolean isPasswordValide(String password) {
        return password != null && password.length() >= 6;
    }

    /**
     * Valide un mot de passe fort
     * @param password Le mot de passe à valider
     * @return true si mot de passe fort (8+ caractères, majuscule, minuscule, chiffre)
     */
    public static boolean isPasswordFort(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);

        return hasUpper && hasLower && hasDigit;
    }

    /**
     * Valide un numéro d'acte
     * @param numero Le numéro d'acte à valider
     * @return true si valide (format: 1 lettre + 7 chiffres, ex: N1234567)
     */
    public static boolean isNumeroActeValide(String numero) {
        return numero != null && PATTERN_NUMERO_ACTE.matcher(numero.trim()).matches();
    }

    /**
     * Valide un lieu (naissance, mariage, décès, enregistrement)
     * @param lieu Le lieu à valider
     * @return true si valide
     */
    public static boolean isLieuValide(String lieu) {
        return lieu != null && PATTERN_LIEU.matcher(lieu.trim()).matches();
    }

    /**
     * Valide un numéro de registre
     * @param registre Le numéro de registre à valider
     * @return true si valide
     */
    public static boolean isRegistreValide(String registre) {
        return registre != null && PATTERN_REGISTRE.matcher(registre.trim()).matches();
    }

    /**
     * Valide que le texte n'est pas vide ou null
     * @param texte Le texte à valider
     * @return true si non vide
     */
    public static boolean isNonVide(String texte) {
        return texte != null && !texte.trim().isEmpty();
    }

    /**
     * Valide que le texte a une longueur minimale
     * @param texte Le texte à valider
     * @param longueurMin Longueur minimale requise
     * @return true si la longueur est suffisante
     */
    public static boolean isLongueurMinimale(String texte, int longueurMin) {
        return texte != null && texte.trim().length() >= longueurMin;
    }

    /**
     * Valide que le texte a une longueur maximale
     * @param texte Le texte à valider
     * @param longueurMax Longueur maximale autorisée
     * @return true si la longueur est acceptable
     */
    public static boolean isLongueurMaximale(String texte, int longueurMax) {
        return texte != null && texte.trim().length() <= longueurMax;
    }

    /**
     * Valide que le texte est dans une plage de longueur
     * @param texte Le texte à valider
     * @param longueurMin Longueur minimale
     * @param longueurMax Longueur maximale
     * @return true si la longueur est dans la plage
     */
    public static boolean isLongueurValide(String texte, int longueurMin, int longueurMax) {
        return isLongueurMinimale(texte, longueurMin) && isLongueurMaximale(texte, longueurMax);
    }

    /**
     * Nettoie et normalise un nom/prénom
     * @param nom Le nom à nettoyer
     * @return Nom nettoyé (première lettre majuscule, reste minuscule)
     */
    public static String nettoyerNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            return "";
        }

        String nomNettoye = nom.trim().toLowerCase();

        // Première lettre en majuscule
        if (!nomNettoye.isEmpty()) {
            nomNettoye = Character.toUpperCase(nomNettoye.charAt(0)) + nomNettoye.substring(1);
        }

        // Majuscule après un espace ou un tiret
        StringBuilder sb = new StringBuilder(nomNettoye);
        for (int i = 1; i < sb.length(); i++) {
            if (sb.charAt(i - 1) == ' ' || sb.charAt(i - 1) == '-') {
                sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
            }
        }

        return sb.toString();
    }

    /**
     * Valide un ID (doit être positif)
     * @param id L'ID à valider
     * @return true si valide (> 0)
     */
    public static boolean isIdValide(int id) {
        return id > 0;
    }

    /**
     * Génère un message d'erreur pour un champ invalide
     * @param nomChamp Nom du champ
     * @param valeur Valeur du champ
     * @param regle Règle de validation
     * @return Message d'erreur formaté
     */
    public static String genererMessageErreur(String nomChamp, String valeur, String regle) {
        return String.format("Erreur sur le champ '%s' (valeur: '%s'): %s",
                nomChamp, valeur != null ? valeur : "null", regle);
    }

    /**
     * Valide les données communes d'un acte
     * @param numero Numéro d'acte
     * @param lieuEnregistrement Lieu d'enregistrement
     * @param idAgent ID de l'agent
     * @return true si toutes les données sont valides
     */
    public static boolean validerDonneesActe(String numero, String lieuEnregistrement, int idAgent) {
        return isNumeroActeValide(numero) &&
                isLieuValide(lieuEnregistrement) &&
                isIdValide(idAgent);
    }
}