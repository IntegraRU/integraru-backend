package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.CheckoutDTO;
import br.edu.ufcg.integra_ru.dtos.RefeicaoDTO;
import br.edu.ufcg.integra_ru.models.Refeicao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RefeicaoService {

    Refeicao cadastrarRefeicao(RefeicaoDTO refeicaoDTO);

    Refeicao saveRefeicao(Refeicao refeicao);

    void deleteRefeicao(Refeicao refeicao);

    Optional<Refeicao> getRefeicaobyId(Long id);

    List<Refeicao> getRefeicoesByUsuario(String usuario);

    List<Refeicao> getRefeicoesByData(Date data);

    List<Refeicao> getRefeicoes();

    Refeicao avaliarRefeicao(Refeicao refeicao, int avaliacaoQuantitativa, String avaliacaoComentario);

    boolean estaReservado(RefeicaoDTO refeicaoDTO);

    Refeicao efetuarCheckout(CheckoutDTO checkoutDTO, Refeicao refeicao);

    BigDecimal getValorRefeicao(Refeicao refeicao);
}
