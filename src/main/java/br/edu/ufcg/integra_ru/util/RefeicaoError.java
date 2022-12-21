package br.edu.ufcg.integra_ru.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class RefeicaoError {

    static final String REFEICAO_CADASTRADA = "Já existe uma refeição cadastrada para o usuário de matrícula %s na data %s.";

    static final String SEM_REFEICOES = "Não há nenhuma refeição cadastrada no sistema.";

    static final String REFEICAO_NAO_EXISTE = "Não há nenhuma refeição com o id %s no sistema.";

    static final String JA_FEZ_CHECKOUT = "Já foi efetuado o checkout da refeição de id %s.";

    static final String NAO_FEZ_CHECKOUT = "Não é possível avaliar a refeição de id %s pois ainda não foi feito checkout para ela.";

    public static ResponseEntity<CustomErrorType> errorRefeicaoJaCadastrada(String usuarioMatricula, LocalDateTime data) {
        return new ResponseEntity<>(new CustomErrorType(String.format(REFEICAO_CADASTRADA, usuarioMatricula, data.toString())), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> errorSemRefeicoes() {
        return new ResponseEntity<>(new CustomErrorType(SEM_REFEICOES), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> errorRefeicaoNaoExiste(Long id) {
        return new ResponseEntity<>(new CustomErrorType(String.format(REFEICAO_NAO_EXISTE, id)), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> errorJaFezCheckout(Long id) {
        return new ResponseEntity<>(new CustomErrorType(String.format(JA_FEZ_CHECKOUT, id)), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> errorNaoFezCheckout(Long id) {
        return new ResponseEntity<>(new CustomErrorType(String.format(NAO_FEZ_CHECKOUT, id)), HttpStatus.BAD_REQUEST);
    }
}
