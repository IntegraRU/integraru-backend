package br.edu.ufcg.integra_ru.services;


import br.edu.ufcg.integra_ru.dtos.UsuarioDTO;
import br.edu.ufcg.integra_ru.models.Usuario;
import br.edu.ufcg.integra_ru.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;


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


}
