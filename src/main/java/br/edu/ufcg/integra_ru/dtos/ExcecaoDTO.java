package br.edu.ufcg.integra_ru.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExcecaoDTO {

    private String mensagem;

    private int status;

    private List<String> erros;
}
