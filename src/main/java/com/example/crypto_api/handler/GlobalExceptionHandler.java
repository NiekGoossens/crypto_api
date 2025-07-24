package com.example.crypto_api.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.example.crypto_api.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle error in api execution
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDto> handleResponseStatusException(ResponseStatusException exception) {
        ErrorResponseDto error = new ErrorResponseDto(exception.getReason(), exception.getStatusCode().value());

        logger.warn("API error: {}", exception.getReason());

        return ResponseEntity.status(exception.getStatusCode()).body(error);
    }
}