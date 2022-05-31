package com.gureli.currencyexchange.service.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CurrencyExchangeResponse {
    private Long accountId;
    private String currencyCode;
    private BigDecimal currentAmount;
    private String message;
}
