package br.edu.ufcg.integra_ru.services;


import br.edu.ufcg.integra_ru.dtos.UsuarioDTO;
import br.edu.ufcg.integra_ru.models.Usuario;
import br.edu.ufcg.integra_ru.repositories.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service("Usuarios")
public class UsuarioService {

    private UsuarioRepository userRepository;

    public UsuarioService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Usuario saveUser(UsuarioDTO usuarioDTO) {
        Usuario userWantsSave = new Usuario(usuarioDTO.getMatricula(), usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getTelefone(), usuarioDTO.getUrlImagem());

        return userRepository.save(userWantsSave);
    }

    public void deleteUser(Usuario usuario) {
        
        userRepository.delete(usuario);
    }

    public Optional<Usuario> getUserByEnroll(Long matricula) {

        return userRepository.findByEnroll(matricula);
    }
    
    public List<Usuario> getUsers() {
        return userRepository.findAll();
    }
  
}