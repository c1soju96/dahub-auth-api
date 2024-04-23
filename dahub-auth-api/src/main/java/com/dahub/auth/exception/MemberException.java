package com.dahub.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import com.dahub.auth.enums.ErrorCode;

public class MemberException extends ApplicationException {
    protected MemberException(ErrorCode errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }

    protected MemberException(ErrorCode errorCode, HttpStatus httpStatus, String message, BindingResult errors) {
        super(errorCode, httpStatus, message, errors);
    }
}
