package main.java.com.etatcivil.view.console;

import main.java.com.etatcivil.service.ActeService;
import main.java.com.etatcivil.service.AuthenticationService;
import main.java.com.etatcivil.view.console.MenuPrincipal;

import java.util.Scanner;

/**
 * Menu pour les utilisateurs invités (accès public)
 */
public class MenuInvite {

    /**
     * Affiche le menu invité
     * @param scanner Scanner pour les saisies
     */
    public static void afficher(Scanner scanner) {
        // Créer un service temporaire pour les invités
        AuthenticationService authService = new AuthenticationService();
        ActeService acteService = new ActeService(authService);

        while (true) {
            afficherMenuInvite();

            System.out.print("Votre choix: ");
            String choix = scanner.nextLine().trim();

            switch (choix) {
                case "1":
                    consulterActe(scanner, acteService);
                    break;

                case "2":
                    afficherInformations();
                    break;

                case "0":
                    return; // Retour au menu principal

                default:
                    System.out.println("❌ Choix invalide. Veuillez saisir 1, 2 ou 0.");
                    MenuPrincipal.attendreEntree();
            }
        }
    }

    /**
     * Affiche le menu des options pour les invités
     */
    private static void afficherMenuInvite() {
        MenuPrincipal.effacerEcran();
        System.out.println("🌐 Mode: Accès Public (Invité)");
        System.out.println("\n" + "─".repeat(50));
        System.out.println("                🔍 CONSULTATION PUBLIQUE");
        System.out.println("─".repeat(50));
        System.out.println("1. 📄 Consulter un acte par numéro");
        System.out.println("2. ℹ️  Informations sur les services");
        System.out.println("0. 🔙 Retour au menu principal");
        System.out.println("─".repeat(50));
    }

    /**
     * Consulte un acte par son numéro (accès limité)
     */
    private static void consulterActe(Scanner scanner, ActeService acteService) {
        System.out.println("\n📄 CONSULTATION D'ACTE");
        System.out.println("─".repeat(40));

        System.out.print("Numéro d'acte: ");
        String numero = scanner.nextLine().trim();

        if (numero.isEmpty()) {
            System.out.println("❌ Veuillez saisir un numéro d'acte valide");
            MenuPrincipal.attendreEntree();
            return;
        }

        var acte = acteService.rechercherActeParNumero(numero);

        if (acte.isPresent()) {
            if (acte.get().isSigne()) {
                System.out.println("\n✅ ACTE TROUVÉ:");
                System.out.println("─".repeat(40));
                System.out.println("Numéro: " + acte.get().getNumero());
                System.out.println("Type: " + acte.get().getType().getLibelle());
                System.out.println("Date d'enregistrement: " + acte.get().getDateEnregistrement());
                System.out.println("Lieu: " + acte.get().getLieuEnregistrement());
                System.out.println("Statut: " + acte.get().getStatut().getLibelle());

                // Informations limitées pour les invités
                System.out.println("\n📋 INFORMATIONS GÉNÉRALES:");
                System.out.println(acte.get().getInformationsSpecifiques());

                System.out.println("\n⚠️  ACCÈS LIMITÉ:");
                System.out.println("Pour obtenir un extrait officiel complet,");
                System.out.println("veuillez vous adresser au service d'état civil");
                System.out.println("avec une pièce d'identité valide.");

            } else {
                System.out.println("❌ Cet acte n'est pas encore validé");
                System.out.println("   Les actes non signés ne sont pas consultables publiquement");
            }
        } else {
            System.out.println("❌ Aucun acte trouvé avec ce numéro");
            System.out.println("   Vérifiez le numéro ou contactez le service d'état civil");
        }

        MenuPrincipal.attendreEntree();
    }

    /**
     * Affiche les informations sur les services d'état civil
     */
    private static void afficherInformations() {
        MenuPrincipal.effacerEcran();
        System.out.println("ℹ️  INFORMATIONS SUR LES SERVICES D'ÉTAT CIVIL");
        System.out.println("=".repeat(60));

        System.out.println("\n🏛️  SERVICES DISPONIBLES:");
        System.out.println("• Enregistrement des naissances");
        System.out.println("• Enregistrement des mariages");
        System.out.println("• Enregistrement des décès");
        System.out.println("• Délivrance d'extraits d'actes");
        System.out.println("• Consultation des registres");

        System.out.println("\n📋 DOCUMENTS REQUIS:");
        System.out.println("• Pour les naissances: Certificat médical, pièces d'identité des parents");
        System.out.println("• Pour les mariages: Pièces d'identité, certificats de célibat");
        System.out.println("• Pour les décès: Certificat médical, pièce d'identité du déclarant");

        System.out.println("\n⏰ HORAIRES D'OUVERTURE:");
        System.out.println("• Lundi à Vendredi: 8h00 - 16h00");
        System.out.println("• Samedi: 8h00 - 12h00");
        System.out.println("• Fermé le dimanche et jours fériés");

        System.out.println("\n📍 LOCALISATION:");
        System.out.println("• Mairie de Yaoundé");
        System.out.println("• Place de l'Hôtel de Ville");
        System.out.println("• Yaoundé, Cameroun");

        System.out.println("\n📞 CONTACT:");
        System.out.println("• Téléphone: +237 XXX XXX XXX");
        System.out.println("• Email: etatcivil@mairie-yaounde.cm");

        System.out.println("\n💰 TARIFS:");
        System.out.println("• Extrait de naissance: 1 000 FCFA");
        System.out.println("• Extrait de mariage: 1 500 FCFA");
        System.out.println("• Extrait de décès: 1 000 FCFA");
        System.out.println("• Copie certifiée conforme: 2 000 FCFA");

        System.out.println("\n⚖️  CADRE LÉGAL:");
        System.out.println("• Code civil camerounais");
        System.out.println("• Loi n° 2011/011 du 6 mai 2011");
        System.out.println("• Décret d'application n° 2011/408");

        System.out.println("\n🔒 PROTECTION DES DONNÉES:");
        System.out.println("• Vos données personnelles sont protégées");
        System.out.println("• Accès limité aux personnes autorisées");
        System.out.println("• Conformité aux lois sur la vie privée");

        MenuPrincipal.attendreEntree();
    }
}