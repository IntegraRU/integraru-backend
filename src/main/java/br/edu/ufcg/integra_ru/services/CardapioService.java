package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.CardapioDTO;
import br.edu.ufcg.integra_ru.mapper.CardapioMapper;
import br.edu.ufcg.integra_ru.models.Cardapio;
import br.edu.ufcg.integra_ru.models.ItemCardapio;
import br.edu.ufcg.integra_ru.repositories.CardapioRepository;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontradoExcecao;
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

    @Transactional
    public CardapioDTO saveMenu(CardapioDTO dto){
        Cardapio model = cardapioMapper.toModel(dto);
        for(String nomeItem : dto.getItens()){
            ItemCardapio itemCardapio = new ItemCardapio(nomeItem);
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
            throw new RecursoNaoEncontradoExcecao("Cardápio com id " + id + " não encontrado!");
        }
    }

    @Transactional
    public CardapioDTO updateMenu(Long id, CardapioDTO dto){
        try {
            Cardapio found = cardapioRepository.getReferenceById(id);
            found.setNome(dto.getNome());
            found.setTipo(dto.getTipo());
            found.clearItens();
            dto.getItens().forEach(i -> found.addItem(new ItemCardapio(i)));
            cardapioRepository.save(found);
            return  cardapioMapper.toDTO(found);
        }
        catch (EntityNotFoundException enfe){
            throw new RecursoNaoEncontradoExcecao("Cardápio com id " + id + " não encontrado!");
        }
    }

    public CardapioDTO getMenuById(Long id) {
        Cardapio menu = cardapioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Cardápio com o id " + id + " não encontrado!"));
        return cardapioMapper.toDTO(menu);
    }
}
