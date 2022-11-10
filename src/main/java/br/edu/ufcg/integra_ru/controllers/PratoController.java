package br.edu.ufcg.integra_ru.controllers;

import br.edu.ufcg.integra_ru.dtos.PratoDTO;
import br.edu.ufcg.integra_ru.services.PratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/prato")
public class PratoController {

    @Autowired
    private PratoService pratoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PratoDTO> saveDish(@RequestBody @Valid PratoDTO dto){

        dto = pratoService.saveDish(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public List<PratoDTO> getDishes(){
        return pratoService.getDishes();
    }

    @GetMapping("/{id}")
    public PratoDTO getDishById(@PathVariable Long id){
        return pratoService.getDishById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteDish(@PathVariable Long id){
        pratoService.deleteDish(id);
    }

    @PutMapping("/{id}")
    public PratoDTO updateDish(@PathVariable Long id, @RequestBody PratoDTO dto){
        return pratoService.updateDish(id, dto);
    }
}
