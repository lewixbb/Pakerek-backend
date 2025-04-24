package com.pakerek.auth.exception;

public class TokenNotFoundException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Nie znaleziono tokenu";
  }
}
