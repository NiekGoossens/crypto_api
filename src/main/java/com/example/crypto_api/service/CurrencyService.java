package com.example.crypto_api.service;

import org.slf4j.LoggerFactory;
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

import org.slf4j.Logger;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    // Create new currency
    public Currency createCurrency(CurrencyCreateDto dto) {

        // Check if currency with the same ticker already exists
        if (currencyRepository.existsById(dto.getTicker())) {
            logger.info("Could not create new currency with ticker: {}, as a currency with this ticker already exists", dto.getTicker());
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
        Currency saved = currencyRepository.save(newCurrency);
        logger.info("Successfully created new currency: {}", saved);

        return saved;
    }

    // Get specific currency
    public Currency getCurrency(String ticker) {
        Currency currency = currencyRepository.findById(ticker).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Currency with ticker " + ticker + " not found"));
        logger.info("Successfully retrieved currency with ticker: {}", ticker);

        return currency;
    }

    // Get all currencies using pagination and sort
    public Page<Currency> getAllCurrencies(Pageable pageable) {
        logger.info("Retrieving all currencies using the following options: {}", pageable);
        return currencyRepository.findAll(pageable);
    }

    // Delete specific currency
    public void deleteCurrency(String ticker) {
        if (!currencyRepository.existsById(ticker)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Currency with ticker " + ticker + " does not exist");
        }
        currencyRepository.deleteById(ticker);
        logger.info("Deleted currency with ticker: {}", ticker);
    }

    // Update specific currency
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

        Currency updated = currencyRepository.save(updatedCurrency);
        logger.info("Updated currency: {}", updated);

        return updated;
    }

}
