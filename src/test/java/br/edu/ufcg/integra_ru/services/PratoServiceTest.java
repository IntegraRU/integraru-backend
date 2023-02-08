package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.PratoDTO;
import br.edu.ufcg.integra_ru.mapper.PratoMapper;
import br.edu.ufcg.integra_ru.models.Cardapio;
import br.edu.ufcg.integra_ru.models.ModalidadePrato;
import br.edu.ufcg.integra_ru.models.Prato;
import br.edu.ufcg.integra_ru.models.TipoPrato;
import br.edu.ufcg.integra_ru.repositories.PratoRepository;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontradoExcecao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.core.Is.is;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PratoServiceTest {

    @InjectMocks
    private PratoService service;

    private PratoMapper mapper = PratoMapper.INSTANCE;
    @Mock
    private PratoRepository repository;

    @Mock
    private CardapioService cardapioService;

    private PratoDTO pratoDTO;

    private Prato prato;

    private Prato pratoSalvo;

    private Cardapio mockCardapio;

    private Long idInexistente = 100L;

    private Long idExistente = 1L;


    @BeforeEach
    void setUp() {
        pratoDTO = new PratoDTO();

        prato = new Prato(1L, TipoPrato.COMUM, ModalidadePrato.ALMOCO, "feijoada", "", null, "feijão, linguiça, carne de porco, carne de charque");

        pratoSalvo = new Prato(1L, TipoPrato.COMUM, ModalidadePrato.ALMOCO, "feijoada", "", null, "feijão, linguiça, carne de porco, carne de charque");

        mockCardapio = new Cardapio();
        mockCardapio.setData(LocalDate.now());
        pratoSalvo.setCardapio(mockCardapio);
        prato.setCardapio(mockCardapio);

    }

    @Test
    void testSalvarPrato() {

        when(repository.save(any(Prato.class)))
                .thenReturn(pratoSalvo);

        PratoDTO pratoReq = mapper.toDTO(prato);

        PratoDTO response = service.saveDish(pratoReq);

        assertThat(response, is(pratoReq));
    }

    @Test
    void testDeletarPratoInexistente(){
        doThrow(EmptyResultDataAccessException.class)
                .when(repository).deleteById(idInexistente);

        assertThrows(RecursoNaoEncontradoExcecao.class, () -> service.deleteDish(idInexistente));
    }

    @Test
    void testDeletarPratoExistente(){
        doNothing()
                .when(repository).deleteById(idExistente);

        service.deleteDish(idExistente);
        verify(repository, times(1)).deleteById(eq(idExistente));
    }
}
