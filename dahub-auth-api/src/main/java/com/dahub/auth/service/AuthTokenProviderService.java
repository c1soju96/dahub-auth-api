package com.dahub.auth.service;

import com.dahub.auth.dto.RequestTokenDto;
import com.dahub.auth.utils.AuthToken;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

@Slf4j
public class AuthTokenProviderService {
    private final Key key;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    public AuthTokenProviderService(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AuthToken createAuthToken(String id, String role, Date expiry) {
        return new AuthToken(id, role, expiry, key);
    }

    public Claims reGenerateRefreshToken(String token) {
        try {
            log.info("Refresh Token 만료 여부 검증.");
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.info("Refresh Token이 만료 되었습니다.");
            return null;
        } catch (Exception e){
            log.info("Refresh Token이 만료 검증 중 에러 발생.");
            log.error("Refresh Token이 만료 검증 중, error msg = {}",e.getMessage());
            return null;
        }
    }

    public Authentication getAuthentication(UserDetails userDetails, RequestTokenDto requestTokenDto) {
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), requestTokenDto.getPassword(), userDetails.getAuthorities());
    }

    public Authentication getAuthentication(String sellerId){
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(sellerId);
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    public Authentication getAuthentication(AuthToken authToken) {
        Claims claims = authToken.getClaims();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
        User principal = new User(claims.getSubject(), authToken.getToken(), userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(principal.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }


    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }


    public Authentication getCurrentUser(String token){
        Claims body = getClaims(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(body.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }



}