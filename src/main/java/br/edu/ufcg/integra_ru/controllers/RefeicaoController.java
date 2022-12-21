package br.edu.ufcg.integra_ru.controllers;


import br.edu.ufcg.integra_ru.dtos.AvaliacaoDTO;
import br.edu.ufcg.integra_ru.dtos.RefeicaoDTO;
import br.edu.ufcg.integra_ru.models.Usuario;
import br.edu.ufcg.integra_ru.services.RefeicaoService;
import br.edu.ufcg.integra_ru.services.UsuarioService;
import br.edu.ufcg.integra_ru.util.RefeicaoError;
import br.edu.ufcg.integra_ru.util.UserError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RefeicaoController {

    @Autowired
    RefeicaoService refeicaoService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/refeicao")
    public ResponseEntity<?> cadastrarRefeicao(@RequestBody RefeicaoDTO refeicaoDTO){
        Optional<Usuario> usuarioOptional = usuarioService.getUserByEnroll(refeicaoDTO.getUsuarioMatricula());
        if(usuarioOptional.isEmpty()){
            return UserError.errorUsuarioNaoCadastrado(refeicaoDTO.getUsuarioMatricula());
        }
        if(refeicaoService.refeicaoExiste(refeicaoDTO)){
            return RefeicaoError.errorRefeicaoJaCadastrada(refeicaoDTO.getUsuarioMatricula(), refeicaoDTO.getDataReserva());
        }
        return new ResponseEntity<>(refeicaoService.cadastrarRefeicao(refeicaoDTO), HttpStatus.OK);
    }

    @GetMapping("/refeicoes")
    public ResponseEntity<?> listarRefeicoes(){
        List<RefeicaoDTO> refeicoes = refeicaoService.listarRefeicoes();
        if(refeicoes.isEmpty()){
            return RefeicaoError.errorSemRefeicoes();
        }
        return new ResponseEntity<>(refeicoes, HttpStatus.OK);
    }

    @GetMapping("/refeicao/{id}")
    public ResponseEntity<?> getRefeicao(@PathVariable Long id){
        Optional<RefeicaoDTO> refeicaoOptional = refeicaoService.getRefeicaoById(id);
        if(refeicaoOptional.isEmpty()){
            return RefeicaoError.errorRefeicaoNaoExiste(id);
        }
        return new ResponseEntity<>(refeicaoOptional.get(), HttpStatus.OK);
    }

    @PutMapping("/refeicao/{id}")
    public ResponseEntity<?> avaliarRefeicao(@PathVariable Long id, AvaliacaoDTO avaliacaoDTO){
        Optional<RefeicaoDTO> refeicaoOptional = refeicaoService.getRefeicaoById(id);
        if(refeicaoOptional.isEmpty()){
            return RefeicaoError.errorRefeicaoNaoExiste(id);
        }
        return new ResponseEntity<>(refeicaoService.avaliarRefeicao(id, avaliacaoDTO), HttpStatus.OK);
    }

    @DeleteMapping("/refeicao/{id}")
    public ResponseEntity<?> deleteRefeicao(@PathVariable Long id){
        Optional<RefeicaoDTO> refeicaoOptional = refeicaoService.getRefeicaoById(id);
        if(refeicaoOptional.isEmpty()){
            return RefeicaoError.errorRefeicaoNaoExiste(id);
        }
        refeicaoService.deleteRefeicao(id);
        return new ResponseEntity<>(id, HttpStatus.OK);

    }

    //GetRefeicoesDeUsuario
    //Checkout
    //GetAvaliacoesPorPrato
    // 

}
