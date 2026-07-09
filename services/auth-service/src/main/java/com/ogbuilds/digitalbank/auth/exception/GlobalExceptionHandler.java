package com.ogbuilds.digitalbank.auth.exception;

import com.ogbuilds.digitalbank.auth.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
public ResponseEntity<ApiResponse<Object>> handleValidation(
        org.springframework.web.bind.MethodArgumentNotValidException ex) {

    String message = ex.getBindingResult()
            .getFieldError()
            .getDefaultMessage();

    return ResponseEntity.badRequest()
            .body(
                    ApiResponse.builder()
                            .success(false)
                            .message(message)
                            .build()
            );
}

}