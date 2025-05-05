package com.pakerek.auth.user.service;

import com.pakerek.auth.user.model.Role;
import com.pakerek.auth.user.model.User;
import com.pakerek.auth.user.model.dto.UserPutRequestDto;
import com.pakerek.auth.user.model.dto.UserRegisterDto;
import com.pakerek.auth.user.model.dto.UserResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);

    UserResponseDto getUserById(Long id);

    UserResponseDto changeUserData(Long id, UserPutRequestDto userPutRequestDto);

    UserResponseDto getOwnUser(String email);

    Page<UserResponseDto> findUsersWithPaginationAndFilter(int page, int size, String sortBy, String order, String filter);

    UserResponseDto addUserWithDefaultRole(UserRegisterDto user);

    void activateUser(String uuid);

    void changePasswordRequest(String email);

    void changePasswordForUser(String uuid, String password);

    List<Role>permissionList();
}
