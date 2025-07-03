-- Script de création de la base de données pour le Système d'État Civil
-- TP3 : Système de Gestion des Actes d'État Civil

-- Création de la base de données
CREATE DATABASE IF NOT EXISTS etat_civil_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE etat_civil_db;

-- Table des utilisateurs
CREATE TABLE utilisateur (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             nom VARCHAR(100) NOT NULL,
                             prenom VARCHAR(100) NOT NULL,
                             role ENUM('AGENT', 'CHEF', 'INVITE') NOT NULL,
                             login VARCHAR(50) UNIQUE NOT NULL,
                             password VARCHAR(255) NOT NULL,
                             date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             actif BOOLEAN DEFAULT TRUE
);

-- Table générique des actes
CREATE TABLE acte (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      numero VARCHAR(20) UNIQUE NOT NULL,
                      type ENUM('NAISSANCE', 'MARIAGE', 'DECES') NOT NULL,
                      date_enregistrement DATE NOT NULL,
                      lieu_enregistrement VARCHAR(200) NOT NULL,
                      id_agent INT NOT NULL,
                      date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      statut ENUM('EN_ATTENTE', 'SIGNE', 'ARCHIVE') DEFAULT 'EN_ATTENTE',
                      FOREIGN KEY (id_agent) REFERENCES utilisateur(id)
);

-- Table des actes de naissance
CREATE TABLE naissance (
                           id_acte INT PRIMARY KEY,
                           nom_enfant VARCHAR(100) NOT NULL,
                           prenom_enfant VARCHAR(100) NOT NULL,
                           sexe ENUM('M', 'F') NOT NULL,
                           date_naissance DATE NOT NULL,
                           lieu_naissance VARCHAR(200) NOT NULL,
                           nom_pere VARCHAR(100),
                           prenom_pere VARCHAR(100),
                           nom_mere VARCHAR(100) NOT NULL,
                           prenom_mere VARCHAR(100) NOT NULL,
                           numero_registre VARCHAR(50),
                           FOREIGN KEY (id_acte) REFERENCES acte(id) ON DELETE CASCADE
);

-- Table des actes de mariage
CREATE TABLE mariage (
                         id_acte INT PRIMARY KEY,
                         nom_epoux VARCHAR(100) NOT NULL,
                         prenom_epoux VARCHAR(100) NOT NULL,
                         nom_epouse VARCHAR(100) NOT NULL,
                         prenom_epouse VARCHAR(100) NOT NULL,
                         date_mariage DATE NOT NULL,
                         lieu_mariage VARCHAR(200) NOT NULL,
                         regime_matrimonial VARCHAR(100) DEFAULT 'Communauté de biens',
                         temoin1_nom VARCHAR(100),
                         temoin1_prenom VARCHAR(100),
                         temoin2_nom VARCHAR(100),
                         temoin2_prenom VARCHAR(100),
                         numero_registre VARCHAR(50),
                         FOREIGN KEY (id_acte) REFERENCES acte(id) ON DELETE CASCADE
);

-- Table des actes de décès
CREATE TABLE deces (
                       id_acte INT PRIMARY KEY,
                       nom_defunt VARCHAR(100) NOT NULL,
                       prenom_defunt VARCHAR(100) NOT NULL,
                       date_deces DATE NOT NULL,
                       lieu_deces VARCHAR(200) NOT NULL,
                       cause_deces TEXT,
                       declarant_nom VARCHAR(100) NOT NULL,
                       declarant_prenom VARCHAR(100) NOT NULL,
                       lien_declarant VARCHAR(100) NOT NULL,
                       numero_registre VARCHAR(50),
                       FOREIGN KEY (id_acte) REFERENCES acte(id) ON DELETE CASCADE
);

-- Index pour optimiser les recherches
CREATE INDEX idx_acte_numero ON acte(numero);
CREATE INDEX idx_acte_type ON acte(type);
CREATE INDEX idx_acte_date ON acte(date_enregistrement);
CREATE INDEX idx_naissance_nom ON naissance(nom_enfant, prenom_enfant);
CREATE INDEX idx_naissance_date ON naissance(date_naissance);
CREATE INDEX idx_mariage_epoux ON mariage(nom_epoux, prenom_epoux);
CREATE INDEX idx_mariage_epouse ON mariage(nom_epouse, prenom_epouse);
CREATE INDEX idx_deces_defunt ON deces(nom_defunt, prenom_defunt);

-- Insertion des utilisateurs par défaut
INSERT INTO utilisateur (nom, prenom, role, login, password) VALUES
                                                                 ('ADMIN', 'Chef', 'CHEF', 'chef', 'admin123'),
                                                                 ('DUPONT', 'Jean', 'AGENT', 'agent1', 'agent123'),
                                                                 ('MARTIN', 'Marie', 'AGENT', 'agent2', 'agent123'),
                                                                 ('INVITE', 'Public', 'INVITE', 'invite', 'invite123');

-- Affichage des tables créées
SHOW TABLES;