package br.edu.ufcg.integra_ru.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardapioDTO {

    private Long id;

    private List<PratoDTO> pratos;

    private LocalDate dataCardapio;
}
