package br.edu.ufcg.integra_ru.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorRefeicao {

    static final String REFEICAO_NAO_EXISTE = "A refeição de ID %s não está cadastrada no sistema.";

    static final String NAO_HA_REFEICOES = "Não há refeições cadastradas no sistema.";

    static final String USUARIO_NAO_REFEICOES = "Não há refeições cadastradas para o usuário.";

    static final String DATA_NAO_REFEICOES = "Não há refeições cadastradas para a data selecionada.";

    public static ResponseEntity<CustomErrorType> errorRefeicaoNaoExiste(Long id) {
        return new ResponseEntity<>(new CustomErrorType(String.format(REFEICAO_NAO_EXISTE, id)), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> errorSemRefeicoes() {
        return new ResponseEntity<>(new CustomErrorType(NAO_HA_REFEICOES), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> errorUsuarioSemRefeicoes() {
        return new ResponseEntity<>(new CustomErrorType(USUARIO_NAO_REFEICOES), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> errorDataSemRefeicoes() {
        return new ResponseEntity<>(new CustomErrorType(DATA_NAO_REFEICOES), HttpStatus.BAD_REQUEST);
    }
}
