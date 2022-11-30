package br.edu.ufcg.integra_ru.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Refeicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;

    private ModalidadePrato modalidade;

    private TipoPrato tipo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data;

    private int avaliacaoQuant;

    private String avaliacaoComentario;

    private Refeicao(){}

    public Refeicao(String usuario, ModalidadePrato opcao, TipoPrato tipo, LocalDate data) {
        this.usuario = usuario;
        this.modalidade = opcao;
        this.tipo = tipo;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public ModalidadePrato getModalidade() {
        return modalidade;
    }

    public void setModalidade(ModalidadePrato modalidade) {
        this.modalidade = modalidade;
    }

    public TipoPrato getTipo() {
        return tipo;
    }

    public void setTipo(TipoPrato tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getAvaliacaoQuant() {
        return avaliacaoQuant;
    }

    public void setAvaliacaoQuant(int avaliacaoQuant) {
        this.avaliacaoQuant = avaliacaoQuant;
    }

    public String getAvaliacaoComentario() {
        return avaliacaoComentario;
    }

    public void setAvaliacaoComentario(String avaliacaoComentario) {
        this.avaliacaoComentario = avaliacaoComentario;
    }
}
