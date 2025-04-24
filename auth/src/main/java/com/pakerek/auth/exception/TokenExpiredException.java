package com.pakerek.auth.exception;

public class TokenExpiredException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Token wygasł";
    }
}
