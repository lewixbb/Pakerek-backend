package com.pakerek.auth.exception.handler;

import com.pakerek.auth.exception.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationExceptionsHandler {

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<ExceptionResponse> badCredentialsExceptionHandler(BadCredentialsException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(DisabledException.class)
    ResponseEntity<ExceptionResponse> disabledExceptionHandler(DisabledException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(e.getMessage()));
    }
}
