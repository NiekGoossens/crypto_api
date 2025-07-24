package com.example.crypto_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    private String ticker;
    private String name;
    private Long numberOfCoins;
    private Long marketCap; // Market cap in USD

    protected Currency() {
    }

    private Currency(Builder builder) {
        this.ticker = builder.ticker;
        this.name = builder.name;
        this.numberOfCoins = builder.numberOfCoins;
        this.marketCap = builder.marketCap;
    }

    public static class Builder {
        private String ticker;
        private String name;
        private Long numberOfCoins = 0L;
        private Long marketCap = 0L; // Market cap in USD

        public Builder setTicker(String ticker) {
            this.ticker = ticker;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setNumberOfCoins(Long numberOfCoins) {
            this.numberOfCoins = numberOfCoins;
            return this;
        }

        public Builder setMarketCap(Long marketCap) {
            this.marketCap = marketCap;
            return this;
        }

        public Currency build() {

            // Validate required fields

            return new Currency(this);
        }
    }
}
