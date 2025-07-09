package com.antonio.apprendrebackend.service.config.jwt;

import com.antonio.apprendrebackend.service.model.UserInfo;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    public String getSupabaseIdFromToken(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    public boolean isTokenValid(String token, UserInfo userInfo) {
        final String username = getSupabaseIdFromToken(token);
        return (username.equals(userInfo.getSupabaseId()) && !isTokenExpired(token));
    }

    private Date getExpiration(String token) {
        Claims claims = extractClaims(token);
        return claims.getExpiration();
    }

    private static Claims extractClaims(String token) {
        String secretKey = "";
        Dotenv dotenv = Dotenv.load();
        secretKey = dotenv.get("JWT_SECRET_KEY");
        return Jwts.parser().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
