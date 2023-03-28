package br.edu.ufcg.integra_ru.services;


import br.edu.ufcg.integra_ru.dtos.MatriculaDTO;
import br.edu.ufcg.integra_ru.models.Matricula;
import br.edu.ufcg.integra_ru.repositories.MatriculaRepository;
import br.edu.ufcg.integra_ru.services.exceptions.BadRequestExcecao;
import br.edu.ufcg.integra_ru.services.exceptions.RecursoNaoEncontradoExcecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    public MatriculaDTO salvarMatricula(MatriculaDTO matriculaDTO){
        if(repository.existsById(matriculaDTO.getMatricula()))
            throw new BadRequestExcecao(String.format("A matrícula %s já está cadastrada no sistema", matriculaDTO.getMatricula()));
        Matricula entity = new Matricula(matriculaDTO.getMatricula(), matriculaDTO.isBeneficiario());
        entity = repository.save(entity);
        return new MatriculaDTO(entity);
    }

    @Transactional
    public MatriculaDTO atualizarMatricula(String matricula, MatriculaDTO matriculaDTO){
        try {
            Matricula entity = repository.getReferenceById(matricula);
            entity.setBeneficiario(matriculaDTO.isBeneficiario());
            usuarioService.atualizarRoleUsuario(entity);
            return new MatriculaDTO(entity);
        }
        catch (EmptyResultDataAccessException erdae){
            throw new RecursoNaoEncontradoExcecao(String.format("Matrícula: %s não encontrada!", matriculaDTO.getMatricula()));
        }
    }
}
