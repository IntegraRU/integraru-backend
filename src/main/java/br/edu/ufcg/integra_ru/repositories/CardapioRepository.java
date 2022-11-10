package br.edu.ufcg.integra_ru.repositories;

import br.edu.ufcg.integra_ru.models.Cardapio;
import br.edu.ufcg.integra_ru.models.Prato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {

    @Query("select c from Cardapio c left join fetch c.pratos")
    List<Cardapio> findAllWithPratos();
}
