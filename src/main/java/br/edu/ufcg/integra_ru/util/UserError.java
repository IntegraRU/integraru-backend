package br.edu.ufcg.integra_ru.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserError {

    static final String USUARIO_JA_EXISTE = "O usuário de matrícula %s já está cadastrado no sistema.";

    static final String USUARIO_NAO_CADASTRADO= "O usuário de matrícula %s não está cadastrado no sistema.";

    static final String NENHUM_USUARIO_CADASTRADO= "Nenhum usuário cadastrado.";

    static final String VALOR_NAO_CABIVEL= "Valor menor ou igual a zero nao pode ser adicionado.";


    public static ResponseEntity<CustomErrorType> errorUsuarioJaExiste(Long matricula) {
        return new ResponseEntity<>(new CustomErrorType(String.format(USUARIO_JA_EXISTE, matricula)), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> errorUsuarioNaoCadastrado(String matricula) {
        return new ResponseEntity<>(new CustomErrorType(String.format(USUARIO_NAO_CADASTRADO, matricula)), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> errorNenhumUsuarioCadastrado() {
        return new ResponseEntity<>(new CustomErrorType(String.format(NENHUM_USUARIO_CADASTRADO)), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> errorValorNaoPodeSerAdicionado() {
        return new ResponseEntity<>(new CustomErrorType(String.format(VALOR_NAO_CABIVEL)), HttpStatus.BAD_REQUEST);
    }
}