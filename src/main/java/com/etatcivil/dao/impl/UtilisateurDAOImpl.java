package main.java.com.etatcivil.dao.impl;


import main.java.com.etatcivil.dao.interfaces.DAOException;
import main.java.com.etatcivil.dao.interfaces.IUtilisateurDAO;
import main.java.com.etatcivil.model.entities.Utilisateur;
import main.java.com.etatcivil.model.enums.RoleUtilisateur;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation DAO pour la gestion des utilisateurs
 */
public class UtilisateurDAOImpl implements IUtilisateurDAO {

    private final DatabaseConnection dbConnection;

    public UtilisateurDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) throws DAOException {
        if (utilisateur.getId() == 0) {
            return create(utilisateur);
        } else {
            return update(utilisateur);
        }
    }

    private Utilisateur create(Utilisateur utilisateur) throws DAOException {
        String sql = "INSERT INTO utilisateur (nom, prenom, role, login, password, date_creation, actif) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getRole().name());
            stmt.setString(4, utilisateur.getLogin());
            stmt.setString(5, utilisateur.getPassword());
            stmt.setTimestamp(6, Timestamp.valueOf(utilisateur.getDateCreation()));
            stmt.setBoolean(7, utilisateur.isActif());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new DAOException("Échec de la création de l'utilisateur");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    utilisateur.setId(generatedKeys.getInt(1));
                }
            }

            return utilisateur;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("création utilisateur", e);
        }
    }

    private Utilisateur update(Utilisateur utilisateur) throws DAOException {
        String sql = "UPDATE utilisateur SET nom=?, prenom=?, role=?, login=?, password=?, actif=? WHERE id=?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getRole().name());
            stmt.setString(4, utilisateur.getLogin());
            stmt.setString(5, utilisateur.getPassword());
            stmt.setBoolean(6, utilisateur.isActif());
            stmt.setInt(7, utilisateur.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw DAOException.objetNonTrouve("Utilisateur", "ID=" + utilisateur.getId());
            }

            return utilisateur;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("mise à jour utilisateur", e);
        }
    }

    @Override
    public Optional<Utilisateur> findById(int id) throws DAOException {
        String sql = "SELECT * FROM utilisateur WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUtilisateur(rs));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw DAOException.fromSQLException("recherche utilisateur par ID", e);
        }
    }

    @Override
    public Optional<Utilisateur> findByLogin(String login) throws DAOException {
        String sql = "SELECT * FROM utilisateur WHERE login = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUtilisateur(rs));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw DAOException.fromSQLException("recherche utilisateur par login", e);
        }
    }

    @Override
    public Optional<Utilisateur> authenticate(String login, String password) throws DAOException {
        String sql = "SELECT * FROM utilisateur WHERE login = ? AND password = ? AND actif = true";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUtilisateur(rs));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw DAOException.fromSQLException("authentification utilisateur", e);
        }
    }

    @Override
    public List<Utilisateur> findAll() throws DAOException {
        String sql = "SELECT * FROM utilisateur ORDER BY nom, prenom";
        List<Utilisateur> utilisateurs = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                utilisateurs.add(mapResultSetToUtilisateur(rs));
            }

            return utilisateurs;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("récupération de tous les utilisateurs", e);
        }
    }

    @Override
    public List<Utilisateur> findByRole(RoleUtilisateur role) throws DAOException {
        String sql = "SELECT * FROM utilisateur WHERE role = ? ORDER BY nom, prenom";
        List<Utilisateur> utilisateurs = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role.name());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    utilisateurs.add(mapResultSetToUtilisateur(rs));
                }
            }

            return utilisateurs;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("recherche utilisateurs par rôle", e);
        }
    }

    @Override
    public List<Utilisateur> findActifs() throws DAOException {
        String sql = "SELECT * FROM utilisateur WHERE actif = true ORDER BY nom, prenom";
        List<Utilisateur> utilisateurs = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                utilisateurs.add(mapResultSetToUtilisateur(rs));
            }

            return utilisateurs;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("récupération utilisateurs actifs", e);
        }
    }

    @Override
    public boolean updatePassword(int id, String nouveauMotDePasse) throws DAOException {
        String sql = "UPDATE utilisateur SET password = ? WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nouveauMotDePasse);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("mise à jour mot de passe", e);
        }
    }

    @Override
    public boolean updateStatut(int id, boolean actif) throws DAOException {
        String sql = "UPDATE utilisateur SET actif = ? WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, actif);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("mise à jour statut utilisateur", e);
        }
    }

    @Override
    public boolean delete(int id) throws DAOException {
        String sql = "DELETE FROM utilisateur WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("suppression utilisateur", e);
        }
    }

    @Override
    public boolean existsByLogin(String login) throws DAOException {
        String sql = "SELECT COUNT(*) FROM utilisateur WHERE login = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

            return false;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("vérification existence login", e);
        }
    }

    @Override
    public long count() throws DAOException {
        String sql = "SELECT COUNT(*) FROM utilisateur";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }

            return 0;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("comptage utilisateurs", e);
        }
    }

    @Override
    public long countByRole(RoleUtilisateur role) throws DAOException {
        String sql = "SELECT COUNT(*) FROM utilisateur WHERE role = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role.name());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

            return 0;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("comptage utilisateurs par rôle", e);
        }
    }

    @Override
    public List<Utilisateur> rechercherParNom(String critere) throws DAOException {
        String sql = "SELECT * FROM utilisateur WHERE nom LIKE ? OR prenom LIKE ? ORDER BY nom, prenom";
        List<Utilisateur> utilisateurs = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String pattern = "%" + critere + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    utilisateurs.add(mapResultSetToUtilisateur(rs));
                }
            }

            return utilisateurs;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("recherche utilisateurs par nom", e);
        }
    }

    /**
     * Convertit un ResultSet en objet Utilisateur
     */
    private Utilisateur mapResultSetToUtilisateur(ResultSet rs) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(rs.getInt("id"));
        utilisateur.setNom(rs.getString("nom"));
        utilisateur.setPrenom(rs.getString("prenom"));
        utilisateur.setRole(RoleUtilisateur.valueOf(rs.getString("role")));
        utilisateur.setLogin(rs.getString("login"));
        utilisateur.setPassword(rs.getString("password"));

        Timestamp dateCreation = rs.getTimestamp("date_creation");
        if (dateCreation != null) {
            utilisateur.setDateCreation(dateCreation.toLocalDateTime());
        }

        utilisateur.setActif(rs.getBoolean("actif"));

        return utilisateur;
    }
}