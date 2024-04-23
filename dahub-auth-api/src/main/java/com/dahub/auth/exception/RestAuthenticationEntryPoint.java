package com.dahub.auth.exception;

import com.dahub.auth.dto.ErrorResponse;
import com.dahub.auth.enums.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        log.error("Responding with unauthorized error. Message := {}", authException.getMessage());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(ErrorCode.FAIL_AUTHORIZATION.getHttpStatus().value());
        ResponseEntity<Object> objectResponseEntity = ErrorResponse.toResponseObjectEntity(ErrorCode.FAIL_AUTHORIZATION);
        String result = objectMapper.writeValueAsString(objectResponseEntity);
        response.getWriter().write(result);
    }
}