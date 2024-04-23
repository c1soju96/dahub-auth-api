package com.dahub.auth.service;

import com.dahub.auth.dto.RequestTokenDto;
import com.dahub.auth.entity.UserEntity;
import com.dahub.auth.enums.RoleType;
import com.dahub.auth.exception.AuthForbiddenException;
import com.dahub.auth.exception.InvalidPasswordException;
import com.dahub.auth.utils.AuthToken;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class AuthService {

    @Value("${auth.tokenExpiry}")
    private long expirePeriod;
    @Value("${auth.refreshTokenExpiry}")
    private long refreshTokenExpiry;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final AuthTokenProviderService tokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserDetails getUserDetail(String id, String password, String roleType){
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(id);
        checkRole(userDetails, roleType);
        checkPassWord(password, userDetails.getPassword());
        return userDetails;
    }

    public void checkRole(UserDetails userDetails, String roleType){
        userDetails.getAuthorities().stream()
                .filter(grantedAuthority -> RoleType.isRightRole(roleType, grantedAuthority.getAuthority()))
                .findAny()
                .orElseThrow(AuthForbiddenException::new);
    }

    public void checkPassWord(String rawPassWord, String encodePassWord){
        if(!passwordEncoder.matches(rawPassWord, encodePassWord)) throw new InvalidPasswordException();
    }

    public String getAccessToken(RequestTokenDto requestTokenDto){
        UserDetails userDetails = getUserDetail(requestTokenDto.getUserId(), requestTokenDto.getPassword(), requestTokenDto.getRoleType());
        AuthToken authToken = generateAccessToken(userDetails, requestTokenDto);
        isAvailableRefreshToken(userDetails, requestTokenDto);
        return authToken.getToken();
    }

    public String reGenerateAccessToken(RequestTokenDto requestTokenDto){
        UserDetails userDetails = getUserDetail(requestTokenDto.getUserId(), requestTokenDto.getPassword(), requestTokenDto.getRoleType());
        return isAvailableRefreshToken(userDetails, requestTokenDto).getToken();
    }


    public AuthToken generateAccessToken(UserDetails userDetails,RequestTokenDto requestTokenDto){
        AuthToken authToken = tokenProvider.createAuthToken(userDetails.getUsername(), RoleType.USER.getCode(), new Date(new Date().getTime() + expirePeriod));
        authenticationManager.authenticate(tokenProvider.getAuthentication(userDetails, requestTokenDto));
        return authToken;
    }

    @Transactional
    public AuthToken isAvailableRefreshToken(UserDetails userDetails, RequestTokenDto requestTokenDto){
        AuthToken token = null;
        UserEntity user = customUserDetailsService.getUser(userDetails.getUsername());
        AuthToken refreshToken = tokenProvider.createAuthToken(userDetails.getUsername(), RoleType.USER.getCode(),new Date(new Date().getTime() + refreshTokenExpiry));
        if(user.getRefreshToken() !=null) {
            Claims claims = tokenProvider.reGenerateRefreshToken(user.getRefreshToken());
            if (claims != null) {
                user.setRefreshToken(refreshToken.getToken());
                token = generateAccessToken(userDetails, requestTokenDto);
            }
        }else{
            user.setRefreshToken(refreshToken.getToken());
            token = refreshToken;
        }
        return token;
    }
}
