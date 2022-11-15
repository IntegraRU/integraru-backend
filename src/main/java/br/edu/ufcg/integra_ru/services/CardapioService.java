package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.CardapioDTO;
import br.edu.ufcg.integra_ru.mapper.CardapioMapper;
import br.edu.ufcg.integra_ru.mapper.PratoMapper;
import br.edu.ufcg.integra_ru.models.Cardapio;
import br.edu.ufcg.integra_ru.repositories.CardapioRepository;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontradoExcecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardapioService {

    @Autowired
    private CardapioRepository repository;

    private CardapioMapper mapper = CardapioMapper.INSTANCE;

    private PratoMapper pratoMapper = PratoMapper.INSTANCE;

    @Transactional
    public CardapioDTO saveMenu(CardapioDTO cardapioDTO){
        try {
            Cardapio newMenu = mapper.toModel(cardapioDTO);
            return mapper.toDTO(repository.save(newMenu));
        }
        catch (EntityNotFoundException enfe){
            throw new RecursoNaoEncontradoExcecao("Algum(ns) pratos não foram encontrados no sistema!");
        }
    }

    @Transactional(readOnly = true)
    public List<CardapioDTO> getMenu(){
        return repository.findAllWithPratos().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public void deleteMenu(Long id){
        try{
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException erdae){
            throw new RecursoNaoEncontradoExcecao("Cardápio com id " + id + " não encontrado!");
        }
    }

    public Cardapio getByDate(LocalDate date){
        return repository.findByDataCardapio(date);
    }


    @Transactional
    public CardapioDTO updateMenu(Long id, CardapioDTO cardapioDTO) {
        try {
            Cardapio menu = repository.findByIdWithDishes(id);
            menu.getPratos().clear();
            mapper.updateCardapioFromDto(cardapioDTO, menu);
            menu = repository.save(menu);
            return mapper.toDTO(menu);
        }
        catch (EntityNotFoundException enfe){
            throw new RecursoNaoEncontradoExcecao(enfe.getMessage());
        }
    }

    public Cardapio createMenu() {
        Cardapio menu = new Cardapio();
        menu.setDataCardapio(LocalDate.now());
        return menu;
    }
}
