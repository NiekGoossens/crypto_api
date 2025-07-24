package com.example.crypto_api.controller;

import com.example.crypto_api.dto.SuccessResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

import com.example.crypto_api.dto.CurrencyCreateDto;
import com.example.crypto_api.dto.CurrencyUpdateDto;
import com.example.crypto_api.model.Currency;
import com.example.crypto_api.service.CurrencyService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@Validated
@RequestMapping("/api/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @PostMapping
    public Currency createCurrency(@Valid @RequestBody CurrencyCreateDto dto) {
        return currencyService.createCurrency(dto);
    }

    @GetMapping
    public Page<Currency> getAllCurrencies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "ticker") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return currencyService.getAllCurrencies(pageable);
    }

    @GetMapping("/{ticker}")
    public Currency getCurrency(@PathVariable("ticker") String ticker) {
        return currencyService.getCurrency(ticker);
    }

    @PutMapping("/{ticker}")
    public Currency updateCurrency(@PathVariable("ticker") String ticker, @Valid @RequestBody CurrencyUpdateDto dto) {
        return currencyService.updateCurrency(ticker, dto);
    }

    @DeleteMapping("/{ticker}")
    public ResponseEntity<SuccessResponseDto> deleteCurrency(@PathVariable String ticker) {
        currencyService.deleteCurrency(ticker);
        return ResponseEntity.ok(new SuccessResponseDto("Successfully deleted " + ticker, 200));
    }

}
