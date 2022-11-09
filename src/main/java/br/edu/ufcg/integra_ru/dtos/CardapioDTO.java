package br.edu.ufcg.integra_ru.dtos;

import br.edu.ufcg.integra_ru.models.TipoCardapio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardapioDTO {

    private Long id;

    @NotNull
    private TipoCardapio tipo;

    @NotNull
    private String nome;

    @NotNull
    private Set<String> itens = new HashSet<>();

    private String urlImagem;
}
