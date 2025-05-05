package com.pakerek.auth.exception.handler;

import com.pakerek.auth.exception.JwtCookieMissingException;
import com.pakerek.auth.exception.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthorizationExceptionsHandler {

    @ExceptionHandler(JwtCookieMissingException.class)
    ResponseEntity<ExceptionResponse> jwtCookieMissingExceptionHandler(JwtCookieMissingException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(e.getMessage()));
    }
}
