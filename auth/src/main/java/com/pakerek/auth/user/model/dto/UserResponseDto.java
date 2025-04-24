package com.pakerek.auth.user.model.dto;

import com.pakerek.auth.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private Role role;
    private boolean isNonLocked;
    private boolean isEnabled;
}
