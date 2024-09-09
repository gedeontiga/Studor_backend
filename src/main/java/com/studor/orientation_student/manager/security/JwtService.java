package com.studor.orientation_student.manager.security;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.Date;
import java.util.List;

import org.springframework.cglib.core.internal.Function;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom; // Ensure this import is present
import java.time.Instant;

import com.studor.orientation_student.entities.Jwt;
import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.manager.repositories.JwtRepository;
import com.studor.orientation_student.manager.services.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;

import javax.crypto.SecretKey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class JwtService {

    private static final SecretKey SECRET_KEY = getKey();
    private final UserService userService;
    private final JwtRepository jwtRepository;
    private final String BEARER = "bearer";

    public Map<String, String> generate(final String email){
        final User user = userService.loadUserByUsername(email);
        this.disableToken(user);
        final Map<String, String> jwtMap = this.generateJwt(user);
        final Jwt jwt = Jwt.builder()
                            .token(jwtMap.get(BEARER))
                            .desactivated(false)
                            .expired(false)
                            .user(user)
                            .build();
        jwtRepository.save(jwt);
        return jwtMap;
    }

    public String getEmailFromToken(final String token){
        return getClaim(token, Claims::getId);
    }
    
    public Jwt getJwtByToken(final String token){
        return jwtRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Token not found"));
    }

    public void logout(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = jwtRepository.findByUserAndDesactivatedIsFalseAndExpiredIsFalse(user)
                                .orElseThrow(() -> new RuntimeException("Token not found"));
        jwt.setDesactivated(true);
        jwt.setExpired(true);
        jwtRepository.save(jwt);
    }

    public Boolean isTokenExpired(final String token){
        final Date expiration = getClaim(token, Claims::getExpiration);
        return expiration != null && expiration.before(new Date());
    }

    private void disableToken(final User user){
        List<Jwt> jwts = jwtRepository.findByUser(user).peek(
            jwt -> {
                jwt.setDesactivated(true);
                jwt.setExpired(true);
            }
        ).collect(Collectors.toList());
        jwtRepository.saveAll(jwts);
    }

    private <T> T getClaim(final String token, final Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(final String token) throws RuntimeException{
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Map<String, String> generateJwt(final User user){

        final Long currentTime = System.currentTimeMillis();
        final Long expirationTime = currentTime + 1800000;

        final String bearer = Jwts.builder()
            .claim(Claims.ISSUED_AT, currentTime)
            .claim(Claims.EXPIRATION, expirationTime)
            .claim(Claims.ID, user.getEmail())
            .claim(Claims.SUBJECT, user.getRole().getType().toString())
            .signWith(SECRET_KEY)
            .compact();
        return Map.of(BEARER, bearer);
    }

    private static SecretKey getKey() {
        final byte[] keyBytes = new byte[64];
        final SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Scheduled(cron = "@hourly")
    public void removeUselessJwt() {
        log.info("Removing token at {}", Instant.now());
        this.jwtRepository.deleteAllByDesactivatedIsTrueAndExpiredIsTrue();
    }
}