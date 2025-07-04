package main.java.com.etatcivil.view.console;

import main.java.com.etatcivil.service.AuthenticationService;
import main.java.com.etatcivil.service.ActeService;
import main.java.com.etatcivil.model.entities.Utilisateur;
import main.java.com.etatcivil.model.entities.Acte;
import main.java.com.etatcivil.model.enums.RoleUtilisateur;
import main.java.com.etatcivil.view.console.MenuPrincipal;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * Menu pour le chef d'état civil
 */
public class MenuChef {

    /**
     * Affiche le menu chef
     * @param scanner Scanner pour les saisies
     * @param authService Service d'authentification
     * @return true pour continuer, false pour se déconnecter
     */
    public static boolean afficher(Scanner scanner, AuthenticationService authService) {
        ActeService acteService = new ActeService(authService);

        while (true) {
            afficherMenuChef(authService);

            System.out.print("Votre choix: ");
            String choix = scanner.nextLine().trim();

            switch (choix) {
                case "1":
                    // Toutes les fonctions d'agent sont disponibles
                    return MenuAgent.afficher(scanner, authService);

                case "2":
                    System.out.println("\n✍️  SIGNER/VALIDER UN ACTE");
                    System.out.print("Entrez le numéro de l'acte: ");
                    String numeroActe = scanner.nextLine();

                    Optional<Acte> acteOpt = acteService.rechercherActeParNumero(numeroActe);
                    if (acteOpt.isPresent()) {
                        Acte acte = acteOpt.get();
                        System.out.println("\nDétails de l'acte:");
                        System.out.println("Numéro: " + acte.getNumero());
                        System.out.println("Type: " + acte.getType());
                        System.out.println("Statut: " + acte.getStatut());

                        System.out.println("1. Signer l'acte");
                        System.out.println("0. Annuler");
                        System.out.print("Votre choix: ");

                        int choixValidation = scanner.nextInt();
                        scanner.nextLine(); // consommer la nouvelle ligne

                        switch (choixValidation) {
                            case 1:
                                acteService.validerActe(acte.getId());
                                break;
                            case 2:
                                acteService.signerActe(acte.getId());
                                break;
                            case 0:
                                System.out.println("Opération annulée");
                                break;
                            default:
                                System.out.println("❌ Choix invalide");
                        }
                    } else {
                        System.out.println("❌ Aucun acte trouvé avec ce numéro");
                    }
                    break;

                case "3":
                    consulterTousLesActes(scanner, acteService);
                    break;

                case "4":
                    creerUtilisateur(scanner, authService);
                    break;

                case "5":
                    gererUtilisateurs(scanner, authService);
                    break;

                case "6":
                    afficherStatistiques(scanner, acteService);
                    break;

                case "7":
                    genererRapport(scanner, acteService);
                    break;

                case "8":
                    changerMotDePasse(scanner, authService);
                    break;

                case "0":
                    if (MenuPrincipal.confirmer("Voulez-vous vraiment vous déconnecter ?")) {
                        authService.logout();
                        return true;
                    }
                    break;

                default:
                    System.out.println("❌ Choix invalide. Veuillez saisir un nombre entre 0 et 8.");
                    MenuPrincipal.attendreEntree();
            }
        }
    }

    /**
     * Affiche le menu des options pour le chef
     */
    private static void afficherMenuChef(AuthenticationService authService) {
        MenuPrincipal.effacerEcran();
        System.out.println("👑 Connecté: " + authService.getUtilisateurConnecte().getNomComplet() + " (Chef d'État Civil)");
        System.out.println("\n" + "─".repeat(70));
        System.out.println("                    🏛️  MENU CHEF D'ÉTAT CIVIL");
        System.out.println("─".repeat(70));
        System.out.println("1. 📋 Gestion des actes (menu agent)");
        System.out.println("2. ✍️  Signer un acte");
        System.out.println("3. 📚 Consulter tous les actes");
        System.out.println("4. 👤 Créer un utilisateur");
        System.out.println("5. 👥 Gérer les utilisateurs");
        System.out.println("6. 📊 Statistiques");
        System.out.println("7. 📈 Générer un rapport");
        System.out.println("8. 🔑 Changer mon mot de passe");
        System.out.println("0. 🚪 Se déconnecter");
        System.out.println("─".repeat(70));
    }

    /**
     * Signe un acte
     */
    private static void signerActe(Scanner scanner, ActeService acteService) {
        System.out.println("\n✍️  SIGNATURE D'ACTE");
        System.out.print("Numéro d'acte à signer: ");
        String numero = scanner.nextLine().trim();

        var acte = acteService.rechercherActeParNumero(numero);
        if (acte.isPresent()) {
            System.out.println("\n📋 ACTE À SIGNER:");
            System.out.println(acte.get().getInformationsSpecifiques());
            System.out.println("Statut actuel: " + acte.get().getStatut());

            if (acte.get().isSigne()) {
                System.out.println("⚠️  Cet acte est déjà signé");
            } else if (MenuPrincipal.confirmer("\nConfirmer la signature de cet acte ?")) {
                boolean success = acteService.signerActe(acte.get().getId());
                if (success) {
                    System.out.println("✅ Acte signé avec succès");
                }
            }
        } else {
            System.out.println("❌ Acte non trouvé");
        }

        MenuPrincipal.attendreEntree();
    }

