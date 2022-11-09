package br.edu.ufcg.integra_ru.dtos;

import br.edu.ufcg.integra_ru.models.TipoCardapio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardapioDTO {

    private Long id;

    private TipoCardapio tipo;

    private String nome;

    private List<String> itens;
}
