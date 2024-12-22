package com.example.casahogwarts;

public class CasaHogwarts {
    public static final int GRIFINORIA = 0;
    public static final int CORVINAL = 1;
    public static final int LUFA_LUFA = 2;
    public static final int SONSERINA = 3;



    public static String getNomeCasa(int indice) {
        switch (indice) {
            case GRIFINORIA:
                return "Grifinória";
            case SONSERINA:
                return "Sonserina";
            case CORVINAL:
                return "Corvinal";
            case LUFA_LUFA:
                return "Lufa-Lufa";
            default:
                return "Casa Desconhecida";
        }
    }
}