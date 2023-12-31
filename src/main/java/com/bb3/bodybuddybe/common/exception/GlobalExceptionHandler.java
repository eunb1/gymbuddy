package com.bb3.bodybuddybe.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // javax.validation.Valid binding error 발생한 경우
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error("handleBindException", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.toString(), e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Request Param 으로 전달된 값이 Enum 으로 binding 되지 못한 경우
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Request Body 가 예상한 형식이나 구조가 아닌 경우
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("handleHttpMessageNotReadableException", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // 지원하지 않는 HTTP method 호출하는 경우
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED.toString(), e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    // 비즈니스 로직 실행 중 에러 발생하는 경우
    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("CustomException", e);
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode().getCode(), e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    // 그 외 예외 발생하는 경우
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
