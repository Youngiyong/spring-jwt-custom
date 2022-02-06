package com.user.auth.advice;

import com.user.auth.exception.ErrorCode;
import com.user.auth.exception.InvalidParameterException;
import com.user.auth.exception.UserAuthServerException;
import com.user.auth.model.response.CustomErrorResponse;
import com.user.auth.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(InvalidParameterException.class)
    protected ResponseEntity<CustomErrorResponse> handleInvalidParameterException(InvalidParameterException e) {
        log.error("handleInvalidParameterException", e);
        ErrorCode errorCode = e.getErrorCode();

        final CustomErrorResponse response = CustomErrorResponse
                .builder()
                .errors(e.getErrors());

        return new ResponseEntity<>(response, HttpStatus.resolve(errorCode.getStatus()));
    }

    @ExceptionHandler(UserAuthServerException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(UserAuthServerException e) {
        log.error("handleUserAuthServerExcepiton", e);
        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = ErrorResponse
                .builder()
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.resolve(errorCode.getStatus()));
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMisMatchException(MethodArgumentTypeMismatchException e){
        log.error("handleMethodArgumentTypeMisMatchException", e);


        ErrorResponse response = ErrorResponse
                .builder()
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleException", e);

        ErrorResponse response = ErrorResponse
                .builder()
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}