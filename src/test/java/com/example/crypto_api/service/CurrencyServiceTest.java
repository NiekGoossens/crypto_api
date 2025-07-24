package com.example.crypto_api.service;

import com.example.crypto_api.dto.CurrencyCreateDto;
import com.example.crypto_api.dto.CurrencyUpdateDto;
import com.example.crypto_api.model.Currency;
import com.example.crypto_api.repository.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    CurrencyService currencyService;

    @Test
    void createCurrencyShouldSuccessfullyCreate() {

        // Create DTO to use in create request
        CurrencyCreateDto create = new CurrencyCreateDto();
        create.setTicker("TST");
        create.setName("TestCoin");
        create.setMarketCap(1000L);
        create.setNumberOfCoins(2000L);

        // Create new currency to use for comparison
        Currency newCurrency = new Currency.Builder()
                .setTicker("TST")
                .setName("TestCoin")
                .setNumberOfCoins(1000L)
                .setMarketCap(2000L)
                .build();

        // Mock repo behavior
        when(currencyRepository.existsById("TST")).thenReturn(false);
        when(currencyRepository.save(any(Currency.class))).thenReturn(newCurrency);

        Currency response = currencyService.createCurrency(create);

        // Assert
        assertEquals(newCurrency, response);
        verify(currencyRepository).existsById("TST");
        verify(currencyRepository).save(any(Currency.class));
    }

    @Test
    void createCurrencyShouldFailWhenItAlreadyExists() {

        // Create DTO to use in create request
        CurrencyCreateDto create = new CurrencyCreateDto();
        create.setTicker("TST");
        create.setName("TestCoin");
        create.setMarketCap(1000L);
        create.setNumberOfCoins(2000L);

        // Mock repo behavior
        when(currencyRepository.existsById("TST")).thenReturn(true);

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> currencyService.createCurrency(create)
        );

        // Assert
        assert (thrown.getStatusCode().value() == 409);
    }

    @Test
    void getCurrencyShouldGetCurrency() {
        Currency currency = new Currency.Builder()
                .setTicker("BTC")
                .setName("BitCoin")
                .setNumberOfCoins(16770000L)
                .setMarketCap(189580000000L)
                .build();

        when(currencyRepository.findById("BTC")).thenReturn(Optional.of(currency));

        Currency result = currencyService.getCurrency("BTC");

        assertEquals(result, currency);
    }

    @Test
    void getCurrencyShouldFailIfTickerDoesNotExist() {

        when(currencyRepository.findById("BTC")).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> currencyService.getCurrency("BTC")
        );

        assert (thrown.getStatusCode().value() == 404);
    }

    @Test
    void getAllCurrenciesShouldReturnPage() {
        Pageable pageable = Pageable.unpaged();
        Page<Currency> page = mock(Page.class);

        when(currencyRepository.findAll(pageable)).thenReturn(page);

        Page<Currency> result = currencyService.getAllCurrencies(pageable);

        assertThat(result).isEqualTo(page);
    }

    @Test
    void updateCurrencySuccessfully() {
        // Set existing entity in DB
        Currency existingCurrency = new Currency.Builder()
                .setTicker("TST")
                .setName("OldName")
                .setNumberOfCoins(100L)
                .setMarketCap(200L)
                .build();

        // Create DTO to use in create request
        CurrencyUpdateDto update = new CurrencyUpdateDto();
        update.setName("TestCoin");
        update.setMarketCap(1000L);
        update.setNumberOfCoins(2000L);

        // Expected updated currency (based on service logic)
        Currency expectedUpdated = new Currency.Builder()
                .setTicker("TST")
                .setName("TestCoin")
                .setNumberOfCoins(2000L)
                .setMarketCap(1000L)
                .build();

        when(currencyRepository.findById("TST")).thenReturn(Optional.of(existingCurrency));
        when(currencyRepository.save(any(Currency.class))).thenReturn(expectedUpdated);

        Currency result = currencyService.updateCurrency("TST", update);

        // Assert
        assertEquals(expectedUpdated.getTicker(), result.getTicker());
        assertEquals(expectedUpdated.getName(), result.getName());
        assertEquals(expectedUpdated.getNumberOfCoins(), result.getNumberOfCoins());
        assertEquals(expectedUpdated.getMarketCap(), result.getMarketCap());

        verify(currencyRepository).findById("TST");
        verify(currencyRepository).save(any(Currency.class));
    }


    @Test
    void updateCurrencyFailsWhenCurrencyNotFound() {
        CurrencyUpdateDto update = new CurrencyUpdateDto();
        update.setName("NotReal");

        // Mock repo: currency not found
        when(currencyRepository.findById("Fake")).thenReturn(Optional.empty());

        // Act + Assert: expect ResponseStatusException with NOT_FOUND
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> currencyService.updateCurrency("Fake", update));

        // Verify exception details
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Currency with ticker Fake not found"));
    }

    @Test
    void deleteCurrencyShouldDelete() {
        when(currencyRepository.existsById("ETH")).thenReturn(true);

        currencyService.deleteCurrency("ETH");

        verify(currencyRepository).deleteById("ETH");
    }

    @Test
    void deleteCurrencyShouldFailWhenItDoesntExist() {
        when(currencyRepository.existsById("ETH")).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> currencyService.deleteCurrency("ETH"));

        assertThat(ex.getStatusCode().value()).isEqualTo(404);
    }

}
