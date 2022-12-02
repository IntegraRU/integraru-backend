package br.edu.ufcg.integra_ru.services;


import br.edu.ufcg.integra_ru.dtos.UsuarioDTO;
import br.edu.ufcg.integra_ru.models.Usuario;
import br.edu.ufcg.integra_ru.repositories.MatriculaRepository;
import br.edu.ufcg.integra_ru.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service("Usuario")
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private MatriculaRepository enrollRepository;

    public UsuarioService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Usuario createUser(UsuarioDTO usuarioDTO) {

        Usuario userWantsSave = new Usuario(usuarioDTO.getMatricula(), usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getTelefone(), usuarioDTO.getUrlImagem(), false);

        if(this.enrollRepository.existsById(usuarioDTO.getMatricula())) {
            userWantsSave.setBeneficiario(true);
        }
        userWantsSave = this.userRepository.save(userWantsSave);
        return userWantsSave;
    }

    public void deleteUser(Usuario usuario) {
        userRepository.delete(usuario);
    }

    public List<Usuario> listUsers() {
        return userRepository.findAll();
    }

    public Optional<Usuario> getUserByEnroll(String matricula) {
        return this.userRepository.findById(matricula);
    }
  
}