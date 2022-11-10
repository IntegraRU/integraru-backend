package br.edu.ufcg.integra_ru.repositories;

import br.edu.ufcg.integra_ru.models.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {

    @Query("select distinct c from Cardapio c join fetch c.pratos cp join fetch cp.prato p join fetch p.itens")
    List<Cardapio> findAllWithPratos();
}
