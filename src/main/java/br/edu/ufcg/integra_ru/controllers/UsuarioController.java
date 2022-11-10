package br.edu.ufcg.integra_ru.controllers;


import br.edu.ufcg.integra_ru.dtos.UsuarioDTO;
import br.edu.ufcg.integra_ru.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
    @GetMapping
    public List<UsuarioDTO> getUsuarios(){
        return usuarioService.getUsers();
    }

    @GetMapping("/{matricula}")
    public UsuarioDTO getUsuarioByUser(@PathVariable Long matricula){
        return usuarioService.getUserByEnroll(matricula);
    }

    @DeleteMapping("/{matricula}")
    public boolean deleteMenu(@PathVariable Long matricula){
        return usuarioService.deleteUserByEnroll(matricula);
    }

    @PutMapping("/{matricula}")
    public UsuarioDTO updateMenu(@PathVariable Long matricula, @RequestBody UsuarioDTO dto){
        return usuarioService.updateUser(matricula, dto);
    }
}
