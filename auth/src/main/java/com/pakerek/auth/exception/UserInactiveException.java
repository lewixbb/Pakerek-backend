package com.pakerek.auth.exception;

public class UserInactiveException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Użytkownik jest nieaktywny, proszę o dokończenie procesu rejestracji.";
    }
}
