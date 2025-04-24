package com.pakerek.auth.exception.dto;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ExceptionResponse {

    private final Map<String, Object> response = new HashMap<>();

    public ExceptionResponse(String message) {
        this.response.put("message", message);
        this.response.put("status", false);
    }
}


