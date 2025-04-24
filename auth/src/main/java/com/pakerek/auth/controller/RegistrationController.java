package com.pakerek.auth.controller;

import com.pakerek.auth.user.model.dto.UserRegisterDto;
import com.pakerek.auth.user.model.dto.UserResponseDto;
import com.pakerek.auth.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> userRegister (@RequestBody UserRegisterDto user){
        return ResponseEntity.ok(userService.addUserWithDefaultRole(user));
    }

    @GetMapping("/activate/{uuid}")
    public ResponseEntity<?> activateUser(@PathVariable String uuid){
        userService.activateUser(uuid);
        return ResponseEntity.ok().build();
    }
}
