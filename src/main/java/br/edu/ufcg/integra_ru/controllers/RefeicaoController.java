package br.edu.ufcg.integra_ru.controllers;

import br.edu.ufcg.integra_ru.dtos.AvaliacaoDTO;
import br.edu.ufcg.integra_ru.dtos.CheckoutDTO;
import br.edu.ufcg.integra_ru.dtos.RefeicaoDTO;
import br.edu.ufcg.integra_ru.models.Refeicao;
import br.edu.ufcg.integra_ru.models.Usuario;
import br.edu.ufcg.integra_ru.services.RefeicaoService;
import br.edu.ufcg.integra_ru.services.UsuarioService;
import br.edu.ufcg.integra_ru.util.ErrorRefeicao;
import br.edu.ufcg.integra_ru.util.UserError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/refeicao")
public class RefeicaoController {

    @Autowired
    private RefeicaoService refeicaoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> efetuarReserva(@RequestBody @Valid RefeicaoDTO refeicaoDTO){
        Optional<Usuario> user = usuarioService.getUserByEnroll(refeicaoDTO.getUsuario());
        if(user.isEmpty()){
            return UserError.errorUsuarioNaoCadastrado(refeicaoDTO.getUsuario());
        }
        if(refeicaoService.estaReservado(refeicaoDTO)){
            return ErrorRefeicao.errorJaReservou();
        }
        Refeicao refeicao = refeicaoService.cadastrarRefeicao(refeicaoDTO);
        BigDecimal valor = refeicaoService.getValorRefeicao(refeicao);
        this.usuarioService.debitarValor(user.get(), valor);
        return new ResponseEntity<Refeicao>(refeicao, HttpStatus.OK);
    }


    @PutMapping("/checkout")
    public ResponseEntity<?> efetuarCheckout(@RequestBody @Valid CheckoutDTO checkoutDTO){
        Optional<Refeicao> refeicaoOptional = refeicaoService.getRefeicaobyId(checkoutDTO.getIdRefeicao());
        if(refeicaoOptional.isEmpty()){
            return ErrorRefeicao.errorSemReserva();
        }
        Refeicao refeicao = refeicaoOptional.get();
        if(refeicao.getDataCheckout() != null){
            return ErrorRefeicao.errorJaFezCheckout();
        }
        Refeicao refeicaoNova = refeicaoService.efetuarCheckout(checkoutDTO, refeicao);
        return new ResponseEntity<Refeicao>(refeicaoNova, HttpStatus.OK);
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

    @PutMapping("/{idRefeicao}")
    public ResponseEntity<?> avaliarRefeicao(@RequestBody @Valid AvaliacaoDTO avaliacaoDTO, @PathVariable Long idRefeicao){
        Optional<Refeicao> refeicao = refeicaoService.getRefeicaobyId(idRefeicao);
        if(refeicao.isEmpty()){
            return new ResponseEntity<>(ErrorRefeicao.errorRefeicaoNaoExiste(idRefeicao), HttpStatus.BAD_REQUEST);
        }
        if(refeicao.get().getDataCheckout() == null){
            return ErrorRefeicao.errorNaoFezCheckout();
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
