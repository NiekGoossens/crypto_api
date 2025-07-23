package com.example.crypto_api.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.crypto_api.dto.CurrencyCreateDto;
import com.example.crypto_api.dto.CurrencyUpdateDto;
import com.example.crypto_api.model.Currency;
import com.example.crypto_api.repository.CurrencyRepository;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public Currency createCurrency(CurrencyCreateDto dto) {

        // Check if currency with the same ticker already exists
        if (currencyRepository.existsById(dto.getTicker())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Currency with ticker " + dto.getTicker() + " already exists");
        }

        // Create a new Currency object
        Currency newCurrency = new Currency.Builder()
                .setTicker(dto.getTicker())
                .setName(dto.getName())
                .setNumberOfCoins(dto.getNumberOfCoins())
                .setMarketCap(dto.getMarketCap())
                .build();
        // Save the currency
        return currencyRepository.save(newCurrency);
    }

    public Currency getCurrency(String ticker) {
        return currencyRepository.findById(ticker).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Currency with ticker " + ticker + " not found"));
    }

    public List<Currency> getAllCurrencies() {

        // TODO Add pagination and sorting

        return currencyRepository.findAll();
    }

    public void deleteCurrency(String ticker) {
        if (!currencyRepository.existsById(ticker)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Currency with ticker " + ticker + " does not exist");
        }
        currencyRepository.deleteById(ticker);
    }

    public Currency updateCurrency(String ticker, CurrencyUpdateDto dto) {
        // Check if currency exists and retrieve it
        Currency existingCurrency = currencyRepository.findById(ticker)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Currency with ticker " + ticker + " not found"));

        Currency updatedCurrency = new Currency.Builder()
                .setTicker(existingCurrency.getTicker()) // Ticker cannot be changed
                .setName(dto.getName() != null ? dto.getName() : existingCurrency.getName())
                .setNumberOfCoins(dto.getNumberOfCoins() != null ? dto.getNumberOfCoins()
                        : existingCurrency.getNumberOfCoins())
                .setMarketCap(dto.getMarketCap() != null ? dto.getMarketCap() : existingCurrency.getMarketCap())
                .build();

        return currencyRepository.save(updatedCurrency);
    }

}
