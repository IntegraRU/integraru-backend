package br.edu.ufcg.integra_ru.models;

public enum TipoCardapio {

    COMUM("Menu comum"), VEGANO("Menu vegano"), VEGERATIANO("Menu vegetariano");

    private String tipo;

    TipoCardapio(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
