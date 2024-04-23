package com.dahub.auth.filter;

import com.dahub.auth.service.AuthTokenProviderService;
import com.dahub.auth.utils.AuthToken;

import lombok.RequiredArgsConstructor;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenProviderService tokenProvider;
    @Value(value = "${apiKey}")
    private String apiKeyOrigin;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";
    private final static String HEADER_API_KEY = "ApiKey";
    private final static String USER_AGENT = "User-Agent";



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenStr = getAccessToken(request);
        if(StringUtils.hasText(tokenStr)){
            AuthToken token = tokenProvider.convertAuthToken(tokenStr);
            if(token.validateToken(tokenStr)){
                SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(token));
            }
        }else{
            String apiKey = getApiKey(request);
            if(StringUtils.hasText(apiKey)){
                apiKey = URLDecoder.decode(apiKey, StandardCharsets.UTF_8);
                if(apiKey.contains(apiKeyOrigin)){
                    Authentication authentication = tokenProvider.getAuthentication(getUserAgent(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    MDC.put("user", SecurityContextHolder.getContext().getAuthentication().getName());
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if(header == null) return null;
        if(header.startsWith(TOKEN_PREFIX)) return header.substring(TOKEN_PREFIX.length());
        return null;
    }

    private String getApiKey(HttpServletRequest request){
        String header = request.getHeader(HEADER_API_KEY);
        if(header == null) return null;
        return header;
    }

    private String getUserAgent(HttpServletRequest request){
        String header = request.getHeader(USER_AGENT);
        if(header == null) return null;
        return header;
    }
}
