package com.studor.orientation_student.manager.security;

import java.util.Map;
import java.util.Date;

import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Service;
import java.security.SecureRandom; // Ensure this import is present

import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.manager.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class JwtService {

    private static final SecretKey SECRET_KEY = getKey();
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    public Map<String, String> generate(String email){
        User user = userService.loadUserByUsername(email);
        return this.generateJwt(user);
    }

    public String getEmailFromToken(String token){
        return getClaim(token, Claims::getId);
    }

    public Boolean isTokenExpired(String token){
        Date expiration = getClaim(token, Claims::getExpiration);
        return expiration != null && expiration.before(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token){
        try{
            return Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
                } catch (SignatureException e) {
                    logger.error("Invalid JWT signature: {}", e.getMessage());
                    throw new RuntimeException("Invalid JWT signature. Please authenticate again.");
                }
    }

    private Map<String, String> generateJwt(User user){

        final Long currentTime = System.currentTimeMillis();
        final Long expirationTime = currentTime + 1800000;

        final String bearer = Jwts.builder()
            .claim(Claims.ISSUED_AT, currentTime)
            .claim(Claims.EXPIRATION, expirationTime)
            .claim(Claims.ID, user.getEmail())
            .claim(Claims.SUBJECT, user.getRole().getType().toString())
            .signWith(SECRET_KEY)
            .compact();
        return Map.of("bearer", bearer);
    }

    private static SecretKey getKey() {
        byte[] keyBytes = new byte[64];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}