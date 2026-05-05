package com.pm.authservice.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key secretKey;
    private final long EXPIRATION_TIME = 86400000;

    public JwtUtil(@Value("${jwt.secret}") String secret) {

        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);


        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("JWT Secret key must be at least 32 characters long!");
        }

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

//    public String extractEmail(String token) {
//        return Jwts.parser()
//                .verifyWith(secretKey)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload()
//                .getSubject();
//    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) secretKey).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            return false;
        }catch (JwtException e){
            return  false;
        }
    }
}
