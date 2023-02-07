package br.edu.ufcg.integra_ru.controllers;

import br.edu.ufcg.integra_ru.AbstractTestClass;
import br.edu.ufcg.integra_ru.dtos.PratoDTO;
import br.edu.ufcg.integra_ru.dtos.RefeicaoDTO;
import br.edu.ufcg.integra_ru.dtos.UsuarioDTO;
import br.edu.ufcg.integra_ru.models.Cardapio;
import br.edu.ufcg.integra_ru.models.ModalidadePrato;
import br.edu.ufcg.integra_ru.models.TipoPrato;
import br.edu.ufcg.integra_ru.repositories.CardapioRepository;
import br.edu.ufcg.integra_ru.repositories.PratoRepository;
import br.edu.ufcg.integra_ru.services.CardapioService;
import br.edu.ufcg.integra_ru.services.PratoService;
import br.edu.ufcg.integra_ru.services.RefeicaoService;
import br.edu.ufcg.integra_ru.services.UsuarioService;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontradoExcecao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PratoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PratoControllerTest {

    protected static final String API = "/api";
    @MockBean
    private PratoService service;
    @Autowired
    private ObjectMapper objMapper;
    @Autowired
    private MockMvc mockMvc;

    private PratoDTO pratoDTO;

    private Long idInexistente;

    private Long idExistente;

    @BeforeEach
    void setUp() {
        pratoDTO = new PratoDTO(1L, TipoPrato.COMUM, ModalidadePrato.ALMOCO, "feijoada", "feijão, linguica, carne de porco", "", null);
        Cardapio cardapio = new Cardapio(1L, LocalDate.now(), Collections.emptyList());

        PratoDTO dtoReturn = new PratoDTO(1L, TipoPrato.COMUM, ModalidadePrato.ALMOCO, "feijoada", "feijão, linguica, carne de porco", "", null);
        pratoDTO.setData(cardapio.getData());

        idInexistente = 10L;
        idExistente = 1L;

        when(service.saveDish(pratoDTO)).thenReturn(dtoReturn);


        doThrow(new RecursoNaoEncontradoExcecao("Prato com id " + idInexistente + " não encontrado!"))
                .when(service).deleteDish(idInexistente);

        doNothing().when(service).deleteDish(idExistente);

        doThrow(new RecursoNaoEncontradoExcecao("Cardápio com o id " + idInexistente + " não encontrado!"))
                .when(service).getDishById(idInexistente);

        when(service.getDishById(idExistente))
                .thenReturn(dtoReturn);
    }

    @Test
    void testCadastrarComSucesso() throws Exception {
        String jsonBody = objMapper.writeValueAsString(pratoDTO);
        String expectedName = pratoDTO.getNome();

        mockMvc.perform(post(API + "/prato")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value(expectedName));
    }

    @Test
    void testBuscaPratoPorIdValido() throws Exception {
        mockMvc.perform(get(API + "/prato/" + idExistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pratoDTO.getId()))
                .andExpect(jsonPath("$.nome").value(pratoDTO.getNome()));
    }

    @Test
    void testBuscaPratoPorIdInvalido() throws Exception {
        mockMvc.perform(get(API + "/prato/" + idInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void getDishByDateAndType() {
    }

    @Test
    void getDishes() {
    }

    @Test
    void testDeletarPratoInexistente() throws Exception {
        mockMvc.perform(delete(API + "/prato/" + idInexistente)
                            .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeletarPratoExistente() throws Exception {
        mockMvc.perform(delete(API + "/prato/" + idExistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateDish() {
    }

    @Test
    void patchDish() {
    }
}