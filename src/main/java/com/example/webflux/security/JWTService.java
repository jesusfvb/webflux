package com.example.webflux.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    private final String SECRET = "2F423F4528482B4D6251655468576D597133743677397A24432646294A404E63";

    public String generate(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(userDetails.getUsername(), claims);
    }

    public Boolean validate(String token) {
        try {
            return getAllClaimsFromToken(token) != null;
        } catch (Error e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Claims getAllClaimsFromToken(String token) throws Error {
        return Jwts.parserBuilder().setSigningKey(getSecret()).build().parseClaimsJws(token).getBody();
    }

    private String createToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(username)
                .addClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getSecret(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

}
