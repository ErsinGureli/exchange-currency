package com.gureli.currencyexchange.service.impl;

import com.gureli.currencyexchange.dto.TransactionDTO;
import com.gureli.currencyexchange.entity.Account;
import com.gureli.currencyexchange.entity.Transaction;
import com.gureli.currencyexchange.repository.TransactionRepository;
import com.gureli.currencyexchange.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public TransactionDTO save(Transaction transaction) {
        return modelMapper.map(transactionRepository.save(transaction), TransactionDTO.class);
    }

    @Override
    @Transactional
    public void saveNewTransaction(Account sendingAccount, BigDecimal exchangeRate, BigDecimal amountToWithdraw) {
        Transaction transaction = new Transaction();
        transaction.setAccount(sendingAccount);
        transaction.setAmount(amountToWithdraw);
        transaction.setExchangeRate(exchangeRate);
        transactionRepository.save(transaction);
    }
}
