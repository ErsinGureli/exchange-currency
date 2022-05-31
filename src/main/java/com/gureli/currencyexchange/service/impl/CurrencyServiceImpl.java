package com.gureli.currencyexchange.service.impl;

import com.gureli.currencyexchange.dto.CurrencyDTO;
import com.gureli.currencyexchange.entity.Currency;
import com.gureli.currencyexchange.exceptions.ResourceNotFoundException;
import com.gureli.currencyexchange.repository.CurrencyRepository;
import com.gureli.currencyexchange.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final ModelMapper modelMapper;

    @Override
    public CurrencyDTO getCurrencyByCode(String code) throws ResourceNotFoundException {
        Currency currency = currencyRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found by code: " + code));
        return modelMapper.map(currency, CurrencyDTO.class);
    }
}
