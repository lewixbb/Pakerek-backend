package com.pakerek.auth.security.service;

import com.pakerek.auth.security.model.dto.request.LoginRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface UserAuthorizationService {
    ResponseEntity<?> authenticateUser(HttpServletResponse response, LoginRequest loginRequest);
}
