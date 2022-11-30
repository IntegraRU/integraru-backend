package br.edu.ufcg.integra_ru.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserError {

    static final String USUARIO_JA_EXISTE = "O usuario de matricula %s ja está cadastrado no sistema.";

    static final String USUARIO_NAO_CADASTRADO= "O usuario de matricula %s não está cadastrado no sistema.";

    static final String NENHUM_USUARIO_CADASTRADO= "Nenhum usuario cadastrado.";


    public static ResponseEntity<CustomErrorType> errorUsuarioJaExiste(Long matricula) {
        return new ResponseEntity<>(new CustomErrorType(String.format(USUARIO_JA_EXISTE, matricula)), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> errorUsuarioNaoCadastrado(String matricula) {
        return new ResponseEntity<>(new CustomErrorType(String.format(USUARIO_NAO_CADASTRADO)), HttpStatus.BAD_REQUEST);
    }


    public static ResponseEntity<CustomErrorType> errorNenhumUsuarioCadastrado() {
        return new ResponseEntity<>(new CustomErrorType(String.format(NENHUM_USUARIO_CADASTRADO)), HttpStatus.BAD_REQUEST);
    }
}