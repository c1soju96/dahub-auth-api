package com.dahub.auth.utils;

import com.dahub.auth.enums.RoleType;
import com.dahub.auth.exception.AuthForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) return "";
        return (String) authentication.getName();
    }

    public static RoleType getAuthorization(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .map(RoleType::of)
                .orElseThrow(AuthForbiddenException::new);
    }

}
