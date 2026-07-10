package com.ogbuilds.digitalbank.auth.exception;

import com.ogbuilds.digitalbank.auth.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;


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

    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<ApiResponse<Object>> handleAuthError(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.builder().success(false).message("Invalid email or password").build());
    }

}