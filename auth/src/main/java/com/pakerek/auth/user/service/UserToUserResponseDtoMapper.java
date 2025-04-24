package com.pakerek.auth.user.service;

import com.pakerek.auth.user.model.dto.UserResponseDto;
import com.pakerek.auth.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserToUserResponseDtoMapper {

    public UserResponseDto map (User user){
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.isNonLocked(),
                user.isEnabled());
    }

}
