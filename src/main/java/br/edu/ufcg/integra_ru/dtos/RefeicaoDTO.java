package br.edu.ufcg.integra_ru.dtos;

import br.edu.ufcg.integra_ru.models.Prato;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class RefeicaoDTO {

    private String usuarioMatricula;

    private LocalDateTime dataReserva;

    private int avaliacaoQuant;

    private String avaliacaoComentario;

    private PratoDTO prato;

    public RefeicaoDTO(String usuarioMatricula, LocalDateTime dataReserva, int avaliacaoQuant, String avaliacaoComentario, PratoDTO prato) {
        this.usuarioMatricula = usuarioMatricula;
        this.dataReserva = dataReserva;
        this.avaliacaoQuant = avaliacaoQuant;
        this.avaliacaoComentario = avaliacaoComentario;
        this.prato = prato;
    }

    public String getUsuarioMatricula() {
        return usuarioMatricula;
    }

    public void setUsuarioMatricula(String usuarioMatricula) {
        this.usuarioMatricula = usuarioMatricula;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDateTime dataReserva) {
        this.dataReserva = dataReserva;
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

    public PratoDTO getPrato() {
        return prato;
    }

    public void setPrato(PratoDTO prato) {
        this.prato = prato;
    }
}
