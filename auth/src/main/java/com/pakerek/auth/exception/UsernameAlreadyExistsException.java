package com.pakerek.auth.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Użytkownik o podanej nazwie już istnieje.";
    }
}
