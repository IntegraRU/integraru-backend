package br.edu.ufcg.integra_ru.controllers;

import br.edu.ufcg.integra_ru.dtos.JwtRequest;
import br.edu.ufcg.integra_ru.dtos.JwtResponse;
import br.edu.ufcg.integra_ru.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtService tokenService;

    public AuthController(AuthenticationManager authenticationManager, JwtService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public JwtResponse token(@RequestBody JwtRequest jwtRequest){
        Authentication user = authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        LOG.info("Token requested for user: '{}'", user.getName());
        String token = tokenService.generateAccessToken((UserDetails) user.getPrincipal());
        LOG.info("Token granted {}", token);
        return new JwtResponse(token);
    }

    private Authentication authenticate(String username, String password){
        return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
