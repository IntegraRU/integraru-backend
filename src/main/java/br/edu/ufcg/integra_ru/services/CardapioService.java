package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.CardapioDTO;
import br.edu.ufcg.integra_ru.mapper.CardapioMapper;
import br.edu.ufcg.integra_ru.models.Cardapio;
import br.edu.ufcg.integra_ru.repositories.CardapioRepository;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardapioService {

    @Autowired
    private CardapioRepository cardapioRepository;
    private CardapioMapper mapper = CardapioMapper.INSTANCE;

    public CardapioDTO saveMenu(CardapioDTO dto){
        Cardapio model = mapper.toModel(dto);
        return mapper.toDTO(cardapioRepository.save(model));
    }

    public List<CardapioDTO> getMenu(){
        return this.cardapioRepository.findAll()
                .stream().map(mapper::toDTO)
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

    public CardapioDTO updateMenu(Long id, CardapioDTO dto){
        try {
            Cardapio found = cardapioRepository.getReferenceById(id);
            found.setNome(dto.getNome());
            found.setTipo(dto.getTipo());
            found.clearItens();
            dto.getItens().forEach(i -> found.addItem(i));
            return  mapper.toDTO(cardapioRepository.save(found));
        }
        catch (EntityNotFoundException enfe){
            throw new RecursoNaoEncontrado("Cardápio com id " + id + " não encontrado!");
        }
    }

}
