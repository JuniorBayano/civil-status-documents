package main.java.com.etatcivil.view.console;

import main.java.com.etatcivil.service.AuthenticationService;
import main.java.com.etatcivil.service.ActeService;
import main.java.com.etatcivil.model.entities.*;
import main.java.com.etatcivil.model.enums.Sexe;
import main.java.com.etatcivil.util.DateUtil;
import main.java.com.etatcivil.util.ValidationUtil;
import main.java.com.etatcivil.view.console.MenuPrincipal;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Menu pour les agents d'état civil
 */
public class MenuAgent {

    /**
     * Affiche le menu agent
     * @param scanner Scanner pour les saisies
     * @param authService Service d'authentification
     * @return true pour continuer, false pour se déconnecter
     */
    public static boolean afficher(Scanner scanner, AuthenticationService authService) {
        ActeService acteService = new ActeService(authService);

        while (true) {
            afficherMenuAgent(authService);

            System.out.print("Votre choix: ");
            String choix = scanner.nextLine().trim();

            switch (choix) {
                case "1":
                    creerActeNaissance(scanner, acteService);
                    break;

                case "2":
                    creerActeMariage(scanner, acteService);
                    break;

                case "3":
                    creerActeDeces(scanner, acteService);
                    break;

                case "4":
                    rechercherActe(scanner, acteService);
                    break;

                case "5":
                    consulterMesActes(scanner, acteService);
                    break;

                case "6":
                    genererExtrait(scanner, acteService);
                    break;

                case "7":
                    changerMotDePasse(scanner, authService);
                    break;

                case "0":
                    if (MenuPrincipal.confirmer("Voulez-vous vraiment vous déconnecter ?")) {
                        authService.logout();
                        return true;
                    }
                    break;

                default:
                    System.out.println("❌ Choix invalide. Veuillez saisir un nombre entre 0 et 7.");
                    MenuPrincipal.attendreEntree();
            }
        }
    }

    /**
     * Affiche le menu des options pour l'agent
     */
    private static void afficherMenuAgent(AuthenticationService authService) {
        MenuPrincipal.effacerEcran();
        System.out.println("👤 Connecté: " + authService.getUtilisateurConnecte().getNomComplet() + " (Agent)");
        System.out.println("\n" + "─".repeat(60));
        System.out.println("                    📋 MENU AGENT D'ÉTAT CIVIL");
        System.out.println("─".repeat(60));
        System.out.println("1. 👶 Créer un acte de naissance");
        System.out.println("2. 💒 Créer un acte de mariage");
        System.out.println("3. ⚰️  Créer un acte de décès");
        System.out.println("4. 🔍 Rechercher un acte");
        System.out.println("5. 📄 Consulter mes actes");
        System.out.println("6. 📝 Générer un extrait d'acte");
        System.out.println("7. 🔑 Changer mon mot de passe");
        System.out.println("0. 🚪 Se déconnecter");
        System.out.println("─".repeat(60));
    }

    /**
     * Crée un acte de naissance
     */
    private static void creerActeNaissance(Scanner scanner, ActeService acteService) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           👶 CRÉATION ACTE DE NAISSANCE");
        System.out.println("=".repeat(50));

