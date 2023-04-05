package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.PatchUserCreditDTO;
import br.edu.ufcg.integra_ru.dtos.PatchUserDTO;
import br.edu.ufcg.integra_ru.dtos.UsuarioDTO;
import br.edu.ufcg.integra_ru.dtos.UsuarioResponseDTO;
import br.edu.ufcg.integra_ru.models.Matricula;
import br.edu.ufcg.integra_ru.models.Role;
import br.edu.ufcg.integra_ru.models.Usuario;
import br.edu.ufcg.integra_ru.repositories.MatriculaRepository;
import br.edu.ufcg.integra_ru.repositories.RoleRepository;
import br.edu.ufcg.integra_ru.repositories.UsuarioRepository;
import br.edu.ufcg.integra_ru.services.exceptions.NaoAutorizadoExcecao;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontradoExcecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                userWantsSave.getTelefone(), userWantsSave.getUrlImagem(), userWantsSave.getCredito(), userWantsSave.isBeneficiario());
    }

    @Transactional
    public void deleteUser(Usuario usuario) {
        userRepository.delete(usuario);
    }

    @Transactional
    public List<UsuarioResponseDTO> listUsers() {
        return userRepository.findAll().stream().map(UsuarioResponseDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public Optional<Usuario> getUserByEnroll(String matricula) {
        return this.userRepository.findById(matricula);
    }
    private void decideUserRole(Matricula matricula, Usuario usuario) throws EmptyResultDataAccessException {
        Role role;
        if (matricula.isBeneficiario()) {
            role = this.roleRepository.getReferenceById("ROLE_BENEFICIARIO");
            usuario.setBeneficiario(true);
        } else {
            role = this.roleRepository.getReferenceById("ROLE_EXTERNO");
            usuario.setBeneficiario(false);
        }
        usuario.setRole(role);
    }

    @Transactional
    public void addCredit(String matricula, PatchUserCreditDTO userDto) {
        try {

            Optional<Usuario> userFound = userRepository.findById(matricula);
            userFound.get().setCredito(userFound.get().getCredito() + userDto.getCredito());
            userRepository.save(userFound.get());
        } catch (NullPointerException npen) {
            throw new RecursoNaoEncontradoExcecao("Usuário com matricula " + matricula + " não encontrado!");
        }

    }

    @Transactional
    public void debitarValor(String matricula, PatchUserCreditDTO userDto) {
        try {
            Optional<Usuario> userFound = userRepository.findById(matricula);
            userFound.get().setCredito(userFound.get().getCredito() - (userDto.getCredito()));
            userRepository.save(userFound.get());
        } catch (NullPointerException npen) {
            throw new RecursoNaoEncontradoExcecao("Usuário com matricula " + matricula + " não encontrado!");
        }
    }

    @Transactional
    public UsuarioResponseDTO updateUser(String matricula, PatchUserDTO userDTO) {
        try {
            if (matricula.equals(getLoggedUser().getMatricula())) {
                Usuario usuario = userRepository.getReferenceById(matricula);
                usuario.setNome(userDTO.getNome());
                usuario.setTelefone(userDTO.getTelefone());
                usuario.setEmail(userDTO.getEmail());

                return new UsuarioResponseDTO(matricula, userDTO.getNome(), userDTO.getEmail(),
                        userDTO.getTelefone(), userDTO.getUrlImagem());
            }
        }
        catch (EntityNotFoundException enfe){
            throw new RecursoNaoEncontradoExcecao("Usuário com matricula " + matricula + " não encontrado!");
        }
        throw new NaoAutorizadoExcecao("Não autorizado a modificar este usuário!");
    }

    private Usuario getLoggedUser(){
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    public void atualizarRoleUsuario(Matricula matricula) throws EmptyResultDataAccessException{
        decideUserRole(matricula, userRepository.getReferenceById(matricula.getValorMatricula()));
    }

    public void devolverCredito(String matricula) {
        Usuario usuario = getUserByEnroll(matricula)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Usuario com a matrícula: " + matricula + " não encontrado"));

        usuario.addCredito(10.0);
    }
}
