package com.gureli.currencyexchange.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gureli.currencyexchange.entity.Account;
import com.gureli.currencyexchange.entity.Currency;
import com.gureli.currencyexchange.entity.Customer;
import com.gureli.currencyexchange.exceptions.InsufficientFundsException;
import com.gureli.currencyexchange.exceptions.ResourceNotFoundException;
import com.gureli.currencyexchange.repository.AccountRepository;
import com.gureli.currencyexchange.repository.CurrencyRepository;
import com.gureli.currencyexchange.repository.CustomerRepository;
import com.gureli.currencyexchange.service.model.CurrencyExchangeRequest;
import com.gureli.currencyexchange.service.model.CurrencyExchangeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CurrencyExchangeServiceImplTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CurrencyExchangeServiceImpl currencyExchangeService;

    @BeforeEach
    void setUp() {
        Account senderAccount = new Account();
        senderAccount.setId(1L);
        Currency lira = new Currency();
        lira.setId(1L);
        lira.setCode("TRY");
        lira.setDescription("Turkish Lira");
        currencyRepository.save(lira);
        Currency dollar = new Currency();
        dollar.setId(2L);
        dollar.setCode("USD");
        dollar.setDescription("US Dollar");
        currencyRepository.save(dollar);
        senderAccount.setBalance(new BigDecimal("7500"));
        Customer testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setFullName("Ersin Gureli");
        customerRepository.save(testCustomer);
        testCustomer.setAccounts(List.of(senderAccount));
        senderAccount.setCustomer(testCustomer);
        senderAccount.setCurrency(lira);
        accountRepository.save(senderAccount);

        Account receiverAccount = new Account();
        receiverAccount.setId(2L);
        receiverAccount.setCurrency(dollar);
        receiverAccount.setCustomer(testCustomer);
        receiverAccount.setBalance(BigDecimal.ONE);
        accountRepository.save(receiverAccount);
    }

    @Test
    void testExchangeCurrency() throws InsufficientFundsException, ResourceNotFoundException, JsonProcessingException {
        CurrencyExchangeRequest request = new CurrencyExchangeRequest();
        request.setAmount(new BigDecimal("200"));
        request.setCurrencyCode("USD");
        request.setSendingAccountId(1L);
        CurrencyExchangeResponse currencyExchangeResponse = currencyExchangeService.exchangeCurrency(request);
        Account account = accountRepository.findById(1L).get();
        Account account2 = accountRepository.findById(2L).get();
        assertNotNull(currencyExchangeResponse);
    }
}