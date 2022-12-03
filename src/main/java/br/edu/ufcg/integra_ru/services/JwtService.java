package br.edu.ufcg.integra_ru.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtService {
    private static final long EXPIRY_DAYS = 7;
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(UserDetails user){
        String scope = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("role", scope)
                .withExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    public Boolean validateToken(String token, UserDetails user){
        final String username = this.getSubject(token);
        return username.equals(user.getUsername()) && notExpired(token);
    }

    public String getSubject(String token){
        return this.extractClaimFromToken(token, Payload::getSubject);
    }

    public Instant getExpiresAt(String token){
        return this.extractClaimFromToken(token, Payload::getExpiresAtAsInstant);
    }

    private <T> T extractClaimFromToken(String token, Function<DecodedJWT, T> resolver){
        DecodedJWT claims = this.extractClaimsFromToken(token);
        return resolver.apply(claims);
    }

    private boolean notExpired(String token){
        Instant ins = this.getExpiresAt(token);
        return Instant.now().isBefore(ins);
    }

    private DecodedJWT extractClaimsFromToken(String token){
        return JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(token);
    }


}
