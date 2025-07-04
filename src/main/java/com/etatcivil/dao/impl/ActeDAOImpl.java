package main.java.com.etatcivil.dao.impl;



import main.java.com.etatcivil.dao.interfaces.DAOException;
import main.java.com.etatcivil.dao.interfaces.IActeDAO;
import main.java.com.etatcivil.model.entities.Acte;
import main.java.com.etatcivil.model.entities.ActeDeces;
import main.java.com.etatcivil.model.entities.ActeMariage;
import main.java.com.etatcivil.model.entities.ActeNaissance;
import main.java.com.etatcivil.model.enums.Sexe;
import main.java.com.etatcivil.model.enums.TypeActe;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * Implémentation DAO pour la gestion des actes (version simplifiée pour tests)
 */
public class ActeDAOImpl implements IActeDAO {

    private final DatabaseConnection dbConnection;

    public ActeDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public ActeNaissance saveNaissance(ActeNaissance acte) throws DAOException {
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Insérer dans la table acte
            int idActe = insertActeGenerique(conn, acte);
            acte.setId(idActe);

            // 2. Insérer dans la table naissance
            String sqlNaissance = "INSERT INTO naissance (id_acte, nom_enfant, prenom_enfant, sexe, " +
                    "date_naissance, lieu_naissance, nom_pere, prenom_pere, nom_mere, prenom_mere, numero_registre) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sqlNaissance)) {
                stmt.setInt(1, idActe);
                stmt.setString(2, acte.getNomEnfant());
                stmt.setString(3, acte.getPrenomEnfant());
                stmt.setString(4, acte.getSexe().getCode());
                stmt.setDate(5, Date.valueOf(acte.getDateNaissance()));
                stmt.setString(6, acte.getLieuNaissance());
                stmt.setString(7, acte.getNomPere());
                stmt.setString(8, acte.getPrenomPere());
                stmt.setString(9, acte.getNomMere());
                stmt.setString(10, acte.getPrenomMere());
                stmt.setString(11, acte.getNumeroRegistre());

                stmt.executeUpdate();
            }

