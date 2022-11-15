package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.PratoDTO;
import br.edu.ufcg.integra_ru.mapper.PratoMapper;
import br.edu.ufcg.integra_ru.models.Cardapio;
import br.edu.ufcg.integra_ru.models.Prato;
import br.edu.ufcg.integra_ru.models.pk.PratoCardapioId;
import br.edu.ufcg.integra_ru.repositories.CardapioRepository;
import br.edu.ufcg.integra_ru.repositories.PratoRepository;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontradoExcecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PratoService {

    @Autowired
    private CardapioService cardapioService;

    @Autowired
    private PratoRepository pratoRepository;
    private PratoMapper pratoMapper = PratoMapper.INSTANCE;

    @Transactional
    public PratoDTO saveDish(PratoDTO dto){
        Cardapio cardapio = cardapioService.getByDate(LocalDate.now());
        if(cardapio == null){
            cardapio = cardapioService.createMenu();
        }
        Prato model = pratoMapper.toModel(dto);
        model.setCardapio(cardapio);
        return pratoMapper.toDTO(pratoRepository.save(model));
    }
    @Transactional
    public void deleteDish(Long pratoId){
        try{
            pratoRepository.deleteById(pratoId);
        }
        catch (EmptyResultDataAccessException erdae){
            throw new RecursoNaoEncontradoExcecao("Prato com id " + pratoId + " não encontrado!");
        }
    }

    @Transactional
    public PratoDTO updateDish(Long id, PratoDTO dto){
        try {
            Prato found = pratoRepository.getReferenceById(id);
            found.setNome(dto.getNome());
            found.setTipo(dto.getTipo());
            found.setItens(dto.getItens());
            pratoRepository.save(found);
            return  pratoMapper.toDTO(found);
        }
        catch (EntityNotFoundException enfe){
            throw new RecursoNaoEncontradoExcecao("Cardápio com id " + id + " não encontrado!");
        }
    }

    @Transactional
    public PratoDTO patchDish(Long id, PratoDTO dto) {
        Prato dish = pratoRepository.getReferenceById(id);
        pratoMapper.updatePratoFromDto(dto, dish);
        return pratoMapper.toDTO(pratoRepository.save(dish));
    }

    public PratoDTO getDishById(Long id) {
        Prato menu = pratoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Cardápio com o id " + id + " não encontrado!"));
        return pratoMapper.toDTO(menu);
    }

    public List<PratoDTO> getDishByDate(LocalDate date) {
        return pratoRepository.findByCardapioDataCardapio(date).stream().map(pratoMapper::toDTO).collect(Collectors.toList());
    }
}
