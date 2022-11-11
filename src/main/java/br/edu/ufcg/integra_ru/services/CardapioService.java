package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.CardapioDTO;
import br.edu.ufcg.integra_ru.dtos.PratoDTO;
import br.edu.ufcg.integra_ru.mapper.CardapioMapper;
import br.edu.ufcg.integra_ru.models.Cardapio;
import br.edu.ufcg.integra_ru.models.Prato;
import br.edu.ufcg.integra_ru.repositories.CardapioRepository;
import br.edu.ufcg.integra_ru.repositories.PratoRepository;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontradoExcecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardapioService {

    @Autowired
    private CardapioRepository repository;

    @Autowired
    private PratoRepository pratoRepository;

    private CardapioMapper mapper = CardapioMapper.INSTANCE;

    @Transactional
    public CardapioDTO saveMenu(CardapioDTO cardapioDTO){
        try {
            Cardapio toBeSaved = mapper.toModel(cardapioDTO);
            for (PratoDTO p : cardapioDTO.getPratos()) {
                Prato dish = pratoRepository.getReferenceById(p.getId());
                toBeSaved.addDish(dish);
            }
            return mapper.toDTO(repository.save(toBeSaved));
        }
        catch (EntityNotFoundException enfe){
            throw new RecursoNaoEncontradoExcecao("Algum(ns) pratos não foram encontrados no sistema!");
        }
    }

    @Transactional(readOnly = true)
    public List<CardapioDTO> getMenu(){
        return repository.findAllWithPratos().stream().map(mapper::toDTO).collect(Collectors.toList());
    }


}
