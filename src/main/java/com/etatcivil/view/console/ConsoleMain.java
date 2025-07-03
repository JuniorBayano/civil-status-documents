package main.java.com.etatcivil.view.console;

import main.java.com.etatcivil.dao.impl.DatabaseConnection;
import main.java.com.etatcivil.model.entities.ActeDeces;
import main.java.com.etatcivil.model.entities.ActeMariage;
import main.java.com.etatcivil.model.entities.ActeNaissance;
import main.java.com.etatcivil.model.entities.Utilisateur;
import main.java.com.etatcivil.model.enums.RoleUtilisateur;
import main.java.com.etatcivil.model.enums.Sexe;
import main.java.com.etatcivil.model.enums.TypeActe;
import main.java.com.etatcivil.util.DateUtil;
import main.java.com.etatcivil.util.NumeroGenerator;
import main.java.com.etatcivil.util.ValidationUtil;

import java.time.LocalDate;

/**
 * Classe principale pour lancer l'application en console
 */
public class ConsoleMain {

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("    SYSTÈME DE GESTION DES ACTES D'ÉTAT CIVIL");
        System.out.println("=".repeat(60));

        // Test des enums créés
        testEnums();

        // Test de la connexion à la base de données
        testDatabase();

        // Test des entités
        testEntites();

        // Test des utilitaires
        testUtilitaires();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("           APPLICATION DÉMARRÉE AVEC SUCCÈS");
        System.out.println("=".repeat(60));

        // TODO: Lancer le menu principal quand il sera créé
        // MenuPrincipal.afficher();
    }

    /**
     * Méthode pour tester les enums créés
     */
    private static void testEnums() {
        System.out.println("\n--- TEST DES ENUMS ---");

        // Test RoleUtilisateur
        System.out.println("\nRôles disponibles:");
        for (RoleUtilisateur role : RoleUtilisateur.values()) {
            System.out.println("- " + role.getLibelle() + ": " + role.getDescription());
        }

        // Test TypeActe
        System.out.println("\nTypes d'actes:");
        for (TypeActe type : TypeActe.values()) {
            System.out.println("- " + type.getLibelle() + ": " + type.getDescription());
        }

        // Test Sexe
        System.out.println("\nSexes disponibles:");
        for (Sexe sexe : Sexe.values()) {
            System.out.println("- " + sexe.getCode() + ": " + sexe.getLibelle());
        }
    }

    /**
     * Méthode pour tester les entités créées
     */
    private static void testEntites() {
        System.out.println("\n--- TEST DES ENTITÉS ---");

        // Test Utilisateur
        Utilisateur chef = new Utilisateur("ADMIN", "Chef", RoleUtilisateur.CHEF, "chef", "admin123");
        Utilisateur agent = new Utilisateur("DUPONT", "Jean", RoleUtilisateur.AGENT, "agent1", "agent123");

        System.out.println("\nUtilisateurs créés:");
        System.out.println("- " + chef.getNomComplet() + " (" + chef.getRole() + ") - Chef: " + chef.isChef());
        System.out.println("- " + agent.getNomComplet() + " (" + agent.getRole() + ") - Agent: " + agent.isAgent());

        // Test des actes
        System.out.println("\nActes créés:");

        // Acte de naissance
        ActeNaissance naissance = new ActeNaissance("N2025001", LocalDate.now(), "Mairie de Yaoundé",
                1, "NGUEMA", "Paul", Sexe.MASCULIN,
                LocalDate.of(2025, 1, 15), "Yaoundé",
                "NGUEMA", "Pierre", "MBALLA", "Marie");
        System.out.println("- " + naissance.getInformationsSpecifiques());
        System.out.println("  Valide: " + naissance.validerDonnees());

        // Acte de mariage
        ActeMariage mariage = new ActeMariage("M2025001", LocalDate.now(), "Mairie de Yaoundé",
                1, "BIYA", "Jean", "FOUDA", "Claire",
                LocalDate.of(2025, 6, 20), "Yaoundé");
        System.out.println("- " + mariage.getInformationsSpecifiques());
        System.out.println("  Valide: " + mariage.validerDonnees());

        // Acte de décès
        ActeDeces deces = new ActeDeces("D2025001", LocalDate.now(), "Mairie de Yaoundé",
                1, "MVONDO", "Joseph", LocalDate.of(2024, 12, 30),
                "Hôpital Central Yaoundé", "MVONDO", "Paul", "Fils");
        System.out.println("- " + deces.getInformationsSpecifiques());
        System.out.println("  Valide: " + deces.validerDonnees());

        System.out.println("\n✅ Entités testées avec succès");
    }

    /**
     * Méthode pour tester les utilitaires
     */
    private static void testUtilitaires() {
        System.out.println("\n--- TEST DES UTILITAIRES ---");

        // Test DateUtil
        System.out.println("\nDateUtil:");
        System.out.println("- Date actuelle: " + DateUtil.getDateActuelle());
        System.out.println("- DateTime actuelle: " + DateUtil.getDateTimeActuelle());
        LocalDate dateTest = LocalDate.of(1990, 5, 15);
        System.out.println("- Âge pour né le 15/05/1990: " + DateUtil.calculerAge(dateTest) + " ans");
        System.out.println("- Date valide pour acte: " + DateUtil.isDateValideActe(dateTest));

        // Test ValidationUtil
        System.out.println("\nValidationUtil:");
        System.out.println("- Nom 'DUPONT' valide: " + ValidationUtil.isNomValide("DUPONT"));
        System.out.println("- Login 'agent123' valide: " + ValidationUtil.isLoginValide("agent123"));
        System.out.println("- Nom nettoyé 'jean-claude': " + ValidationUtil.nettoyerNom("jean-claude"));
        System.out.println("- Numéro 'N2025001' valide: " + ValidationUtil.isNumeroActeValide("N2025001"));

        // Test NumeroGenerator
        System.out.println("\nNumeroGenerator:");
        String numNaissance = NumeroGenerator.genererNumeroNaissance();
        String numMariage = NumeroGenerator.genererNumeroMariage();
        String numDeces = NumeroGenerator.genererNumeroDeces();
        System.out.println("- Numéro naissance généré: " + numNaissance);
        System.out.println("- Numéro mariage généré: " + numMariage);
        System.out.println("- Numéro décès généré: " + numDeces);
        System.out.println("- Type extrait de " + numNaissance + ": " + NumeroGenerator.extraireTypeActe(numNaissance));
        System.out.println("- Année extraite de " + numNaissance + ": " + NumeroGenerator.extraireAnnee(numNaissance));

        System.out.println("\n✅ Utilitaires testés avec succès");
    }

    /**
     * Méthode pour tester la connexion à la base de données
     */
    private static void testDatabase() {
        System.out.println("\n--- TEST DE LA BASE DE DONNÉES ---");

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        if (dbConnection.testConnection()) {
            System.out.println("✅ Base de données opérationnelle");
        } else {
            System.out.println("❌ Problème de connexion à la base");
            System.out.println("   Vérifiez que MySQL est démarré");
            System.out.println("   Exécutez le script create_tables.sql d'abord");
        }
    }
}