            conn.commit();
            return acte;

        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            throw DAOException.fromSQLException("sauvegarde acte naissance", e);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); } catch (SQLException e) { /* ignore */ }
            }
        }
    }

    @Override
    public ActeMariage saveMariage(ActeMariage acte) throws DAOException {
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Insérer dans la table acte
            int idActe = insertActeGenerique(conn, acte);
            acte.setId(idActe);

            // 2. Insérer dans la table mariage
            String sqlMariage = "INSERT INTO mariage (id_acte, nom_epoux, prenom_epoux, nom_epouse, prenom_epouse, " +
                    "date_mariage, lieu_mariage, regime_matrimonial, temoin1_nom, temoin1_prenom, " +
                    "temoin2_nom, temoin2_prenom, numero_registre) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sqlMariage)) {
                stmt.setInt(1, idActe);
                stmt.setString(2, acte.getNomEpoux());
                stmt.setString(3, acte.getPrenomEpoux());
                stmt.setString(4, acte.getNomEpouse());
                stmt.setString(5, acte.getPrenomEpouse());
                stmt.setDate(6, Date.valueOf(acte.getDateMariage()));
                stmt.setString(7, acte.getLieuMariage());
                stmt.setString(8, acte.getRegimeMatrimonial());
                stmt.setString(9, acte.getTemoin1Nom());
                stmt.setString(10, acte.getTemoin1Prenom());
                stmt.setString(11, acte.getTemoin2Nom());
                stmt.setString(12, acte.getTemoin2Prenom());
                stmt.setString(13, acte.getNumeroRegistre());

                stmt.executeUpdate();
            }

            conn.commit();
            return acte;

        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            throw DAOException.fromSQLException("sauvegarde acte mariage", e);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); } catch (SQLException e) { /* ignore */ }
            }
        }
    }

    @Override
    public ActeDeces saveDeces(ActeDeces acte) throws DAOException {
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Insérer dans la table acte
            int idActe = insertActeGenerique(conn, acte);
            acte.setId(idActe);

            // 2. Insérer dans la table deces
            String sqlDeces = "INSERT INTO deces (id_acte, nom_defunt, prenom_defunt, date_deces, lieu_deces, " +
                    "cause_deces, declarant_nom, declarant_prenom, lien_declarant, numero_registre) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sqlDeces)) {
                stmt.setInt(1, idActe);
                stmt.setString(2, acte.getNomDefunt());
                stmt.setString(3, acte.getPrenomDefunt());
                stmt.setDate(4, Date.valueOf(acte.getDateDeces()));
                stmt.setString(5, acte.getLieuDeces());
                stmt.setString(6, acte.getCauseDeces());
                stmt.setString(7, acte.getDeclarantNom());
                stmt.setString(8, acte.getDeclarantPrenom());
                stmt.setString(9, acte.getLienDeclarant());
                stmt.setString(10, acte.getNumeroRegistre());

                stmt.executeUpdate();
            }

            conn.commit();
            return acte;

        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            throw DAOException.fromSQLException("sauvegarde acte décès", e);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); } catch (SQLException e) { /* ignore */ }
            }
        }
    }

    /**
     * Insère un acte générique et retourne l'ID généré
     */
    private int insertActeGenerique(Connection conn, Acte acte) throws SQLException {
        String sql = "INSERT INTO acte (numero, type, date_enregistrement, lieu_enregistrement, id_agent, statut) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, acte.getNumero());
            stmt.setString(2, acte.getType().name());
            stmt.setDate(3, Date.valueOf(acte.getDateEnregistrement()));
            stmt.setString(4, acte.getLieuEnregistrement());
            stmt.setInt(5, acte.getIdAgent());
            stmt.setString(6, acte.getStatut().name());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Échec de la création de l'acte");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Aucun ID généré pour l'acte");
                }
            }
        }
    }

    @Override
    public Optional<Acte> findById(int id) throws DAOException {
        String sql = "SELECT * FROM acte WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToActe(rs));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw DAOException.fromSQLException("recherche acte par ID", e);
        }
    }

    @Override
    public Optional<Acte> findByNumero(String numero) throws DAOException {
        String sql = "SELECT * FROM acte WHERE numero = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numero);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToActe(rs));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw DAOException.fromSQLException("recherche acte par numéro", e);
        }
    }

    @Override
    public List<Acte> findAll() throws DAOException {
        String sql = "SELECT * FROM acte ORDER BY date_enregistrement DESC";
        List<Acte> actes = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                actes.add(mapResultSetToActe(rs));
            }

            return actes;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("récupération de tous les actes", e);
        }
    }

    @Override
    public long count() throws DAOException {
        String sql = "SELECT COUNT(*) FROM acte";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }

            return 0;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("comptage actes", e);
        }
    }

    /**
     * Convertit un ResultSet en objet Acte générique
     */
    private Acte mapResultSetToActe(ResultSet rs) throws SQLException {
        TypeActe type = TypeActe.valueOf(rs.getString("type"));
        int id = rs.getInt("id");

        try {
            switch (type) {
                case NAISSANCE:
                    Optional<ActeNaissance> naissance = findNaissanceById(id);
                    if (naissance.isPresent()) return naissance.get();
                    break;
                case MARIAGE:
                    Optional<ActeMariage> mariage = findMariageById(id);
                    if (mariage.isPresent()) return mariage.get();
                    break;
                case DECES:
                    Optional<ActeDeces> deces = findDecesById(id);
                    if (deces.isPresent()) return deces.get();
                    break;
            }
        } catch (DAOException e) {
            System.err.println("Erreur lors du chargement des détails de l'acte: " + e.getMessage());
        }

        // Fallback si on ne peut pas charger les détails spécifiques
        return new Acte() {
            {
                setId(id);
                setNumero(rs.getString("numero"));
                setType(type);
                setDateEnregistrement(rs.getDate("date_enregistrement").toLocalDate());
                setLieuEnregistrement(rs.getString("lieu_enregistrement"));
                setIdAgent(rs.getInt("id_agent"));
                setStatut(Acte.StatutActe.valueOf(rs.getString("statut")));

                Timestamp dateCreation = rs.getTimestamp("date_creation");
                if (dateCreation != null) {
                    setDateCreation(dateCreation.toLocalDateTime());
                }
            }

            @Override
            public String genererExtrait() {
                return "Extrait générique - Veuillez implémenter les méthodes spécifiques pour chaque type d'acte";
            }

            @Override
            public String getInformationsSpecifiques() {
                return "Informations génériques - Type: " + type;
            }

            @Override
            public boolean validerDonnees() { return true; }
        };
    }
    @Override
    public boolean updateStatut(int id, Acte.StatutActe nouveauStatut) throws DAOException {
        String sql = "UPDATE acte SET statut = ? WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nouveauStatut.name());
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw DAOException.fromSQLException("mise à jour statut acte", e);
        }
    }

    @Override
    public Optional<ActeNaissance> findNaissanceById(int id) throws DAOException {
        String sqlActe = "SELECT * FROM acte WHERE id = ?";
        String sqlNaissance = "SELECT * FROM naissance WHERE id_acte = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmtActe = conn.prepareStatement(sqlActe);
             PreparedStatement stmtNaissance = conn.prepareStatement(sqlNaissance)) {

            stmtActe.setInt(1, id);
            stmtNaissance.setInt(1, id);

            try (ResultSet rsActe = stmtActe.executeQuery();
                 ResultSet rsNaissance = stmtNaissance.executeQuery()) {

                if (rsActe.next() && rsNaissance.next()) {
                    ActeNaissance acte = new ActeNaissance();
                    // Remplir les champs de l'acte générique
                    remplirActeGenerique(rsActe, acte);
                    // Remplir les champs spécifiques à la naissance
                    acte.setNomEnfant(rsNaissance.getString("nom_enfant"));
                    acte.setPrenomEnfant(rsNaissance.getString("prenom_enfant"));
                    acte.setSexe(Sexe.fromCode(rsNaissance.getString("sexe")));
                    acte.setDateNaissance(rsNaissance.getDate("date_naissance").toLocalDate());
                    acte.setLieuNaissance(rsNaissance.getString("lieu_naissance"));
                    acte.setNomPere(rsNaissance.getString("nom_pere"));
                    acte.setPrenomPere(rsNaissance.getString("prenom_pere"));
                    acte.setNomMere(rsNaissance.getString("nom_mere"));
                    acte.setPrenomMere(rsNaissance.getString("prenom_mere"));
                    acte.setNumeroRegistre(rsNaissance.getString("numero_registre"));

                    return Optional.of(acte);
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw DAOException.fromSQLException("recherche acte naissance par ID", e);
        }
    }
    private void remplirActeGenerique(ResultSet rs, Acte acte) throws SQLException {
        acte.setId(rs.getInt("id"));
        acte.setNumero(rs.getString("numero"));
        acte.setType(TypeActe.valueOf(rs.getString("type")));
        acte.setDateEnregistrement(rs.getDate("date_enregistrement").toLocalDate());
        acte.setLieuEnregistrement(rs.getString("lieu_enregistrement"));
        acte.setIdAgent(rs.getInt("id_agent"));
        acte.setStatut(Acte.StatutActe.valueOf(rs.getString("statut")));

        Timestamp dateCreation = rs.getTimestamp("date_creation");
        if (dateCreation != null) {
            acte.setDateCreation(dateCreation.toLocalDateTime());
        }
    }

    public Map<String, Long> getStatistiquesParTypeEtPeriode(int annee, Optional<Integer> mois) {
        Map<String, Long> statistiques = new HashMap<>();

        String sql = "SELECT type, COUNT(*) as total FROM acte WHERE EXTRACT(YEAR FROM date_naissance) = ?";
        if (mois.isPresent()) {
            sql += " AND EXTRACT(MONTH FROM date_naissance) = ?";
        }
        sql += " GROUP BY type";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, annee);
            if (mois.isPresent()) {
                stmt.setInt(2, mois.get());
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String type = rs.getString("type");
                    long total = rs.getLong("total");
                    statistiques.put(type, total);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statistiques;
    }


// Implémentez de la même manière pour findMariageById et findDecesById



    // Implémentations minimales pour les autres méthodes (à compléter plus tard)
//    @Override public Optional<ActeNaissance> findNaissanceById(int id) throws DAOException { return Optional.empty(); }
    @Override public Optional<ActeMariage> findMariageById(int id) throws DAOException { return Optional.empty(); }
    @Override public Optional<ActeDeces> findDecesById(int id) throws DAOException { return Optional.empty(); }
    @Override public List<Acte> findByType(TypeActe type) throws DAOException { return new ArrayList<>(); }
    @Override public List<Acte> findByAgent(int idAgent) throws DAOException { return new ArrayList<>(); }
    @Override public List<Acte> findByPeriode(LocalDate dateDebut, LocalDate dateFin) throws DAOException { return new ArrayList<>(); }
    @Override public List<Acte> findByLieu(String lieu) throws DAOException { return new ArrayList<>(); }
    @Override public List<ActeNaissance> rechercherNaissance(String nom, String prenom) throws DAOException { return new ArrayList<>(); }
    @Override public List<ActeMariage> rechercherMariage(String nomEpoux, String nomEpouse) throws DAOException { return new ArrayList<>(); }
    @Override public List<ActeDeces> rechercherDeces(String nom, String prenom) throws DAOException { return new ArrayList<>(); }
//    @Override public boolean updateStatut(int id, Acte.StatutActe nouveauStatut) throws DAOException { return false; }
    @Override public boolean delete(int id) throws DAOException { return false; }
    @Override public boolean existsByNumero(String numero) throws DAOException { return false; }
    @Override public long countByType(TypeActe type) throws DAOException { return 0; }
    @Override public Map<TypeActe, Long> getStatistiquesMensuelles(int annee, int mois) throws DAOException { return new HashMap<>(); }
    @Override public Map<TypeActe, Long> getStatistiquesAnnuelles(int annee) throws DAOException { return new HashMap<>(); }
}