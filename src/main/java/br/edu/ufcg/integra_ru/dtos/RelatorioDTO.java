package br.edu.ufcg.integra_ru.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class RelatorioDTO {

    private double avaliacaoMedia;

    private List<int> avaliacoesQuant;

    private List<String> avaliacoesComentarios;

    private List<LocalDateTime> checkouts;

    private int comensais;

    private double taxaAvaliacao;

    private double taxaComentario;

    private double taxaOptantes;

    public double getAvaliacaoMedia() {
        return avaliacaoMedia;
    }

    public void setAvaliacaoMedia(double avaliacaoMedia) {
        this.avaliacaoMedia = avaliacaoMedia;
    }

    public List<int> getAvaliacoesQuant() {
        return avaliacoesQuant;
    }

    public void setAvaliacoesQuant(List<int> avaliacoesQuant) {
        this.avaliacoesQuant = avaliacoesQuant;
    }

    public List<String> getAvaliacoesComentarios() {
        return avaliacoesComentarios;
    }

    public void setAvaliacoesComentarios(List<String> avaliacoesComentarios) {
        this.avaliacoesComentarios = avaliacoesComentarios;
    }

    public List<LocalDateTime> getCheckouts() {
        return checkouts;
    }

    public void setCheckouts(List<LocalDateTime> checkouts) {
        this.checkouts = checkouts;
    }

    public int getComensais() {
        return comensais;
    }

    public void setComensais(int comensais) {
        this.comensais = comensais;
    }

    public double getTaxaAvaliacao() {
        return taxaAvaliacao;
    }

    public void setTaxaAvaliacao(double taxaAvaliacao) {
        this.taxaAvaliacao = taxaAvaliacao;
    }

    public double getTaxaComentario() {
        return taxaComentario;
    }

    public void setTaxaComentario(double taxaComentario) {
        this.taxaComentario = taxaComentario;
    }

    public double getTaxaOptantes() {
        return taxaOptantes;
    }

    public void setTaxaOptantes(double taxaOptantes) {
        this.taxaOptantes = taxaOptantes;
    }

    public RelatorioDTO(double avaliacaoMedia, List<int> avaliacoesQuant, List<String> avaliacoesComentarios, List<LocalDateTime> checkouts, int comensais, double taxaAvaliacao, double taxaComentario, double taxaOptantes) {
        this.avaliacaoMedia = avaliacaoMedia;
        this.avaliacoesQuant = avaliacoesQuant;
        this.avaliacoesComentarios = avaliacoesComentarios;
        this.checkouts = checkouts;
        this.comensais = comensais;
        this.taxaAvaliacao = taxaAvaliacao;
        this.taxaComentario = taxaComentario;
        this.taxaOptantes = taxaOptantes;
    }
}
