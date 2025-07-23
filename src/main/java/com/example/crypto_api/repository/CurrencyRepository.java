package com.example.crypto_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.crypto_api.model.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {

}
