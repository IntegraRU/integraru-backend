package br.edu.ufcg.integra_ru.dtos;

import br.edu.ufcg.integra_ru.models.ModalidadePrato;
import br.edu.ufcg.integra_ru.models.TipoPrato;

import java.util.Date;

public class RefeicaoDTO {

    private ModalidadePrato modalidadePrato;

    private TipoPrato tipo;

    private Date data;

    private String usuario;

    public ModalidadePrato getModalidadePrato() {
        return modalidadePrato;
    }

    public void setModalidadePrato(ModalidadePrato modalidadePrato) {
        this.modalidadePrato = modalidadePrato;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
