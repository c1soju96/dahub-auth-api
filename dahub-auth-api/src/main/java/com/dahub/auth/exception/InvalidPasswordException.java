package com.dahub.auth.exception;

import com.dahub.auth.enums.ErrorCode;

public class InvalidPasswordException extends AuthException{
    public static final ErrorCode CODE = ErrorCode.INVALID_PASSWORD;
    public static final String MESSAGE = ErrorCode.INVALID_PASSWORD.getDetail();
    public static final org.springframework.http.HttpStatus HttpStatus = ErrorCode.INVALID_PASSWORD.getHttpStatus();

    public InvalidPasswordException() {
        super(CODE, HttpStatus, MESSAGE);
    }
}