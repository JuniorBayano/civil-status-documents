package main.java.com.etatcivil.model.enums;


public enum TypeActe {
    NAISSANCE("Naissance", "Acte de naissance"),
    MARIAGE("Mariage", "Acte de mariage"),
    DECES("Deces", "Acte de deces");

    private final String libelle;
    private final String description;

    TypeActe(String libelle, String description) {
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