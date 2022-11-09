package br.edu.ufcg.integra_ru.mapper;

import br.edu.ufcg.integra_ru.dtos.CardapioDTO;
import br.edu.ufcg.integra_ru.models.Cardapio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardapioMapper {

    CardapioMapper INSTANCE = Mappers.getMapper(CardapioMapper.class);

    CardapioDTO toDTO(Cardapio cardapio);
    Cardapio toModel(CardapioDTO cardapio);

}
