package br.edu.ufcg.integra_ru.repositories;


import br.edu.ufcg.integra_ru.models.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {
    List<Refeicao> findByUsuario(String usuario);

    List<Refeicao> findByData(Date data);
}
