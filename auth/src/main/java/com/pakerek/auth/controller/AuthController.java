package com.pakerek.auth.controller;

import com.pakerek.auth.security.model.dto.request.LoginRequest;
import com.pakerek.auth.security.service.UserAuthorizationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthorizationService userAuthorizationService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        return userAuthorizationService.authenticateUser(response,loginRequest);
    }

//    @GetMapping("/logout")
//    public ResponseEntity<String> logout (){
//        return ResponseEntity.ok("test");
//    }

}
