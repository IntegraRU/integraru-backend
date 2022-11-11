package br.edu.ufcg.integra_ru.services;

import br.edu.ufcg.integra_ru.dtos.PratoDTO;
import br.edu.ufcg.integra_ru.mapper.PratoMapper;
import br.edu.ufcg.integra_ru.models.ItemPrato;
import br.edu.ufcg.integra_ru.models.Prato;
import br.edu.ufcg.integra_ru.repositories.PratoRepository;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontradoExcecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PratoService {

    @Autowired
    private PratoRepository pratoRepository;
    private PratoMapper pratoMapper = PratoMapper.INSTANCE;

    @Transactional
    public PratoDTO saveDish(PratoDTO dto){
        Prato model = pratoMapper.toModel(dto);
        for(String nomeItem : dto.getItens()){
            ItemPrato itemPrato = new ItemPrato(nomeItem);
            model.addItem(itemPrato);
        }
        return pratoMapper.toDTO(pratoRepository.save(model));
    }

    public List<PratoDTO> getDishes(){
        return this.pratoRepository.findAllWithItens()
                .stream().map(pratoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteDish(Long id){
        try{
            pratoRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException erdae){
            throw new RecursoNaoEncontradoExcecao("Prato com id " + id + " não encontrado!");
        }
    }

    @Transactional
    public PratoDTO updateDish(Long id, PratoDTO dto){
        try {
            Prato found = pratoRepository.getReferenceById(id);
            found.setNome(dto.getNome());
            found.setTipo(dto.getTipo());
            found.clearItens();
            dto.getItens().forEach(i -> found.addItem(new ItemPrato(i)));
            pratoRepository.save(found);
            return  pratoMapper.toDTO(found);
        }
        catch (EntityNotFoundException enfe){
            throw new RecursoNaoEncontradoExcecao("Cardápio com id " + id + " não encontrado!");
        }
    }

    public PratoDTO getDishById(Long id) {
        Prato menu = pratoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Cardápio com o id " + id + " não encontrado!"));
        return pratoMapper.toDTO(menu);
    }
}
