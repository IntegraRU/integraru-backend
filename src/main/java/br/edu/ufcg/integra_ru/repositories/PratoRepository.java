package br.edu.ufcg.integra_ru.repositories;

import br.edu.ufcg.integra_ru.models.Prato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PratoRepository extends JpaRepository<Prato, Long> {

    @Query("select c from Prato c join fetch c.itens")
    List<Prato> findAllWithItens();
}
