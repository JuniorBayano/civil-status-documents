package main.java.com.etatcivil.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Classe utilitaire pour la gestion des dates dans le système d'état civil
 */
public class DateUtil {

    // Formats de dates couramment utilisés
    public static final DateTimeFormatter FORMATTER_FR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATTER_ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMATTER_LONG_FR = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    public static final DateTimeFormatter FORMATTER_DATETIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Convertit une chaîne de caractères en LocalDate selon le format français
     * @param dateStr La date sous forme de chaîne (dd/MM/yyyy)
     * @return LocalDate ou null si erreur
     */
    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }

        try {
            return LocalDate.parse(dateStr.trim(), FORMATTER_FR);
        } catch (DateTimeParseException e) {
            System.err.println("Erreur de format de date: " + dateStr + ". Format attendu: dd/MM/yyyy");
            return null;
        }
    }

    /**
     * Convertit une chaîne de caractères en LocalDate selon le format ISO
     * @param dateStr La date sous forme de chaîne (yyyy-MM-dd)
     * @return LocalDate ou null si erreur
     */
    public static LocalDate parseDateISO(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }

        try {
            return LocalDate.parse(dateStr.trim(), FORMATTER_ISO);
        } catch (DateTimeParseException e) {
            System.err.println("Erreur de format de date ISO: " + dateStr + ". Format attendu: yyyy-MM-dd");
            return null;
        }
    }

    /**
     * Formate une LocalDate en chaîne française
     * @param date La date à formater
     * @return Chaîne formatée (dd/MM/yyyy) ou "Non définie" si null
     */
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(FORMATTER_FR) : "Non définie";
    }

    /**
     * Formate une LocalDate en chaîne longue française
     * @param date La date à formater
     * @return Chaîne formatée (ex: "15 janvier 2025") ou "Non définie" si null
     */
    public static String formatDateLong(LocalDate date) {
        return date != null ? date.format(FORMATTER_LONG_FR) : "Non définie";
    }

    /**
     * Formate une LocalDateTime en chaîne française complète
     * @param dateTime La date-heure à formater
     * @return Chaîne formatée (dd/MM/yyyy HH:mm:ss) ou "Non définie" si null
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(FORMATTER_DATETIME) : "Non définie";
    }

    /**
     * Calcule l'âge à partir d'une date de naissance
     * @param dateNaissance Date de naissance
     * @return Âge en années, ou -1 si date invalide
     */
    public static int calculerAge(LocalDate dateNaissance) {
        if (dateNaissance == null || dateNaissance.isAfter(LocalDate.now())) {
            return -1;
        }
        return (int) ChronoUnit.YEARS.between(dateNaissance, LocalDate.now());
    }

    /**
     * Vérifie si une date est valide pour un acte d'état civil
     * @param date Date à vérifier
     * @return true si valide (pas dans le futur et pas trop ancienne)
     */
    public static boolean isDateValideActe(LocalDate date) {
        if (date == null) {
            return false;
        }

        LocalDate aujourdhui = LocalDate.now();
        LocalDate dateMinimale = aujourdhui.minusYears(150); // Limite de 150 ans dans le passé

        return !date.isAfter(aujourdhui) && !date.isBefore(dateMinimale);
    }

    /**
     * Vérifie si une date de naissance est valide
     * @param dateNaissance Date de naissance à vérifier
     * @return true si valide
     */
    public static boolean isDateNaissanceValide(LocalDate dateNaissance) {
        if (dateNaissance == null) {
            return false;
        }

        LocalDate aujourdhui = LocalDate.now();
        LocalDate dateMinimale = aujourdhui.minusYears(150);

        // Pas dans le futur, pas plus de 150 ans
        return !dateNaissance.isAfter(aujourdhui) && !dateNaissance.isBefore(dateMinimale);
    }

    /**
     * Vérifie si une date de mariage est valide
     * @param dateMariage Date de mariage à vérifier
     * @return true si valide
     */
    public static boolean isDateMariageValide(LocalDate dateMariage) {
        if (dateMariage == null) {
            return false;
        }

        LocalDate aujourdhui = LocalDate.now();
        LocalDate dateMinimale = aujourdhui.minusYears(100); // Limite de 100 ans pour les mariages

        return !dateMariage.isAfter(aujourdhui) && !dateMariage.isBefore(dateMinimale);
    }

    /**
     * Vérifie si une date de décès est valide
     * @param dateDeces Date de décès à vérifier
     * @return true si valide
     */
    public static boolean isDateDecesValide(LocalDate dateDeces) {
        if (dateDeces == null) {
            return false;
        }

        LocalDate aujourdhui = LocalDate.now();
        LocalDate dateMinimale = aujourdhui.minusYears(150);

        return !dateDeces.isAfter(aujourdhui) && !dateDeces.isBefore(dateMinimale);
    }

    /**
     * Calcule le nombre de jours entre deux dates
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @return Nombre de jours, ou -1 si erreur
     */
    public static long calculerJoursEntre(LocalDate dateDebut, LocalDate dateFin) {
        if (dateDebut == null || dateFin == null) {
            return -1;
        }
        return ChronoUnit.DAYS.between(dateDebut, dateFin);
    }

    /**
     * Retourne la date actuelle formatée
     * @return Date actuelle au format dd/MM/yyyy
     */
    public static String getDateActuelle() {
        return LocalDate.now().format(FORMATTER_FR);
    }

    /**
     * Retourne la date-heure actuelle formatée
     * @return Date-heure actuelle au format dd/MM/yyyy HH:mm:ss
     */
    public static String getDateTimeActuelle() {
        return LocalDateTime.now().format(FORMATTER_DATETIME);
    }

    /**
     * Vérifie si une année est bissextile
     * @param annee Année à vérifier
     * @return true si bissextile
     */
    public static boolean isAnneeBissextile(int annee) {
        return LocalDate.of(annee, 1, 1).isLeapYear();
    }
}