package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.CardapioDTO;
import br.edu.ufcg.integra_ru.mapper.CardapioMapper;
import br.edu.ufcg.integra_ru.models.Cardapio;
import br.edu.ufcg.integra_ru.repositories.CardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        cardapioRepository.deleteById(id);
    }

}
