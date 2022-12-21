package br.edu.ufcg.integra_ru.services;


import br.edu.ufcg.integra_ru.dtos.UsuarioDTO;
import br.edu.ufcg.integra_ru.dtos.UsuarioResponseDTO;
import br.edu.ufcg.integra_ru.models.Matricula;
import br.edu.ufcg.integra_ru.models.Role;
import br.edu.ufcg.integra_ru.models.Usuario;
import br.edu.ufcg.integra_ru.repositories.MatriculaRepository;
import br.edu.ufcg.integra_ru.repositories.RoleRepository;
import br.edu.ufcg.integra_ru.repositories.UsuarioRepository;

import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontradoExcecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MatriculaRepository enrollRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UsuarioService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UsuarioResponseDTO createUser(UsuarioDTO usuarioDTO) {

        String encodedPassword = this.passwordEncoder.encode(usuarioDTO.getSenha());
        Usuario userWantsSave = new Usuario(usuarioDTO.getMatricula(), usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getTelefone(), usuarioDTO.getUrlImagem(), false, encodedPassword);

        Matricula matricula = this.enrollRepository.findById(usuarioDTO.getMatricula())
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Matricula nao encontrada"));

        decideUserRole(matricula, userWantsSave);

        userWantsSave = this.userRepository.save(userWantsSave);
        return new UsuarioResponseDTO(userWantsSave.getMatricula(), userWantsSave.getNome(), userWantsSave.getEmail(), userWantsSave.getTelefone(), userWantsSave.getUrlImagem());
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

    private void decideUserRole(Matricula matricula, Usuario usuario){
        Role role;
        if(matricula.isBeneficiario()){
            role = this.roleRepository.getReferenceById("BENEFICIARIO");
            usuario.setBeneficiario(true);
        }
        else{
            role = this.roleRepository.getReferenceById("EXTERNO");
        }
        usuario.setRole(role);
    }

    public void debitarValor(BigDecimal valor) {
        //TODO
    }
}