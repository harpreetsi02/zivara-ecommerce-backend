package com.zivara.backend.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component  // Spring ko batata hai ki yeh ek utility bean hai
public class JwtUtil {

    @Value("${jwt.secret}")       // application.properties se value aayegi
    private String secret;

    @Value("${jwt.expiration}")   // 86400000 = 24 ghante
    private long expiration;

    // Secret key banao — yeh sign karne ke liye use hoti hai
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Token generate karo — login ke baad yeh call hoga
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)              // token mein email store hogi
                .claim("role", role)            // role bhi store hogi
                .setIssuedAt(new Date())        // kab banaya
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // kab expire hoga
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // sign karo
                .compact();
    }

    // Token se email nikalo
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Token se role nikalo
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // Token valid hai ya nahi check karo
    public boolean isTokenValid(String token) {
        try {
            getClaims(token); // agar invalid hai toh exception throw hoga
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Token ka data nikalo
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}