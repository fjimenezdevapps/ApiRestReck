package com.fjdev.rackapirest.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        
        AuthCredentials authCredentials = new AuthCredentials();

        try {
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        } catch (IOException e) {
            e.setStackTrace(null);
        }
        
        UsernamePasswordAuthenticationToken usernamePat = new UsernamePasswordAuthenticationToken(
            authCredentials.getEmail(), 
            authCredentials.getPassword(), 
            Collections.emptyList()
        );
        
        return getAuthenticationManager().authenticate(usernamePat);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        
        UserDetailsImpl userDetails = (UserDetailsImpl)authResult.getPrincipal();
        String token = TokenUtils.createToken(userDetails.getNameAccount(), userDetails.getUsername());

        // Construir la respuesta JSON con el token
        String jsonResponse = new ObjectMapper().writeValueAsString(Collections.singletonMap("token", token));

        // Establecer el tipo de contenido de la respuesta como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}