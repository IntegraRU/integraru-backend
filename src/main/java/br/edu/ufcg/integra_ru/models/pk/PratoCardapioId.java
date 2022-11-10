package br.edu.ufcg.integra_ru.models.pk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"pratoId", "cardapioId"})
public class PratoCardapioId implements Serializable {

    @JoinColumn(name = "prato_id")
    private Long pratoId;

    @JoinColumn(name = "cardapio_id")
    private Long cardapioId;
}
