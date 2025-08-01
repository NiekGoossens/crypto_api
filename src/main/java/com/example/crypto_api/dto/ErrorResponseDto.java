package com.example.crypto_api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponseDto {
    private String message;
    private int status;

    public ErrorResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
    }

}
