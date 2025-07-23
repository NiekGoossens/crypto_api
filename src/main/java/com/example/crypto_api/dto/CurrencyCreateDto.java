package com.example.crypto_api.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class CurrencyCreateDto {

    @NotBlank(message = "Ticker is required")
    @Size(max = 255, message = "Ticker must be at most 255 characters")
    private String ticker;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be at most 255 characters")
    private String name;

    @Min(value = 0, message = "Number of coins cannot be negative")
    private Long numberOfCoins;

    @Min(value = 0, message = "Market cap cannot be negative")
    private Long marketCap;
}