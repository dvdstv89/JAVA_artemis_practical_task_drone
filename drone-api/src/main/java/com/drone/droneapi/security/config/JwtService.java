package com.drone.droneapi.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "61C40907306585F43EA72A020C993DAD4AD612D838839EC742177390D4F34209";
    private static final String SUPER_ADMIN_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXZpZEBkcm9uZS5jb20iLCJpYXQiOjE3MDAyMzEzMTMsImV4cCI6MTcwMDIzMjc1M30.f-NuhMds2cMx_X3aMSgI-k0Bz30_VXc8nrCnQT1KMZw";

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 *60 * 24 ))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserEmail(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return  extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extratAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extratAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseEncryptedClaims(token) //todo error desencriptando
                .getPayload();

    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
