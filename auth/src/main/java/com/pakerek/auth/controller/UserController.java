package com.pakerek.auth.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.pakerek.auth.user.model.Role;
import com.pakerek.auth.user.model.dto.UserPutRequestDto;
import com.pakerek.auth.user.model.dto.UserResponseDto;


import com.pakerek.auth.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getUsers(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(required = false, name = "sort") String sortBy,
            @RequestParam(required = false, name = "order") String order,
            @RequestParam(required = false, name = "filter") String filter
    ){
        return ResponseEntity.ok(userService.findUsersWithPaginationAndFilter(page, size, sortBy, order, filter));
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id,@RequestBody UserPutRequestDto user){
        return ResponseEntity.ok(userService.changeUserData(id, user));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/myAccount")
    public ResponseEntity<UserResponseDto> getOwnUser(@AuthenticationPrincipal UserDetails user){
        return ResponseEntity.ok(userService.getOwnUser(user.getUsername()));
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/permissions")
    public ResponseEntity<List<Role>> permissions (){
        return ResponseEntity.ok(userService.permissionList());
    }

    @PostMapping("/public/changePass")
    public ResponseEntity<?> changePasswordRequest(@RequestBody String  email){
        userService.changePasswordRequest(email);
        return ResponseEntity.ok().body("Na Twoje konto mailowe wysłano wiadomość, skorzystaj z linki potwierdzającego w celu kontynuacji procedury zmiany hasła.");
    }

    @PatchMapping("/public/changePass/{uuid}")
    public ResponseEntity<?> changePasswordForUser(@PathVariable String uuid, @RequestBody String password){
        userService.changePasswordForUser(uuid,password);
        return ResponseEntity.ok().body("Hasło zostało pomyślnie zmienione.");
    }

}
