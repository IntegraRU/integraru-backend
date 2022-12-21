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

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

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

    @Transactional
    public UsuarioResponseDTO createUser(UsuarioDTO usuarioDTO) {

        String encodedPassword = this.passwordEncoder.encode(usuarioDTO.getSenha());
        Usuario userWantsSave = new Usuario(usuarioDTO.getMatricula(), usuarioDTO.getNome(), usuarioDTO.getEmail(),
                usuarioDTO.getTelefone(), usuarioDTO.getUrlImagem(), false, encodedPassword);

        Matricula matricula = this.enrollRepository.findById(usuarioDTO.getMatricula())
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Matricula nao encontrada"));

        decideUserRole(matricula, userWantsSave);

        userWantsSave = this.userRepository.save(userWantsSave);
        return new UsuarioResponseDTO(userWantsSave.getMatricula(), userWantsSave.getNome(), userWantsSave.getEmail(),
                userWantsSave.getTelefone(), userWantsSave.getUrlImagem());
    }

    @Transactional
    public void deleteUser(Usuario usuario) {
        userRepository.delete(usuario);
    }

    @Transactional
    public List<Usuario> listUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public Optional<Usuario> getUserByEnroll(String matricula) {
        return this.userRepository.findById(matricula);
    }

    @Transactional
    private void decideUserRole(Matricula matricula, Usuario usuario) {
        Role role;
        if (matricula.isBeneficiario()) {
            role = this.roleRepository.getReferenceById("BENEFICIARIO");
            usuario.setBeneficiario(true);
        } else {
            role = this.roleRepository.getReferenceById("EXTERNO");
        }
        usuario.setRole(role);
    }

    @Transactional
    public void addCredit(String matricula, UsuarioDTO userDto) {
        try {
            Usuario userFound = userRepository.getReferenceById(matricula);
            userFound.setCredito(userFound.getCredito().add(userDto.getCredito()));
            userRepository.save(userFound);
        } catch (EntityNotFoundException enfe) {
            throw new RecursoNaoEncontradoExcecao("Usuário com matricula " + matricula + " não encontrado!");
        }

    }

    @Transactional
    public void debitarValor(BigDecimal valor) {
        // TODO
    }
}