package com.example.crypto_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CurrencyDto {

    @NotBlank(message = "Ticker is required")
    @Size(max = 255, message = "Ticker must be at most 255 characters")
    private String ticker;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be at most 255 characters")
    private String name;

    @Min(value = 0, message = "Number of coins cannot be negative")
    private Long number_of_coins;

    @Min(value = 0, message = "Market cap cannot be negative")
    private Long market_cap;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumber_of_coins() {
        return number_of_coins;
    }

    public void setNumber_of_coins(Long number_of_coins) {
        this.number_of_coins = number_of_coins;
    }

    public Long getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(Long market_cap) {
        this.market_cap = market_cap;
    }

}