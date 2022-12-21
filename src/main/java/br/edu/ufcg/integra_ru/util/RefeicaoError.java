package br.edu.ufcg.integra_ru.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class RefeicaoError {

    static final String REFEICAO_CADASTRADA = "Já existe uma refeição cadastrada para o usuário de matrícula %s na data %s.";

    static final String SEM_REFEICOES = "Não há nenhuma refeição cadastrada no sistema.";

    static final String REFEICAO_NAO_EXISTE = "Não há nenhuma refeição com o id %s no sistema.";

    public static ResponseEntity<CustomErrorType> errorRefeicaoJaCadastrada(String usuarioMatricula, LocalDateTime data) {
        return new ResponseEntity<>(new CustomErrorType(String.format(REFEICAO_CADASTRADA, usuarioMatricula, data.toString())), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> errorSemRefeicoes() {
        return new ResponseEntity<>(new CustomErrorType(SEM_REFEICOES), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> errorRefeicaoNaoExiste(Long id) {
        return new ResponseEntity<>(new CustomErrorType(String.format(REFEICAO_NAO_EXISTE, id)), HttpStatus.BAD_REQUEST);
    }
}
