package com.gureli.currencyexchange.service;

import com.gureli.currencyexchange.dto.TransactionDTO;
import com.gureli.currencyexchange.entity.Account;
import com.gureli.currencyexchange.entity.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface TransactionService {
    TransactionDTO save(Transaction transaction);

    void saveNewTransaction(Account sendingAccount, BigDecimal exchangeRate, BigDecimal amountToWithdraw);
}
