package br.edu.ufcg.integra_ru.dtos;

import java.time.LocalDateTime;

public class CheckoutDTO {

    private Long RefeicaoID;

    private LocalDateTime dataCheckout;

    private String matriculaUser;

    public Long getRefeicaoID() {
        return RefeicaoID;
    }

    public void setRefeicaoID(Long refeicaoID) {
        RefeicaoID = refeicaoID;
    }

    public LocalDateTime getDataCheckout() {
        return dataCheckout;
    }

    public void setDataCheckout(LocalDateTime dataCheckout) {
        this.dataCheckout = dataCheckout;
    }

    public String getMatriculaUser() {
        return matriculaUser;
    }

    public void setMatriculaUser(String matriculaUser) {
        this.matriculaUser = matriculaUser;
    }
}
