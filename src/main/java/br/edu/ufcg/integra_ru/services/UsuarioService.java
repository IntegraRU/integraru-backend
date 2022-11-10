package br.edu.ufcg.integra_ru.services;


import br.edu.ufcg.integra_ru.dtos.UsuarioDTO;
import br.edu.ufcg.integra_ru.models.Usuario;
import br.edu.ufcg.integra_ru.repositories.UsuarioRepository;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontrado;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("Usuarios")
public class UsuarioService {

    private UsuarioRepository userRepository;

    public UsuarioService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UsuarioDTO saveUser(UsuarioDTO usuarioDTO) {
        Usuario userWantsSave = new Usuario(usuarioDTO.getMatricula(), usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getTelefone());


        return usuarioDTO;
    }

    public UsuarioDTO getUserByEnroll(Long matricula) {
        UsuarioDTO user = userRepository.findById(matricula)
                .orElseThrow(() -> new RecursoNaoEncontrado("Cardápio com o id " + matricula + " não encontrado!"));
        return user;
    }


    public List<UsuarioDTO> getUsers() {

        return null;
    }

    public boolean deleteUserByEnroll(Long matricula) {
        return false;
    }

    public UsuarioDTO updateUser(Long matricula, UsuarioDTO dto) {

        return dto;
    }
}
