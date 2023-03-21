package br.edu.ufcg.integra_ru.controllers;

import br.edu.ufcg.integra_ru.dtos.PratoDTO;
import br.edu.ufcg.integra_ru.models.ModalidadePrato;
import br.edu.ufcg.integra_ru.services.PratoService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api")
public class PratoController {

    @Autowired
    private PratoService pratoService;

    @PostMapping("/prato")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PratoDTO> saveDish(@RequestBody @Valid PratoDTO dto){

        dto = pratoService.saveDish(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping(value = "/prato/{id}")
    public PratoDTO getDishById(@PathVariable(value = "id") Long id){
        return pratoService.getDishById(id);
    }

    @GetMapping("/pratos")
    public List<PratoDTO> getDishByDateAndType(@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date,
                                        @RequestParam(value = "type", required = false) ModalidadePrato type){
        if (date == null) {
            return pratoService.getDishes();
        }
        return pratoService.getDishByDateAndType(date, type);
    }

    @GetMapping("/prato")
    public List<PratoDTO> getDishes(){
        return pratoService.getDishes();
    }

    @DeleteMapping("/prato/{pratoId}")
    public void deleteDish(@PathVariable Long pratoId){
        pratoService.deleteDish(pratoId);
    }

    @PutMapping("/prato/{id}")
    public PratoDTO updateDish(@PathVariable Long id, @RequestBody @Valid PratoDTO dto){
        return pratoService.updateDish(id, dto);
    }

    @PatchMapping("/prato/{pratoId}")
    public PratoDTO patchDish(@PathVariable Long pratoId, @RequestBody PratoDTO pratoDTO) throws InvocationTargetException, IllegalAccessException {
        return pratoService.patchDish(pratoId, pratoDTO);
    }
}
