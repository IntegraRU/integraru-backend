package br.edu.ufcg.integra_ru.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExcecaoDTO {

    private String mensagem;

    private int status;
}
