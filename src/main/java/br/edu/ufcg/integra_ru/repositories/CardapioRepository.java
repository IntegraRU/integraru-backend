package br.edu.ufcg.integra_ru.repositories;

import br.edu.ufcg.integra_ru.models.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {

    @Query("select distinct c from Cardapio c join fetch c.pratos")
    List<Cardapio> findAllWithPratos();

    @Query("select distinct c from Cardapio c join fetch c.pratos pc where c.id = ?1")
    Cardapio findByIdWithDishes(Long id);

    Cardapio findByDataCardapio(LocalDate date);
}
