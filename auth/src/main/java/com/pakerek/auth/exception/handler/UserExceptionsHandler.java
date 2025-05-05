package com.pakerek.auth.exception.handler;

import com.pakerek.auth.exception.TokenExpiredException;
import com.pakerek.auth.exception.TokenNotFoundException;
import com.pakerek.auth.exception.UserInactiveException;
import com.pakerek.auth.exception.UsernameAlreadyExistsException;
import com.pakerek.auth.exception.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionsHandler {

    @ExceptionHandler(value = UsernameNotFoundException.class)
    ResponseEntity<ExceptionResponse> usernameNotFoundExceptionHandler(UsernameNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(value = UsernameAlreadyExistsException.class)
    ResponseEntity<ExceptionResponse> usernameAlreadyExistsExceptionHandler(UsernameAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    ResponseEntity<ExceptionResponse> tokenExpiredExceptionHandler(TokenExpiredException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(value = TokenNotFoundException.class)
    ResponseEntity<ExceptionResponse> tokenNotFoundExceptionHandler(TokenNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(value = UserInactiveException.class)
    ResponseEntity<ExceptionResponse> userInactiveExceptionHandler(UserInactiveException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(e.getMessage()));
    }


}
