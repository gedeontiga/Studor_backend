package com.studor.orientation_student.manager.controllers.advices;

import java.util.Map;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class UserControllerAdvice {

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = BadCredentialsException.class)
    public @ResponseBody ProblemDetail badCredentialsException(BadCredentialsException exception){

        UserControllerAdvice.log.error(exception.getMessage(), exception);

        return ProblemDetail.forStatusAndDetail(
            UNAUTHORIZED, 
            "Invalid credentials");
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = {MalformedJwtException.class, SignatureException.class})
    public @ResponseBody ProblemDetail tokenException(final Exception exception) {

        UserControllerAdvice.log.error(exception.getMessage(), exception);

        return ProblemDetail.forStatusAndDetail(
                UNAUTHORIZED,
                "Invalid token");
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = { AccessDeniedException.class })
    public @ResponseBody ProblemDetail accessException(final AccessDeniedException exception) {

        UserControllerAdvice.log.error(exception.getMessage(), exception);

        return ProblemDetail.forStatusAndDetail(
                FORBIDDEN,
                "You are not authorized to perform this action");
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = Exception.class)
    public Map<String, String> exceptionHandler(final Exception exception) {
        return Map.of("error", exception.getMessage());
    }
}