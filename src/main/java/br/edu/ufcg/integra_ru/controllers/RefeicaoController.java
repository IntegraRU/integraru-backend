package br.edu.ufcg.integra_ru.controllers;


import br.edu.ufcg.integra_ru.dtos.AvaliacaoDTO;
import br.edu.ufcg.integra_ru.dtos.CheckoutDTO;
import br.edu.ufcg.integra_ru.dtos.RefeicaoDTO;
import br.edu.ufcg.integra_ru.dtos.RelatorioDTO;
import br.edu.ufcg.integra_ru.models.Usuario;
import br.edu.ufcg.integra_ru.services.RefeicaoService;
import br.edu.ufcg.integra_ru.services.RelatorioService;
import br.edu.ufcg.integra_ru.services.UsuarioService;
import br.edu.ufcg.integra_ru.util.RefeicaoError;
import br.edu.ufcg.integra_ru.util.UserError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RefeicaoController {

    @Autowired
    RefeicaoService refeicaoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private RelatorioService relatorioService;

    @PostMapping("/refeicao")
    public ResponseEntity<?> cadastrarRefeicao(@RequestBody RefeicaoDTO refeicaoDTO){
        Optional<Usuario> usuarioOptional = usuarioService.getUserByEnroll(refeicaoDTO.getUsuarioMatricula());
        if(usuarioOptional.isEmpty()){
            return UserError.errorUsuarioNaoCadastrado(refeicaoDTO.getUsuarioMatricula());
        }
        if(refeicaoService.refeicaoExiste(refeicaoDTO)){
            return RefeicaoError.errorRefeicaoJaCadastrada(refeicaoDTO.getUsuarioMatricula(), refeicaoDTO.getDataReserva());
        }
        RefeicaoDTO refeicao = refeicaoService.cadastrarRefeicao(refeicaoDTO);
        if(!usuarioOptional.get().isBeneficiario()){
            BigDecimal valor = refeicaoService.getValorRefeicao(refeicao.getRefeicaoID());
            usuarioService.debitarValor(valor);
        }
        return new ResponseEntity<>(refeicao, HttpStatus.OK);
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
        if(!this.refeicaoService.fezCheckout(id)){
            return RefeicaoError.errorNaoFezCheckout(id);
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

    @PutMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutDTO checkoutDTO){
        Optional<RefeicaoDTO> refeicaoOptional = refeicaoService.getRefeicaoById(checkoutDTO.getRefeicaoID());
        if(refeicaoOptional.isEmpty()){
            return RefeicaoError.errorRefeicaoNaoExiste(checkoutDTO.getRefeicaoID());
        }
        if(this.refeicaoService.fezCheckout(checkoutDTO.getRefeicaoID())){
            return RefeicaoError.errorJaFezCheckout(checkoutDTO.getRefeicaoID());
        }
        RefeicaoDTO refeicaoNova = refeicaoService.efetuarCheckout(checkoutDTO);
        return new ResponseEntity<RefeicaoDTO>(refeicaoNova, HttpStatus.OK);
    }

    //GetRefeicoesDeUsuario
    //Checkout
    //GetAvaliacoesPorPrato
    //

    @GetMapping("/relatorio")
    public List<RelatorioDTO> getRelatorio(){
        return relatorioService.gerarRelatorio();
    }

}
