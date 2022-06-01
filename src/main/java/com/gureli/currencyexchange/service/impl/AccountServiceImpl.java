package com.gureli.currencyexchange.service.impl;

import com.gureli.currencyexchange.dto.AccountDTO;
import com.gureli.currencyexchange.entity.Account;
import com.gureli.currencyexchange.exceptions.InsufficientFundsException;
import com.gureli.currencyexchange.exceptions.ResourceNotFoundException;
import com.gureli.currencyexchange.repository.AccountRepository;
import com.gureli.currencyexchange.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    @Override
    public AccountDTO getAccountById(Long id) throws ResourceNotFoundException {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found by id: " + id));
        mapper.getConfiguration().setPreferNestedProperties(false);
        return mapper.map(account, AccountDTO.class);
    }

    @Override
    @Transactional
    public void withdraw(Long id, BigDecimal amount) throws ResourceNotFoundException, InsufficientFundsException {
        Account account = accountRepository.findOneForUpdate(id).orElseThrow(() -> new ResourceNotFoundException("Account not found by id: " + id));
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }
}
