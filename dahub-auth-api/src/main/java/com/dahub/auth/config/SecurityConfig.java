package com.dahub.auth.config;

import com.dahub.auth.service.AuthTokenProviderService;
import com.dahub.auth.exception.RestAuthenticationEntryPoint;
import com.dahub.auth.filter.TokenAuthenticationFilter;
import com.dahub.auth.handler.TokenAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig{

    private final AuthTokenProviderService tokenProvider;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> {
            web.ignoring()
                    .antMatchers(
                            "/assets/**", "/swagger-resources/**","/swagger-ui/**","/v3/api-docs", "/api/v1/auth/authentication",
                            "/api/v1/auth/authentication/refresh","/h2-console/**", "/api/v1/docs/**"
                            );
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests((authz) -> authz
                    .antMatchers("/api/v1/auth/authentication", "/api/v1/auth/authentication/refresh").permitAll()
                    .anyRequest().authenticated()
                    ).exceptionHandling()
                    .and()
                    .formLogin().disable()
                    .cors().disable()
                    .csrf().disable()
                    .httpBasic().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//스프링 시큐리티가 HttpSession을 생성하지 않고, SecurityContext를 획득하는 데 사용하지 않음
                    .and()
                    .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) //jwt 토큰 필터 사용
                    .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint()) // 인증되지 않은 사용자 접근 시
                    .accessDeniedHandler(tokenAccessDeniedHandler)
                    .and()
                .httpBasic(withDefaults());
        return http.build();
    }


    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



}