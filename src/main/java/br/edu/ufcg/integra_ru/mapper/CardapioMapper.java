package br.edu.ufcg.integra_ru.mapper;

import br.edu.ufcg.integra_ru.dtos.CardapioDTO;
import br.edu.ufcg.integra_ru.dtos.PratoDTO;
import br.edu.ufcg.integra_ru.models.Cardapio;
import br.edu.ufcg.integra_ru.models.Prato;
import br.edu.ufcg.integra_ru.models.PratoCardapio;
import br.edu.ufcg.integra_ru.models.pk.PratoCardapioId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(uses = {PratoMapper.class})
public interface CardapioMapper {

    CardapioMapper INSTANCE = Mappers.getMapper(CardapioMapper.class);

    @Mapping(target = "pratos", ignore = true)
    Cardapio toModel(CardapioDTO cardapioDTO);

    CardapioDTO toDTO(Cardapio cardapio);

    default Long map(PratoCardapioId id){
        return id.getPratoId();
    }



}
