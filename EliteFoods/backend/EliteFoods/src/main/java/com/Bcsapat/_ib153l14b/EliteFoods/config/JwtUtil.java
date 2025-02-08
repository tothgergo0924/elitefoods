package com.Bcsapat._ib153l14b.EliteFoods.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "200309241970906197509062006012520090924";

    public String extractUsername(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    public boolean isTokenValid(String token) {
        Claims claims = parseToken(token);
        return !claims.getExpiration().before(new Date());
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String generateToken(Authentication authentication){

        String role=authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("roles",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY.getBytes())
                .compact();
    }
}
