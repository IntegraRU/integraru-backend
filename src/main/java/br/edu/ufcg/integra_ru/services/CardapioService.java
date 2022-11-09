package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.CardapioDTO;
import br.edu.ufcg.integra_ru.dtos.ItemCardapioDTO;
import br.edu.ufcg.integra_ru.mapper.CardapioMapper;
import br.edu.ufcg.integra_ru.mapper.ItemCardapioMapper;
import br.edu.ufcg.integra_ru.models.Cardapio;
import br.edu.ufcg.integra_ru.models.ItemCardapio;
import br.edu.ufcg.integra_ru.repositories.CardapioRepository;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardapioService {

    @Autowired
    private CardapioRepository cardapioRepository;
    private CardapioMapper cardapioMapper = CardapioMapper.INSTANCE;
    private ItemCardapioMapper itemCardapioMapper = ItemCardapioMapper.INSTANCE;

    @Transactional
    public CardapioDTO saveMenu(CardapioDTO dto){
        Cardapio model = cardapioMapper.toModel(dto);
        for(ItemCardapioDTO icd : dto.getItens()){
            ItemCardapio itemCardapio = itemCardapioMapper.toModel(icd);
            model.addItem(itemCardapio);
        }
        return cardapioMapper.toDTO(cardapioRepository.save(model));
    }

    public List<CardapioDTO> getMenu(){
        return this.cardapioRepository.findAllWithItens()
                .stream().map(cardapioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteMenu(Long id){
        try{
            cardapioRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException erdae){
            throw new RecursoNaoEncontrado("Cardápio com id " + id + " não encontrado!");
        }
    }

    @Transactional
    public CardapioDTO updateMenu(Long id, CardapioDTO dto){
        try {
            Cardapio found = cardapioRepository.getReferenceById(id);
            found.setNome(dto.getNome());
            found.setTipo(dto.getTipo());
            found.clearItens();
            dto.getItens().forEach(i -> found.addItem(itemCardapioMapper.toModel(i)));
            cardapioRepository.save(found);
            return  cardapioMapper.toDTO(found);
        }
        catch (EntityNotFoundException enfe){
            throw new RecursoNaoEncontrado("Cardápio com id " + id + " não encontrado!");
        }
    }

}
