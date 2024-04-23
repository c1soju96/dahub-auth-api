package com.dahub.auth.controller;

import com.dahub.auth.dto.RequestTokenDto;
import com.dahub.auth.dto.ResponseTokenDto;
import com.dahub.auth.dto.ApiResponse;
import com.dahub.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "getAccessToken", description = "get AccessToken")
    @PostMapping("/auth/authentication")
    public ResponseEntity<ApiResponse> getAccessToken(@RequestBody RequestTokenDto requestTokenDto) throws Exception {
        String accessToken = authService.getAccessToken(requestTokenDto);
        return ResponseEntity.ok(ApiResponse.success(ResponseTokenDto.builder().token(accessToken).build()));
    }

    @Operation(summary = "regenerateAccessToken", description = "regenerate AccessToken")
    @PostMapping("/auth/authentication/refresh")
    public ResponseEntity<ApiResponse> regenerateAccessToken(@RequestBody RequestTokenDto requestTokenDto) throws Exception {
        String accessToken = authService.reGenerateAccessToken(requestTokenDto);
        return ResponseEntity.ok(ApiResponse.success(ResponseTokenDto.builder().token(accessToken).build()));
    }

}
