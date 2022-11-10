package br.edu.ufcg.integra_ru.repositories;

import br.edu.ufcg.integra_ru.dtos.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioDTO, Long> {

    List<UsuarioDTO> saveUser();

    
}
