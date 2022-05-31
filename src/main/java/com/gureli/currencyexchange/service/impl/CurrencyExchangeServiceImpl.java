package com.gureli.currencyexchange.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gureli.currencyexchange.dto.AccountDTO;
import com.gureli.currencyexchange.dto.CurrencyDTO;
import com.gureli.currencyexchange.dto.CustomerDTO;
import com.gureli.currencyexchange.entity.Account;
import com.gureli.currencyexchange.exceptions.InsufficientFundsException;
import com.gureli.currencyexchange.exceptions.ResourceNotFoundException;
import com.gureli.currencyexchange.service.AccountService;
import com.gureli.currencyexchange.service.CurrencyExchangeService;
import com.gureli.currencyexchange.service.CurrencyService;
import com.gureli.currencyexchange.service.TransactionService;
import com.gureli.currencyexchange.service.external.rest.ExchangeRateRestService;
import com.gureli.currencyexchange.service.model.CurrencyExchangeRequest;
import com.gureli.currencyexchange.service.model.CurrencyExchangeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Service
@Slf4j
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final TransactionService transactionService;
    private final ExchangeRateRestService rateService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public CurrencyExchangeResponse exchangeCurrency(CurrencyExchangeRequest currencyExchangeRequest) throws ResourceNotFoundException, InsufficientFundsException, JsonProcessingException {
        AccountDTO sendingAccount = accountService.getAccountById(currencyExchangeRequest.getSendingAccountId());
        CurrencyDTO currency = currencyService.getCurrencyByCode(currencyExchangeRequest.getCurrencyCode());

        CustomerDTO customer = sendingAccount.getCustomer();
        AccountDTO receiverAccount = customer.getAccounts().stream().filter(c -> currency.getId().equals(c.getCurrency().getId())).findAny()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer does not have an " + currency.getDescription() + " account"));

        BigDecimal exchangeRate = rateService.getExchangeRate(sendingAccount.getCurrency().getCode(), currencyExchangeRequest.getCurrencyCode());
        //BigDecimal exchangeRate = new BigDecimal("16.30");
        BigDecimal amountToWithdraw = currencyExchangeRequest.getAmount().multiply(exchangeRate);

        checkCurrentBalanceOfSendingAccount(sendingAccount, amountToWithdraw);
        saveCurrencyExchangeForSenderAccount(sendingAccount, exchangeRate, amountToWithdraw);
        saveCurrencyExchangeForReceiverAccount(currencyExchangeRequest.getAmount(), receiverAccount, exchangeRate);
        receiverAccount = accountService.getAccountById(receiverAccount.getId());

        return CurrencyExchangeResponse.builder()
                .accountId(receiverAccount.getId())
                .currentAmount(receiverAccount.getBalance())
                .message("SUCCESS")
                .currencyCode(currencyExchangeRequest.getCurrencyCode())
                .build();
    }

    private void checkCurrentBalanceOfSendingAccount(AccountDTO sendingAccount, BigDecimal amountToWithdraw) throws InsufficientFundsException {
        if (sendingAccount.getBalance().compareTo(amountToWithdraw) < 0) {
            throw new InsufficientFundsException(
                    "Sending account does not have enough funds. Current balance: " + sendingAccount.getBalance() + " needed amount: " + amountToWithdraw);
        }
    }

    private void saveCurrencyExchangeForSenderAccount(AccountDTO sendingAccount, BigDecimal exchangeRate, BigDecimal amountToWithdraw) throws ResourceNotFoundException, InsufficientFundsException {
        try {
            accountService.withdraw(sendingAccount.getId(), amountToWithdraw);
            transactionService.saveNewTransaction(modelMapper.map(sendingAccount, Account.class)
                    , exchangeRate, amountToWithdraw.negate());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void saveCurrencyExchangeForReceiverAccount(BigDecimal amount, AccountDTO receivingAccount, BigDecimal exchangeRate) throws ResourceNotFoundException, InsufficientFundsException {
        try {
            accountService.withdraw(receivingAccount.getId(), amount.negate());
            transactionService.saveNewTransaction(modelMapper.map(receivingAccount, Account.class)
                    , BigDecimal.ONE.divide(exchangeRate, RoundingMode.HALF_DOWN), amount);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
