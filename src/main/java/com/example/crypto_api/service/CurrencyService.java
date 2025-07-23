package com.example.crypto_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crypto_api.dto.CurrencyDto;
import com.example.crypto_api.model.Currency;
import com.example.crypto_api.repository.CurrencyRepository;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public Currency create(CurrencyDto dto) {

        // TODO Add check incase ticker is null or already exists

        Currency newCurrency = new Currency.Builder()
                .setTicker(dto.getTicker())
                .setName(dto.getName())
                .setNumberOfCoins(dto.getNumber_of_coins())
                .setMarketCap(dto.getMarket_cap())
                .build();
        return currencyRepository.save(newCurrency);
    }

    public Currency getCurrency(String ticker) {
        return currencyRepository.findById(ticker).orElse(null);
    }

    public List<Currency> getAllCurrencies() {

        // TODO Add pagination and sorting

        return currencyRepository.findAll();
    }

}
