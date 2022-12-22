package br.edu.ufcg.integra_ru.repositories;

import br.edu.ufcg.integra_ru.models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

}
