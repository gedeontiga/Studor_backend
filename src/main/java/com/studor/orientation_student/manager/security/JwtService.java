package com.studor.orientation_student.manager.security;

import java.security.Key;
import java.util.Map;

import org.springframework.stereotype.Service;
import java.security.SecureRandom; // Ensure this import is present

import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.manager.services.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtService {

    private final UserService userService;

    public Map<String, String> generate(String email){
        User user = userService.loadUserByUsername(email);
        return this.generateJwt(user);
    }

    public Map<String, String> generateJwt(User user){

        final Long currentTime = System.currentTimeMillis();
        final Long expirationTime = currentTime + 1800000;

        final String token = Jwts.builder()
            .claim("iat", currentTime)
            .claim("exp", expirationTime)
            .claim("sub", user.getEmail())
            .claim("username", user.getUsername())
            .claim("role", user.getRole().getType().toString())
            .signWith(getKey())
            .compact();
        return Map.of("token", token);
    }

    private Key getKey() {
        byte[] keyBytes = new byte[64];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);
        System.out.println(keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}