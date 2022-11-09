package br.edu.ufcg.integra_ru.controllers.exceptions;

import br.edu.ufcg.integra_ru.dtos.ExcecaoDTO;
import br.edu.ufcg.integra_ru.services.exceptions.BadRequestExcecao;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontradoExcecao;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IntegraRuExcecoesController {


    @ExceptionHandler(RecursoNaoEncontradoExcecao.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExcecaoDTO notFoundException(RecursoNaoEncontradoExcecao rne){
        return ExcecaoDTO.builder()
                .mensagem(rne.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler(BadRequestExcecao.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExcecaoDTO duplicateResoureException(BadRequestExcecao bre){
        return ExcecaoDTO.builder()
                .mensagem(bre.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
