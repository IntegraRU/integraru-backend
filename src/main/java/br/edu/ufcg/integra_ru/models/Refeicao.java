package br.edu.ufcg.integra_ru.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Refeicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;

    private ModalidadePrato modalidade;

    private TipoPrato tipo;

    private Date data;

    private int avaliacaoQuant;

    private String avaliacaoComentario;

    private Refeicao(){}

    public Refeicao(String usuario, ModalidadePrato opcao, TipoPrato tipo, Date data) {
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
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
