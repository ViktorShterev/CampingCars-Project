package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.service.JwtService;

import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    private final String jwtSecret;

    public JwtServiceImpl(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Override
    public String generateToken(String userId, Map<String, Object> claims) {
        var now = new Date();
        long expiration = 60000;

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
