package br.edu.ufcg.integra_ru.mapper;

import br.edu.ufcg.integra_ru.dtos.CardapioDTO;
import br.edu.ufcg.integra_ru.models.Cardapio;
import br.edu.ufcg.integra_ru.models.ItemCardapio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardapioMapper {

    CardapioMapper INSTANCE = Mappers.getMapper(CardapioMapper.class);

    CardapioDTO toDTO(Cardapio cardapio);

    default String mapItensToString(ItemCardapio child) {
        return child.getNome();
    }

    @Mapping(target = "itens", ignore = true)
    Cardapio toModel(CardapioDTO cardapio);

}
