package main.java.com.etatcivil.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;
    private Properties properties;

    private DatabaseConnection() {
        loadProperties();
        createConnection();
    }

    /**
     * Pattern Singleton pour avoir une seule instance
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Charge les propriétés de la base de données
     */
    private void loadProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("database.properties")) {

            if (input == null) {
                System.out.println("Fichier database.properties introuvable!");
                setDefaultProperties();
                return;
            }

            properties.load(input);
            System.out.println("✅ Propriétés de la base chargées avec succès");

        } catch (IOException e) {
            System.err.println("❌ Erreur lors du chargement des propriétés: " + e.getMessage());
            setDefaultProperties();
        }
    }


    private void setDefaultProperties() {
        properties.setProperty("db.driver", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("db.url", "jdbc:mysql://localhost:3306/etat_civil_db?useSSL=false&serverTimezone=UTC");
        properties.setProperty("db.username", "root");
        properties.setProperty("db.password", "");
        System.out.println("⚠️  Utilisation des propriétés par défaut");
    }

    /**
     * Crée la connexion à la base de données
     */
    private void createConnection() {
        try {
            // Chargement du driver
            Class.forName(properties.getProperty("db.driver"));

            // Création de la connexion
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            connection = DriverManager.getConnection(url, username, password);

            if (connection != null && !connection.isClosed()) {
                System.out.println("✅ Connexion à la base de données établie");
                System.out.println("   URL: " + url);
                System.out.println("   Utilisateur: " + username);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver MySQL introuvable: " + e.getMessage());
            System.err.println("   Assurez-vous d'avoir le driver MySQL dans le classpath");
        } catch (SQLException e) {
            System.err.println("❌ Erreur de connexion à la base: " + e.getMessage());
            System.err.println("   Vérifiez que MySQL est démarré et que la base 'etat_civil_db' existe");
        }
    }

    /**
     * Retourne la connexion active
     */
    public Connection getConnection() {
        try {
            // Vérifier si la connexion est toujours valide
            if (connection == null || connection.isClosed()) {
                System.out.println("⚠️  Reconnexion à la base de données...");
                createConnection();
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la vérification de la connexion: " + e.getMessage());
            createConnection();
        }
        return connection;
    }

    /**
     * Ferme la connexion
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Connexion fermée");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la fermeture: " + e.getMessage());
        }
    }

    /**
     * Teste la connexion à la base
     */
    public boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("❌ Test de connexion échoué: " + e.getMessage());
            return false;
        }
    }
}