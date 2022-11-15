package br.edu.ufcg.integra_ru.repositories;

import br.edu.ufcg.integra_ru.dtos.UsuarioDTO;
import br.edu.ufcg.integra_ru.models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<UsuarioDTO> saveUser();

    Usuario save(Usuario userWantsSave);

    Optional<Usuario> findByEnroll(Long matricula);

    void delete(Usuario usuario);


}
