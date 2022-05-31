package com.gureli.currencyexchange.service;

import com.gureli.currencyexchange.dto.CurrencyDTO;
import com.gureli.currencyexchange.entity.Currency;
import com.gureli.currencyexchange.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface CurrencyService {

    CurrencyDTO getCurrencyByCode(String code) throws ResourceNotFoundException;
}
