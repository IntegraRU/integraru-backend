package br.edu.ufcg.integra_ru.models;

import br.edu.ufcg.integra_ru.models.pk.PratoCardapioId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"cardapio", "prato"})
public class PratoCardapio {

    @EmbeddedId
    private PratoCardapioId id = new PratoCardapioId();

    @MapsId("cardapioId")
    @ManyToOne
    @JoinColumn(name = "cardapio_id")
    private Cardapio cardapio;

    @MapsId("pratoId")
    @ManyToOne
    @JoinColumn(name = "prato_id")
    private Prato prato;

    public PratoCardapio(Cardapio cardapio, Prato prato) {
        this.cardapio = cardapio;
        this.prato = prato;
    }
}
