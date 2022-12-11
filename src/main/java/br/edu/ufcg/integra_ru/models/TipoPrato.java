package br.edu.ufcg.integra_ru.models;

public enum TipoPrato {

    COMUM("Menu comum"), VEGANO("Menu vegano"), VEGETARIANO("Menu vegetariano");

    private String tipo;

    TipoPrato(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
