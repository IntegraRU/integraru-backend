package br.edu.ufcg.integra_ru.models;

import java.math.BigDecimal;

public enum ModalidadePrato {
    ALMOCO, CAFE, JANTAR;

    private static final double VALOR_ALMOCO = 10;

    private static final double VALOR_CAFE = 5;

    private static final double VALOR_JANTAR = 10;

    public BigDecimal getValue() {
        return switch (this) {
            case ALMOCO -> BigDecimal.valueOf(VALOR_ALMOCO);
            case CAFE -> BigDecimal.valueOf(VALOR_CAFE);
            case JANTAR -> BigDecimal.valueOf(VALOR_JANTAR);
        };
    }
}
