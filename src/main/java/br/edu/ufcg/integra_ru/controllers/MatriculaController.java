package br.edu.ufcg.integra_ru.controllers;


import br.edu.ufcg.integra_ru.dtos.MatriculaDTO;
import br.edu.ufcg.integra_ru.models.Matricula;
import br.edu.ufcg.integra_ru.services.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/matricula")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public MatriculaDTO salvarMatricula(@RequestBody @Valid MatriculaDTO dto){
        return matriculaService.salvarMatricula(dto);
    }

    @PatchMapping("/{matricula}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public MatriculaDTO atualizarMatricula(@PathVariable String matricula, @RequestBody @Valid MatriculaDTO dto){
        return matriculaService.atualizarMatricula(matricula, dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public List<Matricula> listarMAtriculas(){
        return matriculaService.listarMatriculas();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public MatriculaDTO deleteMAtricula(@RequestBody @Valid MatriculaDTO dto){
        return matriculaService.deleteMatricula(dto);
    }


}

