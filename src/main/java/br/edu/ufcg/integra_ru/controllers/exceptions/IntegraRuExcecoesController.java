package br.edu.ufcg.integra_ru.controllers.exceptions;

import br.edu.ufcg.integra_ru.dtos.ExcecaoDTO;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontrado;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IntegraRuExcecoesController {


    @ExceptionHandler(RecursoNaoEncontrado.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExcecaoDTO notFoundException(RecursoNaoEncontrado rne){
        return ExcecaoDTO.builder()
                .mensagem(rne.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }
}
