package com.dahub.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import com.dahub.auth.enums.ErrorCode;

@Getter
public class ApplicationException extends RuntimeException{

    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;
    private BindingResult errors;

    protected ApplicationException(ErrorCode errorCode, HttpStatus httpStatus, String message) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    protected ApplicationException(ErrorCode errorCode, HttpStatus httpStatus, String message, BindingResult errors) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errors = errors;
    }

}