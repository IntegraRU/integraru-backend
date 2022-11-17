package br.edu.ufcg.integra_ru.controllers;

import br.edu.ufcg.integra_ru.dtos.PratoDTO;
import br.edu.ufcg.integra_ru.services.PratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.time.LocalDate;
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

    @GetMapping(value = "/{id}")
    public PratoDTO getDishById(@PathVariable(value = "id") Long id){
        return pratoService.getDishById(id);
    }

    @GetMapping
    public List<PratoDTO> getDishByDate(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return pratoService.getDishByDate(date);
    }

    @DeleteMapping("/{pratoId}")
    public void deleteDish(@PathVariable Long pratoId){
        pratoService.deleteDish(pratoId);
    }

    @PutMapping("/{id}")
    public PratoDTO updateDish(@PathVariable Long id, @RequestBody @Valid PratoDTO dto){
        return pratoService.updateDish(id, dto);
    }

    @PatchMapping("/{pratoId}")
    public PratoDTO patchDish(@PathVariable Long pratoId, @RequestBody PratoDTO pratoDTO) throws InvocationTargetException, IllegalAccessException {
        return pratoService.patchDish(pratoId, pratoDTO);
    }
}
