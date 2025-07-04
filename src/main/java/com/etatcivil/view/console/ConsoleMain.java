package main.java.com.etatcivil.view.console;



public class ConsoleMain {

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("    SYSTÈME DE GESTION DES ACTES D'ÉTAT CIVIL");
        System.out.println("=".repeat(60));
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           APPLICATION DÉMARRÉE AVEC SUCCÈS");
        System.out.println("=".repeat(60));

        MenuPrincipal.afficherBienvenue();
        MenuPrincipal.afficher();
    }
}

