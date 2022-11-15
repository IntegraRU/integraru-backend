package br.edu.ufcg.integra_ru.dtos;

import br.edu.ufcg.integra_ru.models.ModalidadePrato;
import br.edu.ufcg.integra_ru.models.TipoPrato;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PratoDTO {

    private Long id;

    @NotNull
    private TipoPrato tipo;

    @NotNull
    private ModalidadePrato modalidadePrato;

    @NotNull
    private String nome;

    private String itens;

    private String urlImagem;
}
