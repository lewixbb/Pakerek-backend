package com.pakerek.auth.controller;

import com.pakerek.auth.user.model.dto.UserResponseDto;

import com.pakerek.auth.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/myAccount")
    public ResponseEntity<UserResponseDto> getOwnUser(@AuthenticationPrincipal UserDetails user){
        return ResponseEntity.ok(userService.getOwnUser(user.getUsername()));
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/test")
    public String test (){
        return "test";
    }

    @GetMapping("/public/test")
    public String test2 (){
        return "test";
    }

    @PostMapping("/public/changePass")
    public ResponseEntity<?> changePasswordRequest(@RequestParam String email){
        userService.changePasswordRequest(email);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/public/changePass")
    public ResponseEntity<?> changePasswordForUser(@RequestParam String uuid, @RequestParam String password){
        userService.changePasswordForUser(uuid,password);
        return ResponseEntity.ok().build();
    }
}
