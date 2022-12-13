package br.edu.ufcg.integra_ru.controllers;


import br.edu.ufcg.integra_ru.dtos.UsuarioDTO;
import br.edu.ufcg.integra_ru.dtos.UsuarioResponseDTO;
import br.edu.ufcg.integra_ru.models.Usuario;
import br.edu.ufcg.integra_ru.services.UsuarioService;
import br.edu.ufcg.integra_ru.util.UserError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/user")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UsuarioDTO usuarioDTO){
        UsuarioResponseDTO user = usuarioService.createUser(usuarioDTO);
        return new ResponseEntity<UsuarioResponseDTO>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<?> deleteUser(@PathVariable String matricula){
        Optional<Usuario> user = usuarioService.getUserByEnroll(matricula);
        if(user.isEmpty()){
            return new ResponseEntity<>(UserError.errorUsuarioNaoCadastrado(matricula), HttpStatus.BAD_REQUEST);
        }
        usuarioService.deleteUser(user.get());
        return new ResponseEntity<>(matricula, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> listUsers(){
        List<Usuario> users = usuarioService.listUsers();
        if(users.isEmpty()){
            return new ResponseEntity<>(UserError.errorNenhumUsuarioCadastrado(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<?> getUser(@PathVariable String matricula){
        Optional<Usuario> user = usuarioService.getUserByEnroll(matricula);
        if(user.isEmpty()){
            return new ResponseEntity<>(UserError.errorUsuarioNaoCadastrado(matricula), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> updateCreditUser(@PathVariable String matricula, @PathVariable BigDecimal quantCredito){
        Optional<Usuario> user = usuarioService.getUserByEnroll(matricula);
        if(user.isEmpty()){
            return new ResponseEntity<>(UserError.errorUsuarioNaoCadastrado(matricula), HttpStatus.BAD_REQUEST);
        }
        BigDecimal valor = new BigDecimal("0.0");
        if(quantCredito.compareTo(valor) == 1) {
            return new ResponseEntity<>(UserError.errorValorNaoPodeSerAdicionado(), HttpStatus.BAD_REQUEST);
        }
        usuarioService.addCredit(matricula, quantCredito);
        return new ResponseEntity<>(matricula, HttpStatus.OK);
    }
}