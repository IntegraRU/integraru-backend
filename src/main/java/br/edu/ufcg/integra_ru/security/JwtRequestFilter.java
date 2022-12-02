package br.edu.ufcg.integra_ru.security;

import br.edu.ufcg.integra_ru.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String auth = request.getHeader("Authorization");
        String token;

        int TOKEN_START_INDEX = 7;
        if(hasBearer(auth)){
            token = auth.substring(TOKEN_START_INDEX);
            if(userNotInContext(token)){
                addUserToContext(token, request);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean hasBearer(String auth){
        return auth != null && auth.startsWith("Bearer");
    }
    private boolean userNotInContext(String token){
        String username = jwtService.getSubject(token);
        return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private void addUserToContext(String token, HttpServletRequest request){
        String username = jwtService.getSubject(token);
        UserDetails user = this.userDetailsService.loadUserByUsername(username);
        if(jwtService.validateToken(token, user)){
            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(userToken);
        }
    }
}
