package com.fjdev.rackapirest.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class TokenUtils {

    private static final SecretKey ACCESS_TOKEN_SECRET = generateSecretKey();
    private final static Long ACCES_TOKEN_VALIDITY_SECONDS = 2_592_000L;

    private static SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            keyGen.init(256);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Error al generar clave secreta", e);
        }
    }

    public static String createToken(String nombre, String email) {
        long expirationTime = ACCES_TOKEN_VALIDITY_SECONDS * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("nombre", nombre);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(ACCESS_TOKEN_SECRET)
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

        } catch (JwtException e) {
            return null;
        }

    }

}
