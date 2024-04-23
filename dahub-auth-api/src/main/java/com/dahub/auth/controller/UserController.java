package com.dahub.auth.controller;

import com.dahub.auth.dto.ApiResponse;
import com.dahub.auth.service.UserService;
import com.dahub.auth.utils.SecurityUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "getUser", description = "get User")
    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getUser() {
        return ResponseEntity.ok(ApiResponse.success(userService.getUser(SecurityUtils.currentUserId())));
    }

}