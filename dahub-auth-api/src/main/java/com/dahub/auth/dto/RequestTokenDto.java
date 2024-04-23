package com.dahub.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTokenDto {

    private String userId;
    private String password;
    private String roleType;
}
