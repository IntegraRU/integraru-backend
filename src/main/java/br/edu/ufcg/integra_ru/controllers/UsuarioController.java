package br.edu.ufcg.integra_ru.controllers;


import br.edu.ufcg.integra_ru.dtos.UsuarioDTO;
import br.edu.ufcg.integra_ru.models.Usuario;
import br.edu.ufcg.integra_ru.services.UsuarioService;
import br.edu.ufcg.integra_ru.util.UserError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    @PostMapping("/matricula")
    public ResponseEntity<?> saveUser(@RequestBody UsuarioDTO usuarioDTO){
        Usuario user = usuarioService.saveUser(usuarioDTO);
        return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<?> deleteUser(@PathVariable Long matricula){
        Optional<Usuario> user = usuarioService.getUserByEnroll(matricula);
        if(user.isEmpty()){
            return new ResponseEntity<>(UserError.errorUsuarioNaoCadastrado(matricula), HttpStatus.BAD_REQUEST);
        }
        usuarioService.deleteUser(user.get());
        return new ResponseEntity<>(matricula, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> listUsers(){
        List<Usuario> users = usuarioService.getUsers();
        if(users.isEmpty()){
            return new ResponseEntity<>(UserError.errorNenhumUsuarioCadastrado(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<?> getUser(@PathVariable Long matricula){
        Optional<Usuario> user = usuarioService.getUserByEnroll(matricula);
        if(user.isEmpty()){
            return new ResponseEntity<>(UserError.errorUsuarioNaoCadastrado(matricula), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }
}