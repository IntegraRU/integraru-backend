package br.edu.ufcg.integra_ru.services.exceptions;

public class NaoAutorizadoExcecao extends RuntimeException{
    public NaoAutorizadoExcecao(String msg) {
        super(msg);
    }
}
