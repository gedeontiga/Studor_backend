package com.studor.orientation_student.manager.security;

import java.security.SecureRandom; // Ensure this import is present
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.cglib.core.internal.Function;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.studor.orientation_student.entities.Jwt;
import com.studor.orientation_student.entities.RefreshToken;
import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.manager.repositories.JwtRepository;
import com.studor.orientation_student.manager.services.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class JwtService {

    private static final String TOKEN_NOT_FOUND = "Token not found";
    private static final String REFRESH = "refresh";
    private static final SecretKey SECRET_KEY = getKey();

    private static SecretKey getKey() {
        final byte[] keyBytes = new byte[64];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private final UserService userService;
    private final JwtRepository jwtRepository;

    private final String BEARER = "bearer";

    public Map<String, String> generate(final String email) {
        final User user = userService.loadUserByUsername(email);
        this.disableToken(user);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10);
        Map<String, String> jwtMap = this.generateJwt(user);
        final RefreshToken refreshToken = RefreshToken.builder()
                .value(UUID.randomUUID().toString())
                .expired(false)
                .creation(new Date())
                .expiration(calendar.getTime())
                .build();

        final Jwt jwt = Jwt.builder()
                .token(jwtMap.get(BEARER))
                .desactivated(false)
                .expired(false)
                .refreshToken(refreshToken)
                .user(user)
                .build();
        jwtRepository.save(jwt);
        jwtMap.put(REFRESH, refreshToken.getValue());
        return jwtMap;
    }

    public String getEmailFromToken(final String token) {
        return getClaim(token, Claims::getId);
    }

    public Jwt getJwtByToken(final String token) {
        return jwtRepository.findByToken(token).orElseThrow(() -> new RuntimeException(TOKEN_NOT_FOUND));
    }

    public void logout() {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = jwtRepository.findByUserAndDesactivatedIsFalseAndExpiredIsFalse(user)
                .orElseThrow(() -> new RuntimeException(TOKEN_NOT_FOUND));
        jwt.setDesactivated(true);
        jwt.setExpired(true);
        jwtRepository.save(jwt);
    }

    public Boolean isTokenExpired(final String token) {
        final Date expiration = getClaim(token, Claims::getExpiration);
        return expiration != null && expiration.before(new Date());
    }

    @Scheduled(cron = "@hourly")
    public void removeUselessJwt() {
        log.info("Removing token at {}", Instant.now());
        this.jwtRepository.deleteAllByDesactivatedIsTrueAndExpiredIsTrue();
    }

    public Map<String, String> refreshToken(final Map<String, Object> refreshTokenRequest) {
        final Jwt jwt = jwtRepository.findByRefreshTokenValue(refreshTokenRequest.get(REFRESH).toString())
                .orElseThrow(() -> new RuntimeException());
        if (jwt.getRefreshToken().getExpired() || jwt.getRefreshToken().getExpiration().before(new Date())) {
            throw new RuntimeException("Refreshed token has expired");
        }
        disableToken(jwt.getUser());
        final Map<String, String> tokens = this.generate(jwt.getUser().getEmail());
        return tokens;
    }

    private void disableToken(final User user) {
        final List<Jwt> jwts = jwtRepository.findByUser(user).peek(
                jwt -> {
                    jwt.setDesactivated(true);
                    jwt.setExpired(true);
                    jwt.getRefreshToken().setExpired(true);
                }).collect(Collectors.toList());
        jwtRepository.saveAll(jwts);
    }

    private <T> T getClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(final String token) throws RuntimeException {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Map<String, String> generateJwt(final User user) {

        final Long currentTime = System.currentTimeMillis();
        final Long expirationTime = currentTime + 2*60*1000;

        final String bearer = Jwts.builder()
                .claim(Claims.ISSUED_AT, new Date(currentTime))
                .claim(Claims.EXPIRATION, new Date(expirationTime))
                .claim(Claims.ID, user.getEmail())
                .claim(Claims.SUBJECT, user.getRole().getType().toString())
                .signWith(SECRET_KEY)
                .compact();
        return new HashMap<>(Map.of(BEARER, bearer));
    }
}