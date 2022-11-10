package br.edu.ufcg.integra_ru.mapper;

import br.edu.ufcg.integra_ru.dtos.PratoDTO;
import br.edu.ufcg.integra_ru.models.Prato;
import br.edu.ufcg.integra_ru.models.ItemPrato;
import br.edu.ufcg.integra_ru.models.PratoCardapio;
import br.edu.ufcg.integra_ru.models.pk.PratoCardapioId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PratoMapper {

    PratoMapper INSTANCE = Mappers.getMapper(PratoMapper.class);

    PratoDTO toDTO(Prato prato);

    @Mapping(source = "prato.nome", target = "nome")
    @Mapping(source = "prato.tipo", target = "tipo")
    @Mapping(source = "prato.urlImagem", target = "urlImagem")
    @Mapping(source = "prato.itens", target = "itens")
    PratoDTO toDTO(PratoCardapio pratoCardapio);

    default String mapItensToString(ItemPrato child) {
        return child.getNome();
    }

    default PratoCardapioId mapPratoCardapioIdToPratoId(Long pratoId){
        PratoCardapioId id = new PratoCardapioId();
        id.setPratoId(pratoId);
        return id;
    }

    @Mapping(target = "itens", ignore = true)
    Prato toModel(PratoDTO cardapio);

    default Long map(PratoCardapioId id){
        return id.getPratoId();
    }

}
