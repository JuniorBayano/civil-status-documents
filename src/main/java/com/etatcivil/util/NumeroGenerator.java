package main.java.com.etatcivil.util;

import main.java.com.etatcivil.model.enums.TypeActe;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Classe utilitaire pour générer des numéros d'actes uniques
 */
public class NumeroGenerator {

    // Compteurs atomiques pour assurer l'unicité en environnement concurrent
    private static final AtomicInteger compteurNaissance = new AtomicInteger(1);
    private static final AtomicInteger compteurMariage = new AtomicInteger(1);
    private static final AtomicInteger compteurDeces = new AtomicInteger(1);

    // Préfixes pour chaque type d'acte
    private static final String PREFIX_NAISSANCE = "N";
    private static final String PREFIX_MARIAGE = "M";
    private static final String PREFIX_DECES = "D";

    /**
     * Génère un numéro d'acte unique selon le type
     * @param typeActe Type d'acte (NAISSANCE, MARIAGE, DECES)
     * @return Numéro d'acte formaté (ex: N2025001)
     */
    public static String genererNumero(TypeActe typeActe) {
        if (typeActe == null) {
            throw new IllegalArgumentException("Le type d'acte ne peut pas être null");
        }

        String annee = String.valueOf(LocalDate.now().getYear());
        String prefix;
        int numero;

        switch (typeActe) {
            case NAISSANCE:
                prefix = PREFIX_NAISSANCE;
                numero = compteurNaissance.getAndIncrement();
                break;

            case MARIAGE:
                prefix = PREFIX_MARIAGE;
                numero = compteurMariage.getAndIncrement();
                break;

            case DECES:
                prefix = PREFIX_DECES;
                numero = compteurDeces.getAndIncrement();
                break;

            default:
                throw new IllegalArgumentException("Type d'acte non supporté: " + typeActe);
        }

        // Format: [PREFIX][ANNEE][NUMERO] ex: N2025001
        return String.format("%s%s%03d", prefix, annee, numero);
    }

    /**
     * Génère un numéro d'acte de naissance
     * @return Numéro d'acte de naissance (ex: N2025001)
     */
    public static String genererNumeroNaissance() {
        return genererNumero(TypeActe.NAISSANCE);
    }

    /**
     * Génère un numéro d'acte de mariage
     * @return Numéro d'acte de mariage (ex: M2025001)
     */
    public static String genererNumeroMariage() {
        return genererNumero(TypeActe.MARIAGE);
    }

    /**
     * Génère un numéro d'acte de décès
     * @return Numéro d'acte de décès (ex: D2025001)
     */
    public static String genererNumeroDeces() {
        return genererNumero(TypeActe.DECES);
    }

    /**
     * Génère un numéro d'acte avec une année spécifique
     * @param typeActe Type d'acte
     * @param annee Année pour le numéro
     * @return Numéro d'acte formaté
     */
    public static String genererNumeroAvecAnnee(TypeActe typeActe, int annee) {
        if (typeActe == null) {
            throw new IllegalArgumentException("Le type d'acte ne peut pas être null");
        }

        if (annee < 1900 || annee > 2100) {
            throw new IllegalArgumentException("Année invalide: " + annee);
        }

        String prefix;
        int numero;

        switch (typeActe) {
            case NAISSANCE:
                prefix = PREFIX_NAISSANCE;
                numero = compteurNaissance.getAndIncrement();
                break;

            case MARIAGE:
                prefix = PREFIX_MARIAGE;
                numero = compteurMariage.getAndIncrement();
                break;

            case DECES:
                prefix = PREFIX_DECES;
                numero = compteurDeces.getAndIncrement();
                break;

            default:
                throw new IllegalArgumentException("Type d'acte non supporté: " + typeActe);
        }

        return String.format("%s%d%03d", prefix, annee, numero);
    }

    /**
     * Génère un numéro de registre
     * @param typeActe Type d'acte
     * @return Numéro de registre (ex: REG-N-2025-001)
     */
    public static String genererNumeroRegistre(TypeActe typeActe) {
        if (typeActe == null) {
            throw new IllegalArgumentException("Le type d'acte ne peut pas être null");
        }

        String annee = String.valueOf(LocalDate.now().getYear());
        String typeCode = typeActe.name().charAt(0) + ""; // Première lettre du type

        int numero;
        switch (typeActe) {
            case NAISSANCE:
                numero = compteurNaissance.get();
                break;
            case MARIAGE:
                numero = compteurMariage.get();
                break;
            case DECES:
                numero = compteurDeces.get();
                break;
            default:
                numero = 1;
        }

        return String.format("REG-%s-%s-%03d", typeCode, annee, numero);
    }

    /**
     * Extrait le type d'acte à partir du numéro
     * @param numeroActe Numéro d'acte (ex: N2025001)
     * @return Type d'acte ou null si invalide
     */
    public static TypeActe extraireTypeActe(String numeroActe) {
        if (numeroActe == null || numeroActe.length() < 1) {
            return null;
        }

        char prefix = numeroActe.charAt(0);

        switch (prefix) {
            case 'N':
                return TypeActe.NAISSANCE;
            case 'M':
                return TypeActe.MARIAGE;
            case 'D':
                return TypeActe.DECES;
            default:
                return null;
        }
    }

    /**
     * Extrait l'année à partir du numéro d'acte
     * @param numeroActe Numéro d'acte (ex: N2025001)
     * @return Année ou -1 si invalide
     */
    public static int extraireAnnee(String numeroActe) {
        if (numeroActe == null || numeroActe.length() < 5) {
            return -1;
        }

        try {
            return Integer.parseInt(numeroActe.substring(1, 5));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Valide le format d'un numéro d'acte
     * @param numeroActe Numéro à valider
     * @return true si le format est valide
     */
    public static boolean isNumeroValide(String numeroActe) {
        if (numeroActe == null || numeroActe.length() != 8) {
            return false;
        }

        // Vérifier le préfixe
        char prefix = numeroActe.charAt(0);
        if (prefix != 'N' && prefix != 'M' && prefix != 'D') {
            return false;
        }

        // Vérifier que les caractères suivants sont numériques
        try {
            Integer.parseInt(numeroActe.substring(1));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Réinitialise les compteurs (pour les tests uniquement)
     */
    public static void reinitialiserCompteurs() {
        compteurNaissance.set(1);
        compteurMariage.set(1);
        compteurDeces.set(1);
    }

    /**
     * Définit la valeur du compteur pour un type d'acte
     * @param typeActe Type d'acte
     * @param valeur Nouvelle valeur du compteur
     */
    public static void definirCompteur(TypeActe typeActe, int valeur) {
        if (valeur < 1) {
            throw new IllegalArgumentException("La valeur du compteur doit être positive");
        }

        switch (typeActe) {
            case NAISSANCE:
                compteurNaissance.set(valeur);
                break;
            case MARIAGE:
                compteurMariage.set(valeur);
                break;
            case DECES:
                compteurDeces.set(valeur);
                break;
        }
    }

    /**
     * Obtient la valeur actuelle du compteur pour un type d'acte
     * @param typeActe Type d'acte
     * @return Valeur actuelle du compteur
     */
    public static int obtenirCompteur(TypeActe typeActe) {
        switch (typeActe) {
            case NAISSANCE:
                return compteurNaissance.get();
            case MARIAGE:
                return compteurMariage.get();
            case DECES:
                return compteurDeces.get();
            default:
                return -1;
        }
    }
}