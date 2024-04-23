package com.dahub.auth.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.dahub.auth.enums.RoleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserResponseDto {
    private String userId;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    private String userName;
    private String email;
}
