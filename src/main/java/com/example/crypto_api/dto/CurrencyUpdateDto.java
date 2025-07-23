package com.example.crypto_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CurrencyUpdateDto {

    @Size(max = 255, message = "Name must be at most 255 characters")
    private String name;

    @Min(value = 0, message = "Number of coins cannot be negative")
    private Long numberOfCoins;

    @Min(value = 0, message = "Market cap cannot be negative")
    private Long marketCap;
}