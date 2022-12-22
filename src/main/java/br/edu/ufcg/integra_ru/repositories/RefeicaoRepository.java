package br.edu.ufcg.integra_ru.repositories;

import br.edu.ufcg.integra_ru.models.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {

    List<Refeicao> findByMatriculaUser(String usuarioMatricula);

    List<Refeicao> findByPratoID(Long pratoID);

}
