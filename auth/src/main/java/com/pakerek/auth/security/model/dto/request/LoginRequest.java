package com.pakerek.auth.security.model.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
