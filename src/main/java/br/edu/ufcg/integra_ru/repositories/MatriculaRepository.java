package br.edu.ufcg.integra_ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufcg.integra_ru.models.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, String>{
    
}
