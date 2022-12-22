package br.edu.ufcg.integra_ru.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Refeicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matriculaUser;

    private String nomeUsuario;

    private Long pratoID;

    private LocalDateTime dataReserva;

    private LocalDateTime dataCheckout;

    private int avaliacaoQuant;

    private String avaliacaoComentario;

    public Refeicao(String matriculaUser, String nomeUsuario, Long pratoID, LocalDateTime dataReserva){
        this.matriculaUser = matriculaUser;
        this.nomeUsuario = nomeUsuario;
        this.dataReserva = dataReserva;
        this.pratoID = pratoID;
    }
}
