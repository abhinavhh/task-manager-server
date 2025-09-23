package com.example.taskManagement.Configs;

import com.example.taskManagement.Enums.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${jwt.expirationMs}")
    private Long jwtExpirationMs;

    // Convert string to Cryptographic format
    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    // Create a token
    public String generateToken(String username, Long userId, Role role){
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setId(userId.toString())
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }
}
