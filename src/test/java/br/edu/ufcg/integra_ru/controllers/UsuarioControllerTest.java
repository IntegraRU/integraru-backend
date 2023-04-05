package br.edu.ufcg.integra_ru.controllers;

import br.edu.ufcg.integra_ru.AbstractTestClass;
import br.edu.ufcg.integra_ru.dtos.PatchUserCreditDTO;
import br.edu.ufcg.integra_ru.dtos.PatchUserDTO;
import br.edu.ufcg.integra_ru.dtos.UsuarioDTO;
import br.edu.ufcg.integra_ru.dtos.UsuarioResponseDTO;
import br.edu.ufcg.integra_ru.models.Matricula;
import br.edu.ufcg.integra_ru.models.Usuario;
import br.edu.ufcg.integra_ru.repositories.MatriculaRepository;
import br.edu.ufcg.integra_ru.services.UsuarioService;
import br.edu.ufcg.integra_ru.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UsuarioControllerTest extends AbstractTestClass {

    protected static final String API = "/api/user";

    private UsuarioDTO user;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MatriculaRepository enrollRepository;

    @BeforeEach
    public void setup(){
        this.user = TestUtil.makeRandomUser();
        List<Matricula> matriculas = this.enrollRepository.findAll();
        if(matriculas.size() > 0){
            this.user.setMatricula(matriculas.get(0).getValorMatricula());
        }
    }

    @Test
    void testCadastrarUsuario() throws Exception {

        String json = TestUtil.convertObjectToString(this.user);

        mockMvc.perform(post(API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());

    }

    @Test
    void testDeletarUsuarioInexistente() throws Exception{
        mockMvc.perform(delete(API + "/000")
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeletarUsuario() throws Exception{
        UsuarioResponseDTO newUser = usuarioService.createUser(this.user);

        mockMvc.perform(delete(API + "/" + newUser.getMatricula())
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    void testListarUsuarios() throws Exception{
        mockMvc.perform(get(API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Test
    void testListarUsuariosSemUsuarios() throws Exception{
        List<UsuarioResponseDTO> users = usuarioService.listUsers();
        for(UsuarioResponseDTO u: users){
            usuarioService.deleteUser(new Usuario(u.getMatricula(), u.getNome(), u.getEmail(), u.getTelefone(), u.getUrlImagem()));
        }

        mockMvc.perform(get(API)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRecuperarUsuarioInexistente() throws Exception{
        mockMvc.perform(get(API + "/000")
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRecuperarUsuario() throws Exception{
        usuarioService.createUser(this.user);

        mockMvc.perform(get(API + "/" + this.user.getMatricula())
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(TestUtil.APPLICATION_JSON_UTF8));

    }

    @Test
    void testUpdateCreditUsuarioInexistente() throws Exception{
        PatchUserCreditDTO dto = TestUtil.makeRandomPatchUserCredit();
        String json = TestUtil.convertObjectToString(dto);

        mockMvc.perform(patch(API + "/000/add-credit")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateCreditUsuario() throws Exception{
        usuarioService.createUser(this.user);
        PatchUserCreditDTO dto = TestUtil.makeRandomPatchUserCredit();
        String json = TestUtil.convertObjectToString(dto);

        mockMvc.perform(patch(API + "/" + this.user.getMatricula() + "/add-credit")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testDelCreditUsuarioInexistente() throws Exception{
        PatchUserCreditDTO dto = TestUtil.makeRandomPatchUserCredit();
        String json = TestUtil.convertObjectToString(dto);

        mockMvc.perform(patch(API + "/000/del-credit")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDelCreditUsuarioInsuficiente() throws Exception{
        usuarioService.createUser(this.user);
        PatchUserCreditDTO dto = TestUtil.makeRandomPatchUserCredit();
        dto.setCredito(10000.0);
        String json = TestUtil.convertObjectToString(dto);

        mockMvc.perform(patch(API + "/" + this.user.getMatricula() + "/del-credit")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDelCreditUsuario() throws Exception{
        usuarioService.createUser(this.user);
        PatchUserCreditDTO dto = TestUtil.makeRandomPatchUserCredit();
        usuarioService.addCredit(this.user.getMatricula(), dto);
        String json = TestUtil.convertObjectToString(dto);

        mockMvc.perform(patch(API + "/" + this.user.getMatricula() + "/del-credit")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());
    }
}
