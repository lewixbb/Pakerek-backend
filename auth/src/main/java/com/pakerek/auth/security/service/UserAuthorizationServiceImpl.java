package com.pakerek.auth.security.service;

import com.pakerek.auth.security.jwt.JwtUtils;
import com.pakerek.auth.security.model.dto.request.LoginRequest;
import com.pakerek.auth.security.model.dto.response.LoginResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAuthorizationServiceImpl implements UserAuthorizationService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> authenticateUser(HttpServletResponse response,LoginRequest loginRequest){
        Authentication authentication;
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        //opcjonalnie dodalem generowanie ciasteczka z jwt tokenem

        if(authentication.isAuthenticated()){
            Cookie cookie = jwtUtils.generateCookie("jwt", jwtToken);
            response.addCookie(cookie);
        }

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok(new LoginResponse(userDetails.getUsername(),roles, jwtToken));

    }
}
