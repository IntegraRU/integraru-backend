package br.edu.ufcg.integra_ru.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario  {

    @Id
    private String matricula;

    @NotNull()
    private String nome;

    @NotNull()
    private String email;

    private String telefone;

    private String urlImagem;

}
