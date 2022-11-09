package br.edu.ufcg.integra_ru.controllers;

import br.edu.ufcg.integra_ru.dtos.CardapioDTO;
import br.edu.ufcg.integra_ru.services.CardapioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cardapios")
public class CardapioController {

    @Autowired
    private CardapioService cardapioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CardapioDTO> saveMenu(@RequestBody CardapioDTO dto){

        dto = cardapioService.saveMenu(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public List<CardapioDTO> getMenu(){
        return cardapioService.getMenu();
    }

    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id){
        cardapioService.deleteMenu(id);
    }
}
