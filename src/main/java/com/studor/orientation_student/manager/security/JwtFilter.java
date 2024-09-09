package com.studor.orientation_student.manager.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.studor.orientation_student.entities.Jwt;
import com.studor.orientation_student.manager.services.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull final HttpServletRequest request, @NonNull final HttpServletResponse response, @NonNull final FilterChain filterChain)
            throws ServletException, IOException {

        Boolean isTokenExpired = true;
        String email = null;
        Jwt jwtLoaded = new Jwt();

        final String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            final String token = authorization.substring(7);
            jwtLoaded = jwtService.getJwtByToken(token);
            isTokenExpired = jwtService.isTokenExpired(token);
            email = jwtService.getEmailFromToken(token);
        }

        if (!isTokenExpired 
                && jwtLoaded.getUser().getEmail().equals(email)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = userService.loadUserByUsername(email);
            final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
    
}
