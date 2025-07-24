package com.example.crypto_api.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.example.crypto_api.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle error in api execution
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDto> handleResponseStatusException(ResponseStatusException exception) {
        ErrorResponseDto error = new ErrorResponseDto(exception.getReason(), exception.getStatusCode().value());
        return ResponseEntity.status(exception.getStatusCode()).body(error);
    }
}