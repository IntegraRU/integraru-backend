package br.edu.ufcg.integra_ru.repositories;

import br.edu.ufcg.integra_ru.models.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {

    @Query("select c from Cardapio c join fetch c.itens")
    List<Cardapio> findAllWithItens();
}
