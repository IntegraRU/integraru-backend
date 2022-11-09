package br.edu.ufcg.integra_ru.mapper;


import br.edu.ufcg.integra_ru.dtos.ItemCardapioDTO;
import br.edu.ufcg.integra_ru.models.ItemCardapio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemCardapioMapper {

    ItemCardapioMapper INSTANCE = Mappers.getMapper(ItemCardapioMapper.class);

    ItemCardapioDTO toDTO(ItemCardapio cardapio);
    ItemCardapio toModel(ItemCardapioDTO cardapio);
}
