package br.edu.ufcg.integra_ru.dtos;

public class AvaliacaoDTO {

    private int avaliacaoQuantitativa;

    private String avaliacaoComentario;

    public int getAvaliacaoQuantitativa() {
        return avaliacaoQuantitativa;
    }

    public void setAvaliacaoQuantitativa(int avaliacaoQuantitativa) {
        this.avaliacaoQuantitativa = avaliacaoQuantitativa;
    }

    public String getAvaliacaoComentario() {
        return avaliacaoComentario;
    }

    public void setAvaliacaoComentario(String avaliacaoComentario) {
        this.avaliacaoComentario = avaliacaoComentario;
    }
}
