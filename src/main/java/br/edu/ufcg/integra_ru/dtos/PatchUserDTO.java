package br.edu.ufcg.integra_ru.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatchUserDTO {

    @NotEmpty
    private String nome;
    @NotEmpty
    private String email;
    @NotEmpty
    private String telefone;
    @NotEmpty
    private String urlImagem;
}
