package br.edu.ufcg.integra_ru.dtos;

import br.edu.ufcg.integra_ru.models.ModalidadePrato;
import br.edu.ufcg.integra_ru.models.TipoPrato;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Date;

public class RefeicaoDTO {

    private ModalidadePrato modalidadePrato;

    private TipoPrato tipo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data;

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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
