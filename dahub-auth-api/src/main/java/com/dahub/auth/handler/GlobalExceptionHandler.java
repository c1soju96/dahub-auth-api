package com.dahub.auth.handler;

import com.dahub.auth.exception.ApplicationException;
import com.dahub.auth.dto.ErrorResponse;
import com.dahub.auth.enums.ErrorCode;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String LOG_CODE_FORMAT = "Class : {}, Code : {}, Message : {}";

    @ExceptionHandler(value = { ApplicationException.class })
    protected ResponseEntity<Object> handleCustomException(ApplicationException e) {


        ErrorCode errorCode = e.getErrorCode();
        String exceptionClassName = e.getClass().getSimpleName();
        String message = e.getMessage();

        log.error(LOG_CODE_FORMAT, exceptionClassName, errorCode, message);
        return ErrorResponse.toResponseObjectEntity(errorCode, message);

    }

    @ExceptionHandler(value = {ConstraintViolationException.class })
    protected ResponseEntity<Object> bindException(ConstraintViolationException e) {
        log.error("ExceptionCode : {} , ExceptionMessage : {}", ErrorCode.INVALID_INPUT_VALUE.getHttpStatus(), e.getMessage());
        return ErrorResponse.toResponseObjectEntity(ErrorCode.INVALID_INPUT_VALUE, e.getMessage());
    }


}