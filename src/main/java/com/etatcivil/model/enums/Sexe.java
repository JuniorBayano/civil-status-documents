package main.java.com.etatcivil.model.enums;


public enum Sexe {
    MASCULIN("M", "Masculin"),
    FEMININ("F", "Feminin");

    private final String code;
    private final String libelle;

    Sexe(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }


    public static Sexe fromCode(String code) {
        for (Sexe sexe : values()) {
            if (sexe.code.equals(code)) {
                return sexe;
            }
        }
        throw new IllegalArgumentException("Code sexe invalide: " + code);
    }
}