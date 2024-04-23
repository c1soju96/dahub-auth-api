package com.dahub.auth.exception;

import com.dahub.auth.enums.ErrorCode;

public class MemberNotFoundException extends MemberException{

    public static final ErrorCode CODE = ErrorCode.USER_NOT_FOUND;
    public static final String MESSAGE = ErrorCode.USER_NOT_FOUND.getDetail();
    public static final org.springframework.http.HttpStatus HttpStatus = ErrorCode.USER_NOT_FOUND.getHttpStatus();

    public MemberNotFoundException() {
        super(CODE, HttpStatus, MESSAGE);
    }
}
