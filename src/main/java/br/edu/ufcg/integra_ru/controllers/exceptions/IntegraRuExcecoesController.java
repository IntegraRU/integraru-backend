package br.edu.ufcg.integra_ru.controllers.exceptions;

import br.edu.ufcg.integra_ru.dtos.ExcecaoDTO;
import br.edu.ufcg.integra_ru.services.exceptions.BadRequestExcecao;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontradoExcecao;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class IntegraRuExcecoesController extends ResponseEntityExceptionHandler {


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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> erros = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> erros.add(fieldError.getDefaultMessage()));

        return ResponseEntity.badRequest().body(ExcecaoDTO.builder()
                .mensagem("Erro de validação")
                .status(HttpStatus.BAD_REQUEST.value())
                .erros(erros)
                .build());
    }
}
