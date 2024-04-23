package com.dahub.auth.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import com.dahub.auth.enums.ErrorCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class ErrorResponse {

    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    private final int status;
    private final String error;
    private final String code;
    private final String message;


    public static ResponseEntity<Object> toResponseObjectEntity(ErrorCode e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(e.getHttpStatus().value())
                        .error(e.getHttpStatus().name())
                        .code(e.name())
                        .message(e.getDetail())
                        .build()
                );
    }

    public static ResponseEntity<Object> toResponseObjectEntity(ErrorCode errorCode, String detailMessage) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .message(detailMessage)
                        .build()
                );
    }


}