package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(CompromisedPasswordException.class)
    public ErrorResponse handleCompromisedPassword(CompromisedPasswordException ex) {
        return ErrorResponse
            .builder(ex, HttpStatus.UNAUTHORIZED, "Compromised Password")
            .type(URI.create("https://example.org/errors/compromised-password"))
            .detail(ex.getMessage())
            .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse handleBadCredentials(BadCredentialsException ex) {
        return ErrorResponse
            .builder(ex, HttpStatus.UNAUTHORIZED, "Bad credentials")
            .type(URI.create("https://example.org/errors/bad-credentials"))
            .detail(ex.getMessage())
            .build();
    }

    @ExceptionHandler(AuthenticationException.class)
    public ErrorResponse handleAuthenticationFailed(AuthenticationException ex) {
        return ErrorResponse
            .builder(ex, HttpStatus.UNAUTHORIZED, "Authentication failed")
            .type(URI.create("https://example.org/errors/authentication-failed"))
            .detail(ex.getMessage())
            .build();
    }
}
