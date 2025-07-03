package main.java.com.etatcivil.view.console;

import main.java.com.etatcivil.model.enums.RoleUtilisateur;
import main.java.com.etatcivil.model.enums.TypeActe;
import main.java.com.etatcivil.model.enums.Sexe;
import main.java.com.etatcivil.dao.impl.DatabaseConnection;
import main.java.com.etatcivil.model.entities.Utilisateur;
import main.java.com.etatcivil.model.entities.ActeNaissance;
import main.java.com.etatcivil.model.entities.ActeMariage;
import main.java.com.etatcivil.model.entities.ActeDeces;
import main.java.com.etatcivil.util.DateUtil;
import main.java.com.etatcivil.util.ValidationUtil;
import main.java.com.etatcivil.util.NumeroGenerator;
import main.java.com.etatcivil.dao.impl.UtilisateurDAOImpl;
import main.java.com.etatcivil.dao.impl.ActeDAOImpl;
import main.java.com.etatcivil.dao.interfaces.DAOException;
import main.java.com.etatcivil.service.AuthenticationService;
import main.java.com.etatcivil.service.ActeService;
import main.java.com.etatcivil.view.console.MenuPrincipal;
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

        // Test de la persistance (sauvegarde en base)
        testPersistance();

        // Test des services
        testServices();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("           APPLICATION DÉMARRÉE AVEC SUCCÈS");
        System.out.println("=".repeat(60));

        // TODO: Lancer le menu principal quand il sera créé
        MenuPrincipal.afficherBienvenue();
        MenuPrincipal.afficher();
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
     * Méthode pour tester la persistance des données en base
     */
    private static void testPersistance() {
        System.out.println("\n--- TEST DE PERSISTANCE EN BASE ---");

        try {
            // Test sauvegarde utilisateur
            UtilisateurDAOImpl utilisateurDAO = new UtilisateurDAOImpl();

            System.out.println("\n1. Test sauvegarde utilisateur:");
            // Utiliser un login unique avec timestamp
            String loginUnique = "user" + System.currentTimeMillis();
            Utilisateur nouveauUser = new Utilisateur("MARTIN", "Sophie", RoleUtilisateur.AGENT, loginUnique, "test123");
            Utilisateur userSauvegarde = utilisateurDAO.save(nouveauUser);
            System.out.println("✅ Utilisateur sauvegardé avec ID: " + userSauvegarde.getId());
            System.out.println("   " + userSauvegarde.getNomComplet() + " (" + userSauvegarde.getLogin() + ")");

            // Test récupération utilisateur
            System.out.println("\n2. Test récupération utilisateur:");
            var userRecupere = utilisateurDAO.findById(userSauvegarde.getId());
            if (userRecupere.isPresent()) {
                System.out.println("✅ Utilisateur récupéré: " + userRecupere.get().getNomComplet());
            } else {
                System.out.println("❌ Utilisateur non trouvé");
            }

            // Test authentification
            System.out.println("\n3. Test authentification:");
            var userAuth = utilisateurDAO.authenticate(loginUnique, "test123");
            if (userAuth.isPresent()) {
                System.out.println("✅ Authentification réussie pour: " + userAuth.get().getNomComplet());
            } else {
                System.out.println("❌ Authentification échouée");
            }

            // Test sauvegarde acte de naissance
            System.out.println("\n4. Test sauvegarde acte de naissance:");
            ActeDAOImpl acteDAO = new ActeDAOImpl();

            ActeNaissance acteNaissance = new ActeNaissance();
            // Générer un numéro unique avec timestamp
            String numeroUnique = "N" + LocalDate.now().getYear() +
                    String.format("%06d", System.currentTimeMillis() % 1000000);
            acteNaissance.setNumero(numeroUnique);
            acteNaissance.setDateEnregistrement(LocalDate.now());
            acteNaissance.setLieuEnregistrement("Mairie de Yaoundé");
            acteNaissance.setIdAgent(userSauvegarde.getId());
            acteNaissance.setNomEnfant("ONANA");
            acteNaissance.setPrenomEnfant("Michel");
            acteNaissance.setSexe(Sexe.MASCULIN);
            acteNaissance.setDateNaissance(LocalDate.of(2025, 7, 1));
            acteNaissance.setLieuNaissance("Yaoundé");
            acteNaissance.setNomPere("ONANA");
            acteNaissance.setPrenomPere("Paul");
            acteNaissance.setNomMere("BELLA");
            acteNaissance.setPrenomMere("Marie");

            ActeNaissance acteSauvegarde = acteDAO.saveNaissance(acteNaissance);
            System.out.println("✅ Acte de naissance sauvegardé avec ID: " + acteSauvegarde.getId());
            System.out.println("   Numéro: " + acteSauvegarde.getNumero());
            System.out.println("   Enfant: " + acteSauvegarde.getNomCompletEnfant());

            // Test comptage des données
            System.out.println("\n5. Statistiques de la base:");
            long nbUtilisateurs = utilisateurDAO.count();
            long nbActes = acteDAO.count();
            System.out.println("✅ Nombre d'utilisateurs en base: " + nbUtilisateurs);
            System.out.println("✅ Nombre d'actes en base: " + nbActes);

            System.out.println("\n✅ PERSISTANCE TESTÉE AVEC SUCCÈS !");
            System.out.println("   → Vérifiez votre base MySQL, les données sont sauvegardées !");

        } catch (DAOException e) {
            System.err.println("❌ Erreur lors du test de persistance: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Erreur inattendue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour tester les services
     */
    private static void testServices() {
        System.out.println("\n--- TEST DES SERVICES ---");

        try {
            // Test du service d'authentification
            System.out.println("\n1. Test service d'authentification:");
            AuthenticationService authService = new AuthenticationService();

            // Test connexion avec utilisateur par défaut (depuis le script SQL)
            boolean loginSuccess = authService.authenticate("chef", "admin123");
            if (loginSuccess) {
                System.out.println("✅ Connexion réussie");
                authService.afficherInfosUtilisateur();
            } else {
                System.out.println("❌ Connexion échouée");
            }

            // Test du service d'actes
            if (authService.isUserConnected()) {
                System.out.println("\n2. Test service d'actes:");
                ActeService acteService = new ActeService(authService);

                // Créer un acte de naissance via le service
                ActeNaissance nouvelActe = acteService.creerActeNaissance(
                        "ESSOMBA", "Alice", Sexe.FEMININ,
                        LocalDate.of(2025, 7, 3), "Douala",
                        "ESSOMBA", "Robert", "NJOYA", "Catherine",
                        "Mairie de Douala"
                );

                if (nouvelActe != null) {
                    System.out.println("✅ Acte créé via le service");

                    // Test génération d'extrait
                    System.out.println("\n3. Test génération d'extrait:");
                    String extrait = acteService.genererExtrait(nouvelActe.getNumero());
                    System.out.println("📄 EXTRAIT GÉNÉRÉ:");
                    System.out.println(extrait);
                } else {
                    System.out.println("❌ Échec de création d'acte via le service");
                }
            }

            // Test déconnexion
            System.out.println("\n4. Test déconnexion:");
            authService.logout();

            System.out.println("\n✅ SERVICES TESTÉS AVEC SUCCÈS !");

        } catch (Exception e) {
            System.err.println("❌ Erreur lors du test des services: " + e.getMessage());
            e.printStackTrace();
        }
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