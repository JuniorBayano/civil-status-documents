package main.java.com.etatcivil.model.enums;

public enum RoleUtilisateur {
    AGENT("Agent d'etat civil", "Enregistre les actes, consulte et modifie ses saisies"),
    CHEF("Chef d'etat civil", "Accède à tous les actes, valide, genere les extraits officiels"),
    INVITE("Utilisateur e", "Peut consulter un acte par numero et date");

    private final String libelle;
    private final String description;

    RoleUtilisateur(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return libelle;
    }
}