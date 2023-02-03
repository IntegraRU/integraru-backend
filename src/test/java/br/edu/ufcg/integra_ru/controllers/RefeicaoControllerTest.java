package br.edu.ufcg.integra_ru.controllers;

import br.edu.ufcg.integra_ru.AbstractTestClass;
import br.edu.ufcg.integra_ru.dtos.*;
import br.edu.ufcg.integra_ru.services.PratoService;
import br.edu.ufcg.integra_ru.services.RefeicaoService;
import br.edu.ufcg.integra_ru.services.UsuarioService;
import br.edu.ufcg.integra_ru.util.TestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class RefeicaoControllerTest extends AbstractTestClass {

    protected static final String API = "/api";

    private UsuarioDTO user;

    private PratoDTO prato;

    private RefeicaoDTO refeicao;

    @Autowired
    private RefeicaoService refeicaoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PratoService pratoService;

    @BeforeEach
    public void setup(){

        this.user = TestUtil.makeRandomUser();
        usuarioService.createUser(user);
        this.prato = pratoService.saveDish(TestUtil.makeRandomPrato());

        this.refeicao = TestUtil.makeRefeicaoOf(this.prato, this.user);
    }

    @Test
    void testCadastrarRefeicao() throws Exception {
        this.refeicaoService.deleteRefeicao(this.refeicao.getRefeicaoID());
        String json = TestUtil.convertObjectToString(this.refeicao);

        mockMvc.perform(post(API + "/refeicao")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isOk());

    }

    @Test
    void testCadastrarRefeicaoCadastrada() throws Exception {
        this.refeicao = refeicaoService.cadastrarRefeicao(this.refeicao);
        String json = TestUtil.convertObjectToString(this.refeicao);

        mockMvc.perform(post(API + "/refeicao")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isBadRequest());

        this.refeicaoService.deleteRefeicao(this.refeicao.getRefeicaoID());
    }

    @Test
    void testCadastrarRefeicaoBadUser() throws Exception {
        this.refeicaoService.deleteRefeicao(this.refeicao.getRefeicaoID());
        this.refeicao.setUsuarioMatricula(TestUtil.makeRandomString(9));
        String json = TestUtil.convertObjectToString(this.refeicao);

        mockMvc.perform(post(API + "/refeicao")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isBadRequest());


    }

    @Test
    void testListarRefeicoes() throws Exception{
        mockMvc.perform(get(API + "/refeicoes")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Test
    void testRecuperarRefeicao() throws Exception{
        this.refeicao = refeicaoService.cadastrarRefeicao(this.refeicao);

        mockMvc.perform(get(API + "/refeicao/" + this.refeicao.getRefeicaoID())
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(TestUtil.APPLICATION_JSON_UTF8));

        this.refeicaoService.deleteRefeicao(this.refeicao.getRefeicaoID());
    }

    @Test
    void testRecuperarRefeicaoInexistente() throws Exception{
        mockMvc.perform(get(API + "/refeicao/" + 9999)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAvaliarRefeicaoInexistente() throws Exception {
        AvaliacaoDTO dto = TestUtil.makeRandomAvaliacao();
        String json = TestUtil.convertObjectToString(dto);

        mockMvc.perform(put(API + "/refeicao/" + 9999)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAvaliarAntesDoCheckout() throws Exception {
        this.refeicao = refeicaoService.cadastrarRefeicao(this.refeicao);
        AvaliacaoDTO dto = TestUtil.makeRandomAvaliacao();
        String json = TestUtil.convertObjectToString(dto);

        mockMvc.perform(put(API + "/refeicao/" + this.refeicao.getRefeicaoID())
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isBadRequest());

        this.refeicaoService.deleteRefeicao(this.refeicao.getRefeicaoID());
    }

    @Test
    void testDeleteRefeicaoInexistente() throws Exception {
        mockMvc.perform(delete(API + "/refeicao/" + this.refeicao.getRefeicaoID())
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteRefeicao() throws Exception {
        this.refeicao = refeicaoService.cadastrarRefeicao(this.refeicao);
        mockMvc.perform(delete(API + "/refeicao/" + this.refeicao.getRefeicaoID())
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Test
    void testCheckout() throws Exception {
        this.refeicao = refeicaoService.cadastrarRefeicao(this.refeicao);
        CheckoutDTO dto = TestUtil.makeCheckoutDTOOf(this.refeicao);
        String json = TestUtil.convertObjectToString(dto);

        mockMvc.perform(put(API + "/checkout")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isOk());

        this.refeicaoService.deleteRefeicao(this.refeicao.getRefeicaoID());
    }

    @Test
    void testCheckoutSemRefeicao() throws Exception {
        CheckoutDTO dto = TestUtil.makeCheckoutDTOOf(this.refeicao);
        dto.setRefeicaoID(9999L);
        String json = TestUtil.convertObjectToString(dto);

        mockMvc.perform(put(API + "/checkout")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCheckoutDuplo() throws Exception {
        this.refeicao = refeicaoService.cadastrarRefeicao(this.refeicao);
        CheckoutDTO dto = TestUtil.makeCheckoutDTOOf(this.refeicao);
        refeicaoService.efetuarCheckout(dto);
        String json = TestUtil.convertObjectToString(dto);

        mockMvc.perform(put(API + "/checkout")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isBadRequest());

        this.refeicaoService.deleteRefeicao(this.refeicao.getRefeicaoID());
    }

    @Test
    void testRelatorio() throws Exception{
        this.refeicao = refeicaoService.cadastrarRefeicao(this.refeicao);

        mockMvc.perform(get(API + "/relatorio/" + this.prato.getId())
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        this.refeicaoService.deleteRefeicao(this.refeicao.getRefeicaoID());

    }

}
