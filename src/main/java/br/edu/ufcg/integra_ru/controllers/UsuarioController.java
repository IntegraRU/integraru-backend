package br.edu.ufcg.integra_ru.controllers;


import br.edu.ufcg.integra_ru.dtos.UsuarioDTO;
import br.edu.ufcg.integra_ru.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioDTO usuarioDTO) {

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{matricula}")
                .buildAndExpand(usuarioDTO.getMatricula()).toUri();

        return ResponseEntity.created(uri).body(usuarioService.saveUser(usuarioDTO));
    }
}