    /**
     * Consulte tous les actes
     */
    private static void consulterTousLesActes(Scanner scanner, ActeService acteService) {
        System.out.println("\n📚 TOUS LES ACTES");

        List<Acte> actes = acteService.listerActes();

        if (actes.isEmpty()) {
            System.out.println("Aucun acte enregistré");
        } else {
            System.out.println("Nombre total d'actes: " + actes.size());
            System.out.println("\n" + "─".repeat(80));

            for (Acte acte : actes) {
                System.out.printf("%-12s | %-8s | %s%n",
                        acte.getNumero(),
                        acte.getStatut(),
                        acte.getInformationsSpecifiques());
            }
        }

        MenuPrincipal.attendreEntree();
    }

    /**
     * Crée un nouvel utilisateur
     */
    private static void creerUtilisateur(Scanner scanner, AuthenticationService authService) {
        System.out.println("\n👤 CRÉATION D'UTILISATEUR");

        System.out.print("Nom: ");
        String nom = scanner.nextLine().trim();

        System.out.print("Prénom: ");
        String prenom = scanner.nextLine().trim();

        System.out.print("Login: ");
        String login = scanner.nextLine().trim();

        System.out.print("Mot de passe: ");
        String password = scanner.nextLine().trim();

        RoleUtilisateur role = null;
        while (role == null) {
            System.out.println("\nRôles disponibles:");
            System.out.println("1. Agent d'état civil");
            System.out.println("2. Chef d'état civil");
            System.out.println("3. Utilisateur invité");
            System.out.print("Choisir le rôle (1-3): ");

            String choixRole = scanner.nextLine().trim();
            switch (choixRole) {
                case "1":
                    role = RoleUtilisateur.AGENT;
                    break;
                case "2":
                    role = RoleUtilisateur.CHEF;
                    break;
                case "3":
                    role = RoleUtilisateur.INVITE;
                    break;
                default:
                    System.out.println("❌ Choix invalide");
            }
        }

        System.out.println("\n📋 RÉCAPITULATIF:");
        System.out.println("Nom: " + prenom + " " + nom);
        System.out.println("Login: " + login);
        System.out.println("Rôle: " + role.getLibelle());

        if (MenuPrincipal.confirmer("\nConfirmer la création de cet utilisateur ?")) {
            Utilisateur nouvelUtilisateur = authService.creerUtilisateur(nom, prenom, role, login, password);

            if (nouvelUtilisateur != null) {
                System.out.println("✅ Utilisateur créé avec succès !");
                System.out.println("ID: " + nouvelUtilisateur.getId());
            }
        }

        MenuPrincipal.attendreEntree();
    }

    /**
     * Gère les utilisateurs existants
     */
    private static void gererUtilisateurs(Scanner scanner, AuthenticationService authService) {
        System.out.println("\n👥 GESTION DES UTILISATEURS");
        System.out.println("Cette fonctionnalité sera implémentée dans une version future.");
        System.out.println("Fonctionnalités prévues:");
        System.out.println("• Lister tous les utilisateurs");
        System.out.println("• Activer/désactiver un utilisateur");
        System.out.println("• Réinitialiser un mot de passe");
        System.out.println("• Modifier les rôles");

        MenuPrincipal.attendreEntree();
    }



    private static void afficherStatistiques(Scanner scanner, ActeService acteService) {
        System.out.println("\n📊 STATISTIQUES D'ACTES");

        System.out.print("Année (ex: 2024): ");
        int annee = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Mois (1-12 ou 0 pour toute l'année): ");
        int mois = Integer.parseInt(scanner.nextLine().trim());

        Optional<Integer> moisOpt = (mois == 0) ? Optional.empty() : Optional.of(mois);

        Map<String, Long> stats = acteService.getStatistiquesParTypeEtPeriode(annee, moisOpt);

        if (stats.isEmpty()) {
            System.out.println("❌ Aucune donnée pour la période spécifiée.");
        } else {
            System.out.println("\n📈 Résultat :");
            stats.forEach((type, total) -> {
                System.out.printf("• %-10s : %d actes%n", type, total);
            });
        }

        MenuPrincipal.attendreEntree();
    }


    /**
     * Génère un rapport
     */
    private static void genererRapport(Scanner scanner, ActeService acteService) {
        System.out.println("\n📈 GÉNÉRATION DE RAPPORT");
        System.out.println("Cette fonctionnalité sera implémentée dans une version future.");
        System.out.println("Rapports prévus:");
        System.out.println("• Rapport mensuel d'activité");
        System.out.println("• Rapport annuel par type d'acte");
        System.out.println("• Rapport de performance par agent");
        System.out.println("• Export PDF/Excel");

        MenuPrincipal.attendreEntree();
    }

    /**
     * Change le mot de passe
     */
    private static void changerMotDePasse(Scanner scanner, AuthenticationService authService) {
        System.out.println("\n🔑 CHANGEMENT DE MOT DE PASSE");

        System.out.print("Ancien mot de passe: ");
        String ancien = scanner.nextLine().trim();

        System.out.print("Nouveau mot de passe: ");
        String nouveau = scanner.nextLine().trim();

        System.out.print("Confirmer le nouveau mot de passe: ");
        String confirmation = scanner.nextLine().trim();

        if (!nouveau.equals(confirmation)) {
            System.out.println("❌ Les mots de passe ne correspondent pas");
        } else {
            authService.changerMotDePasse(ancien, nouveau);
        }

        MenuPrincipal.attendreEntree();
    }
}