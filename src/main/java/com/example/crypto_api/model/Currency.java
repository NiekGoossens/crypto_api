package com.example.crypto_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Currency {
    @Id
    private String ticker;
    private String name;
    private Long number_of_coins;
    private Long market_cap; // Market cap in USD

    protected Currency() {
    }

    private Currency(Builder builder) {
        this.ticker = builder.ticker;
        this.name = builder.name;
        this.number_of_coins = builder.number_of_coins;
        this.market_cap = builder.market_cap;
    }

    public String getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }

    public Long getNumberOfCoins() {
        return number_of_coins;
    }

    public Long getMarketCap() {
        return market_cap;
    }

    public static class Builder {
        private String ticker;
        private String name;
        private Long number_of_coins = 0L; 
        private Long market_cap = 0L; // Market cap in USD

        public Builder setTicker(String ticker) {
            this.ticker = ticker;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setNumberOfCoins(Long number_of_coins) {
            this.number_of_coins = number_of_coins;
            return this;
        }

        public Builder setMarketCap(Long market_cap) {
            this.market_cap = market_cap;
            return this;
        }

        public Currency build() {

            // Validate required fields

            return new Currency(this);
        }
    }

}
