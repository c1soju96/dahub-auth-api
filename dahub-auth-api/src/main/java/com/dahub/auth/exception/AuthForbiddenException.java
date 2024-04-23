package com.dahub.auth.exception;

import com.dahub.auth.enums.ErrorCode;

public class AuthForbiddenException extends AuthException{

    public static final ErrorCode CODE = ErrorCode.FORBIDDEN_MEMBER;
    public static final String MESSAGE = ErrorCode.FORBIDDEN_MEMBER.getDetail();
    public static final org.springframework.http.HttpStatus HttpStatus = ErrorCode.FORBIDDEN_MEMBER.getHttpStatus();

    public AuthForbiddenException() {
        super(CODE, HttpStatus, MESSAGE);
    }
}
