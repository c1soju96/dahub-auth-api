package com.dahub.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private Object apiResult;
    private ResponseStatus responseStatus;

    public static ApiResponse success(Object apiResult) {
        return new ApiResponse(apiResult, ResponseStatus.builder()
                .statusCode(200)
                .returnMessage("success")
                .build());
    }

    public static ApiResponse fail(Object apiResult, String msg) {
        return new ApiResponse(apiResult, ResponseStatus.builder()
                .statusCode(400)
                .returnMessage(msg)
                .build());
    }

}