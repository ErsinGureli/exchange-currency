package com.gureli.currencyexchange.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@EqualsAndHashCode(of = "id")
public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private BigDecimal exchangeRate;
    private AccountDTO account;
    private ZonedDateTime creationDate;
}
