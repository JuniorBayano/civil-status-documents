package main.java.com.etatcivil.view.console;

import main.java.com.etatcivil.service.AuthenticationService;
import main.java.com.etatcivil.view.console.MenuAgent;
import main.java.com.etatcivil.view.console.MenuChef;
import main.java.com.etatcivil.view.console.MenuInvite;
import java.util.Scanner;

/**
 * Menu principal de l'application
 */
public class MenuPrincipal {

    private static final Scanner scanner = new Scanner(System.in);
    private static AuthenticationService authService = new AuthenticationService();

    /**
     * Affiche le menu principal et gère la navigation
     */
    public static void afficher() {
        afficherBanniere();

        boolean continuer = true;

        while (continuer) {
            try {
                if (!authService.isUserConnected()) {
                    continuer = afficherMenuConnexion();
                } else {
                    continuer = redirigerSelonRole();
                }
            } catch (Exception e) {
                System.err.println("❌ Erreur inattendue: " + e.getMessage());
                System.out.println("Appuyez sur Entrée pour continuer...");
                scanner.nextLine();
            }
        }

        System.out.println("\n👋 Au revoir et à bientôt !");
        scanner.close();
    }

    /**
     * Affiche la bannière de l'application
     */
    private static void afficherBanniere() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("        🏛️  SYSTÈME DE GESTION DES ACTES D'ÉTAT CIVIL  🏛️");
        System.out.println("                     République du Cameroun");
        System.out.println("=".repeat(70));
    }

    /**
     * Affiche le menu de connexion
     * @return true pour continuer, false pour quitter
     */
    private static boolean afficherMenuConnexion() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("                🔐 CONNEXION");
        System.out.println("─".repeat(50));
        System.out.println("1. Se connecter");
        System.out.println("2. Consulter un acte (accès public)");
        System.out.println("0. Quitter l'application");
        System.out.println("─".repeat(50));

        System.out.print("Votre choix: ");
        String choix = scanner.nextLine().trim();

        switch (choix) {
            case "1":
                return gererConnexion();

            case "2":
                MenuInvite.afficher(scanner);
                return true;

            case "0":
                return false;

            default:
                System.out.println("❌ Choix invalide. Veuillez saisir 1, 2 ou 0.");
                return true;
        }
    }

    /**
     * Gère le processus de connexion
     * @return true pour continuer
     */
    private static boolean gererConnexion() {
        System.out.println("\n🔑 AUTHENTIFICATION");
        System.out.println("─".repeat(30));

        int tentatives = 0;
        final int MAX_TENTATIVES = 3;

        while (tentatives < MAX_TENTATIVES) {
            System.out.print("Login: ");
            String login = scanner.nextLine().trim();

            if (login.isEmpty()) {
                System.out.println("❌ Le login ne peut pas être vide");
                continue;
            }

            System.out.print("Mot de passe: ");
            String password = scanner.nextLine().trim();

            if (password.isEmpty()) {
                System.out.println("❌ Le mot de passe ne peut pas être vide");
                continue;
            }

            if (authService.authenticate(login, password)) {
                System.out.println("\n🎉 Connexion réussie !");
                authService.afficherInfosUtilisateur();
                System.out.println("\nAppuyez sur Entrée pour continuer...");
                scanner.nextLine();
                return true;
            } else {
                tentatives++;
                int restantes = MAX_TENTATIVES - tentatives;

                if (restantes > 0) {
                    System.out.println("❌ Échec de connexion. " + restantes + " tentative(s) restante(s).");
                } else {
                    System.out.println("❌ Trop de tentatives échouées. Retour au menu principal.");
                    System.out.println("Appuyez sur Entrée pour continuer...");
                    scanner.nextLine();
                }
            }
        }

        return true;
    }

    /**
     * Redirige vers le bon menu selon le rôle de l'utilisateur
     * @return true pour continuer, false pour quitter
     */
    private static boolean redirigerSelonRole() {
        if (authService.isChef()) {
            return MenuChef.afficher(scanner, authService);
        } else if (authService.isAgent()) {
            return MenuAgent.afficher(scanner, authService);
        } else {
            // Ne devrait pas arriver, mais par sécurité
            System.out.println("❌ Rôle utilisateur non reconnu");
            authService.logout();
            return true;
        }
    }

    /**
     * Affiche un message de bienvenue personnalisé
     */
    public static void afficherBienvenue() {
        System.out.println("\n🌟 Bienvenue dans le système d'état civil !");
        System.out.println("Cette application vous permet de:");
        System.out.println("• Enregistrer les actes de naissance, mariage et décès");
        System.out.println("• Générer des extraits d'actes officiels");
        System.out.println("• Effectuer des recherches rapides");
        System.out.println("• Consulter les statistiques");
        System.out.println("\nPour commencer, veuillez vous connecter avec vos identifiants.");
    }

    /**
     * Méthode utilitaire pour attendre une saisie utilisateur
     */
    public static void attendreEntree() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    /**
     * Méthode utilitaire pour confirmer une action
     * @param message Message de confirmation
     * @return true si confirmé
     */
    public static boolean confirmer(String message) {
        System.out.print(message + " (o/n): ");
        String reponse = scanner.nextLine().trim().toLowerCase();
        return reponse.equals("o") || reponse.equals("oui") || reponse.equals("y") || reponse.equals("yes");
    }

    /**
     * Méthode utilitaire pour effacer l'écran (simulation)
     */
    public static void effacerEcran() {
        // Simulation d'effacement d'écran avec des lignes vides
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
        afficherBanniere();
    }
}