        try {
            // Informations sur l'enfant
            System.out.println("\n📝 INFORMATIONS SUR L'ENFANT:");
            System.out.print("Nom de l'enfant: ");
            String nomEnfant = scanner.nextLine().trim();

            System.out.print("Prénom de l'enfant: ");
            String prenomEnfant = scanner.nextLine().trim();

            Sexe sexe = null;
            while (sexe == null) {
                System.out.print("Sexe (M/F): ");
                String sexeStr = scanner.nextLine().trim().toUpperCase();
                try {
                    sexe = Sexe.fromCode(sexeStr);
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ Veuillez saisir M pour Masculin ou F pour Féminin");
                }
            }

            LocalDate dateNaissance = saisirDate(scanner, "Date de naissance (dd/MM/yyyy): ");

            System.out.print("Lieu de naissance: ");
            String lieuNaissance = scanner.nextLine().trim();

            // Informations sur les parents
            System.out.println("\n👨‍👩‍👧‍👦 INFORMATIONS SUR LES PARENTS:");
            System.out.print("Nom du père (optionnel): ");
            String nomPere = scanner.nextLine().trim();
            nomPere = nomPere.isEmpty() ? null : nomPere;

            System.out.print("Prénom du père (optionnel): ");
            String prenomPere = scanner.nextLine().trim();
            prenomPere = prenomPere.isEmpty() ? null : prenomPere;

            System.out.print("Nom de la mère: ");
            String nomMere = scanner.nextLine().trim();

            System.out.print("Prénom de la mère: ");
            String prenomMere = scanner.nextLine().trim();

            // Lieu d'enregistrement
            System.out.print("Lieu d'enregistrement: ");
            String lieuEnregistrement = scanner.nextLine().trim();

            // Confirmation avant création
            System.out.println("\n📋 RÉCAPITULATIF:");
            System.out.println("Enfant: " + prenomEnfant + " " + nomEnfant + " (" + sexe.getLibelle() + ")");
            System.out.println("Né(e) le: " + DateUtil.formatDate(dateNaissance) + " à " + lieuNaissance);
            System.out.println("Père: " + (nomPere != null ? prenomPere + " " + nomPere : "Non déclaré"));
            System.out.println("Mère: " + prenomMere + " " + nomMere);

            if (MenuPrincipal.confirmer("\nConfirmer la création de cet acte ?")) {
                ActeNaissance acte = acteService.creerActeNaissance(
                        nomEnfant, prenomEnfant, sexe, dateNaissance, lieuNaissance,
                        nomPere, prenomPere, nomMere, prenomMere, lieuEnregistrement
                );

                if (acte != null) {
                    System.out.println("\n🎉 Acte de naissance créé avec succès !");
                    System.out.println("Numéro d'acte: " + acte.getNumero());
                }
            } else {
                System.out.println("❌ Création annulée");
            }

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la création: " + e.getMessage());
        }

