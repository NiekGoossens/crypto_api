package com.example.crypto_api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SuccessResponseDto {
    private String message;
    private int status;

    public SuccessResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
    }

}