package com.pakerek.auth.exception;

public class JwtCookieMissingException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Brakuje ciaskeczka autoryzacyjnego";
  }
}
