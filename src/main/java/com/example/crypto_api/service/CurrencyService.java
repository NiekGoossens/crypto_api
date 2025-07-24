package com.example.crypto_api.service;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.crypto_api.dto.CurrencyCreateDto;
import com.example.crypto_api.dto.CurrencyUpdateDto;
import com.example.crypto_api.model.Currency;
import com.example.crypto_api.repository.CurrencyRepository;

import org.springframework.data.domain.Pageable;

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

    public Page<Currency> getAllCurrencies(Pageable pageable) {

        // TODO Add pagination and sorting

        return currencyRepository.findAll(pageable);
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


        // Update the fields based on the DTO, keeping existing values if not provided
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
