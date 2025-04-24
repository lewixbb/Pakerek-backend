package com.pakerek.auth.user.service;

import com.pakerek.auth.user.model.User;
import com.pakerek.auth.user.model.dto.UserRegisterDto;
import com.pakerek.auth.user.model.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);

    UserResponseDto getUserById(Long id);

    UserResponseDto getOwnUser(String email);

    List<UserResponseDto> findAllUsers();

    UserResponseDto addUserWithDefaultRole(UserRegisterDto user);

    void activateUser(String uuid);

    void changePasswordRequest(String email);

    void changePasswordForUser(String uuid, String password);
}
