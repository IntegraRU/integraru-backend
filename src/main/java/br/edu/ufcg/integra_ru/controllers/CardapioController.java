package br.edu.ufcg.integra_ru.controllers;

import br.edu.ufcg.integra_ru.dtos.CardapioDTO;
import br.edu.ufcg.integra_ru.services.CardapioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cardapio")
public class CardapioController {

    @Autowired
    private CardapioService cardapioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardapioDTO saveMenu(@RequestBody CardapioDTO cardapioDTO){
        return cardapioService.saveMenu(cardapioDTO);
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
