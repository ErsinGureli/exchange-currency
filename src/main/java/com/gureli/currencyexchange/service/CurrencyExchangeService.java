package com.gureli.currencyexchange.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gureli.currencyexchange.exceptions.InsufficientFundsException;
import com.gureli.currencyexchange.exceptions.ResourceNotFoundException;
import com.gureli.currencyexchange.service.model.CurrencyExchangeRequest;
import com.gureli.currencyexchange.service.model.CurrencyExchangeResponse;
import org.springframework.stereotype.Service;

@Service
public interface CurrencyExchangeService {
    public CurrencyExchangeResponse exchangeCurrency(CurrencyExchangeRequest currencyExchangeRequest) throws ResourceNotFoundException, InsufficientFundsException, JsonProcessingException;
}
