package com.gureli.currencyexchange.service;

import com.gureli.currencyexchange.dto.AccountDTO;
import com.gureli.currencyexchange.entity.Account;
import com.gureli.currencyexchange.exceptions.InsufficientFundsException;
import com.gureli.currencyexchange.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface AccountService {

    AccountDTO getAccountById(Long id) throws ResourceNotFoundException;

    void withdraw(Long id, BigDecimal amount) throws ResourceNotFoundException, InsufficientFundsException;
}
