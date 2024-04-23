package com.dahub.auth.config;

import com.dahub.auth.service.AuthTokenProviderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value(value = "${auth.secret}")
    private String secret;
    @Bean
    public AuthTokenProviderService jwtProvider() {
        return new AuthTokenProviderService(secret);
    }

}