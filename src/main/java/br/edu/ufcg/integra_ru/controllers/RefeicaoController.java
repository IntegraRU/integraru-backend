package br.edu.ufcg.integra_ru.controllers;

import br.edu.ufcg.integra_ru.dtos.AvaliacaoDTO;
import br.edu.ufcg.integra_ru.dtos.RefeicaoDTO;
import br.edu.ufcg.integra_ru.models.Refeicao;
import br.edu.ufcg.integra_ru.services.RefeicaoService;
import br.edu.ufcg.integra_ru.util.ErrorRefeicao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/refeicao")
public class RefeicaoController {

    @Autowired
    private RefeicaoService refeicaoService;

    @PostMapping
    public ResponseEntity<?> cadastrarRefeicao(@RequestBody RefeicaoDTO refeicaoDTO){
        Refeicao refeicao = refeicaoService.cadastrarRefeicao(refeicaoDTO);
        return new ResponseEntity<Refeicao>(refeicao, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarRefeicao(@PathVariable Long id){
        Optional<Refeicao> refeicao = refeicaoService.getRefeicaobyId(id);
        if(refeicao.isEmpty()){
            return new ResponseEntity<>(ErrorRefeicao.errorRefeicaoNaoExiste(id), HttpStatus.BAD_REQUEST);
        }
        refeicaoService.deleteRefeicao(refeicao.get());
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> avaliarRefeicao(@RequestBody AvaliacaoDTO avaliacaoDTO, @PathVariable Long id){
        Optional<Refeicao> refeicao = refeicaoService.getRefeicaobyId(id);
        if(refeicao.isEmpty()){
            return new ResponseEntity<>(ErrorRefeicao.errorRefeicaoNaoExiste(id), HttpStatus.BAD_REQUEST);
        }
        Refeicao result = refeicaoService.avaliarRefeicao(refeicao.get(),
                avaliacaoDTO.getAvaliacaoQuantitativa(), avaliacaoDTO.getAvaliacaoComentario());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> listarRefeicoes(){
        List<Refeicao> refeicoes = refeicaoService.getRefeicoes();
        if(refeicoes.isEmpty()){
            return new ResponseEntity<>(ErrorRefeicao.errorSemRefeicoes(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(refeicoes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRefeicao(@PathVariable Long id){
        Optional<Refeicao> refeicao = refeicaoService.getRefeicaobyId(id);
        if(refeicao.isEmpty()){
            return new ResponseEntity<>(ErrorRefeicao.errorRefeicaoNaoExiste(id), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(refeicao.get(), HttpStatus.OK);
    }

    @GetMapping("/user/{usuario}")
    public ResponseEntity<?> getRefeicoesUsuario(@PathVariable String usuario){
        List<Refeicao> refeicoes = refeicaoService.getRefeicoesByUsuario(usuario);
        if(refeicoes.isEmpty()){
            return new ResponseEntity<>(ErrorRefeicao.errorUsuarioSemRefeicoes(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(refeicoes, HttpStatus.OK);
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<?> getRefeicoesData(@PathVariable("data") @DateTimeFormat(pattern = "dd-MM-yyyy") Date data){
        List<Refeicao> refeicoes = refeicaoService.getRefeicoesByData(data);
        if(refeicoes.isEmpty()){
            return new ResponseEntity<>(ErrorRefeicao.errorDataSemRefeicoes(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(refeicoes, HttpStatus.OK);
    }
}