        MenuPrincipal.attendreEntree();
    }

    /**
     * Crée un acte de mariage
     */
    private static void creerActeMariage(Scanner scanner, ActeService acteService) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           💒 CRÉATION ACTE DE MARIAGE");
        System.out.println("=".repeat(50));

        try {
            // Informations sur l'époux
            System.out.println("\n🤵 INFORMATIONS SUR L'ÉPOUX:");
            System.out.print("Nom de l'époux: ");
            String nomEpoux = scanner.nextLine().trim();

            System.out.print("Prénom de l'époux: ");
            String prenomEpoux = scanner.nextLine().trim();

            // Informations sur l'épouse
            System.out.println("\n👰 INFORMATIONS SUR L'ÉPOUSE:");
            System.out.print("Nom de l'épouse: ");
            String nomEpouse = scanner.nextLine().trim();

            System.out.print("Prénom de l'épouse: ");
            String prenomEpouse = scanner.nextLine().trim();

            // Informations sur le mariage
            System.out.println("\n💒 INFORMATIONS SUR LE MARIAGE:");
            LocalDate dateMariage = saisirDate(scanner, "Date du mariage (dd/MM/yyyy): ");

            System.out.print("Lieu du mariage: ");
            String lieuMariage = scanner.nextLine().trim();

            System.out.print("Régime matrimonial (Entrée = Communauté de biens): ");
            String regime = scanner.nextLine().trim();
            regime = regime.isEmpty() ? "Communauté de biens" : regime;

            System.out.print("Lieu d'enregistrement: ");
            String lieuEnregistrement = scanner.nextLine().trim();

            // Confirmation
            System.out.println("\n📋 RÉCAPITULATIF:");
            System.out.println("Époux: " + prenomEpoux + " " + nomEpoux);
            System.out.println("Épouse: " + prenomEpouse + " " + nomEpouse);
            System.out.println("Date: " + DateUtil.formatDate(dateMariage) + " à " + lieuMariage);
            System.out.println("Régime: " + regime);

            if (MenuPrincipal.confirmer("\nConfirmer la création de cet acte ?")) {
                ActeMariage acte = acteService.creerActeMariage(
                        nomEpoux, prenomEpoux, nomEpouse, prenomEpouse,
                        dateMariage, lieuMariage, regime, lieuEnregistrement
                );

                if (acte != null) {
                    System.out.println("\n🎉 Acte de mariage créé avec succès !");
                    System.out.println("Numéro d'acte: " + acte.getNumero());
                }
            } else {
                System.out.println("❌ Création annulée");
            }

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la création: " + e.getMessage());
        }

        MenuPrincipal.attendreEntree();
    }

    /**
     * Crée un acte de décès
     */
    private static void creerActeDeces(Scanner scanner, ActeService acteService) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           ⚰️  CRÉATION ACTE DE DÉCÈS");
        System.out.println("=".repeat(50));

        try {
            // Informations sur le défunt
            System.out.println("\n💀 INFORMATIONS SUR LE DÉFUNT:");
            System.out.print("Nom du défunt: ");
            String nomDefunt = scanner.nextLine().trim();

            System.out.print("Prénom du défunt: ");
            String prenomDefunt = scanner.nextLine().trim();

            LocalDate dateDeces = saisirDate(scanner, "Date du décès (dd/MM/yyyy): ");

            System.out.print("Lieu du décès: ");
            String lieuDeces = scanner.nextLine().trim();

            System.out.print("Cause du décès (optionnel): ");
            String causeDeces = scanner.nextLine().trim();
            causeDeces = causeDeces.isEmpty() ? null : causeDeces;

            // Informations sur le déclarant
            System.out.println("\n👤 INFORMATIONS SUR LE DÉCLARANT:");
            System.out.print("Nom du déclarant: ");
            String declarantNom = scanner.nextLine().trim();

            System.out.print("Prénom du déclarant: ");
            String declarantPrenom = scanner.nextLine().trim();

            System.out.print("Lien avec le défunt: ");
            String lienDeclarant = scanner.nextLine().trim();

            System.out.print("Lieu d'enregistrement: ");
            String lieuEnregistrement = scanner.nextLine().trim();

            // Confirmation
            System.out.println("\n📋 RÉCAPITULATIF:");
            System.out.println("Défunt: " + prenomDefunt + " " + nomDefunt);
            System.out.println("Décédé le: " + DateUtil.formatDate(dateDeces) + " à " + lieuDeces);
            System.out.println("Déclarant: " + declarantPrenom + " " + declarantNom + " (" + lienDeclarant + ")");

            if (MenuPrincipal.confirmer("\nConfirmer la création de cet acte ?")) {
                ActeDeces acte = acteService.creerActeDeces(
                        nomDefunt, prenomDefunt, dateDeces, lieuDeces, causeDeces,
                        declarantNom, declarantPrenom, lienDeclarant, lieuEnregistrement
                );

                if (acte != null) {
                    System.out.println("\n🎉 Acte de décès créé avec succès !");
                    System.out.println("Numéro d'acte: " + acte.getNumero());
                }
            } else {
                System.out.println("❌ Création annulée");
            }

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la création: " + e.getMessage());
        }

        MenuPrincipal.attendreEntree();
    }

    /**
     * Recherche un acte
     */
    private static void rechercherActe(Scanner scanner, ActeService acteService) {
        System.out.println("\n🔍 RECHERCHE D'ACTE");
        System.out.print("Numéro d'acte: ");
        String numero = scanner.nextLine().trim();

        var acte = acteService.rechercherActeParNumero(numero);
        if (acte.isPresent()) {
            System.out.println("\n✅ Acte trouvé:");
            System.out.println(acte.get().getInformationsSpecifiques());
            System.out.println("Statut: " + acte.get().getStatut());
        } else {
            System.out.println("❌ Aucun acte trouvé avec ce numéro");
        }

        MenuPrincipal.attendreEntree();
    }

    /**
     * Consulte les actes créés par l'agent connecté
     */
    private static void consulterMesActes(Scanner scanner, ActeService acteService) {
        System.out.println("\n📄 MES ACTES");
        List<Acte> actes = acteService.listerActes();

        if (actes.isEmpty()) {
            System.out.println("Aucun acte trouvé");
        } else {
            for (Acte acte : actes) {
                System.out.println("- " + acte.getNumero() + ": " + acte.getInformationsSpecifiques());
            }
        }

        MenuPrincipal.attendreEntree();
    }

    /**
     * Génère un extrait d'acte
     */
    private static void genererExtrait(Scanner scanner, ActeService acteService) {
        System.out.println("\n📝 GÉNÉRATION D'EXTRAIT");
        System.out.print("Numéro d'acte: ");
        String numero = scanner.nextLine().trim();

        String extrait = acteService.genererExtrait(numero);
        System.out.println("\n" + extrait);

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

    /**
     * Saisit une date au format dd/MM/yyyy
     */
    private static LocalDate saisirDate(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String dateStr = scanner.nextLine().trim();

            LocalDate date = DateUtil.parseDate(dateStr);
            if (date != null) {
                return date;
            } else {
                System.out.println("❌ Format de date invalide. Utilisez dd/MM/yyyy (ex: 15/03/2025)");
            }
        }
    }
}