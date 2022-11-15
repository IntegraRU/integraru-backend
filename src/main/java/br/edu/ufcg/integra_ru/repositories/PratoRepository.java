package br.edu.ufcg.integra_ru.repositories;

import br.edu.ufcg.integra_ru.models.Prato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PratoRepository extends JpaRepository<Prato, Long> {

    Prato findByCardapioIdAndId(Long cardapioId, Long id);

    void deleteByCardapioIdAndId(Long cardapioId, Long id);

    Prato getReferenceByCardapioIdAndId(Long cardapioId, Long id);

    List<Prato> findByCardapioDataCardapio(LocalDate date);

}
