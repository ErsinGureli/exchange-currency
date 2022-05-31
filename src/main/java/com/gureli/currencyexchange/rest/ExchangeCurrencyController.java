package com.gureli.currencyexchange.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gureli.currencyexchange.exceptions.InsufficientFundsException;
import com.gureli.currencyexchange.exceptions.ResourceNotFoundException;
import com.gureli.currencyexchange.service.CurrencyExchangeService;
import com.gureli.currencyexchange.service.model.CurrencyExchangeRequest;
import com.gureli.currencyexchange.service.model.CurrencyExchangeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "exchange-currency")
@RequiredArgsConstructor
public class ExchangeCurrencyController {

    private final CurrencyExchangeService currencyExchangeService;

    @PostMapping
    public ResponseEntity<CurrencyExchangeResponse> exchangeCurrency(@RequestBody CurrencyExchangeRequest currencyExchangeRequest) throws InsufficientFundsException, ResourceNotFoundException, JsonProcessingException {
        return new ResponseEntity<>(currencyExchangeService.exchangeCurrency(currencyExchangeRequest), HttpStatus.OK);
    }
}
