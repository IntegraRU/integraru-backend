package br.edu.ufcg.integra_ru.mapper;

import br.edu.ufcg.integra_ru.dtos.PratoDTO;
import br.edu.ufcg.integra_ru.models.Prato;
import br.edu.ufcg.integra_ru.models.pk.PratoCardapioId;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PratoMapper {

    PratoMapper INSTANCE = Mappers.getMapper(PratoMapper.class);

    PratoDTO toDTO(Prato pratoCardapio);

    Prato toModel(PratoDTO pratoDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePratoFromDto(PratoDTO dto, @MappingTarget Prato entity);

}
