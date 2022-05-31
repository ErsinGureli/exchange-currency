package com.gureli.currencyexchange.service.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyExchangeRequest {
    private Long sendingAccountId;
    private String currencyCode;
    private BigDecimal amount;

}
