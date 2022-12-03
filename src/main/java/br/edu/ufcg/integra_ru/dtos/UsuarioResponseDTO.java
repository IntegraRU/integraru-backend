package br.edu.ufcg.integra_ru.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {

    private String matricula;

    private String nome;

    private String email;

    private String telefone;

    private String urlImagem;

}
