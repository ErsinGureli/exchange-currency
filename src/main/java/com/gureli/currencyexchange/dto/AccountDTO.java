package com.gureli.currencyexchange.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = "transactions")
public class AccountDTO {
    private Long id;
    private BigDecimal balance;
    private CustomerDTO customer;
    private CurrencyDTO currency;
    private List<TransactionDTO> transactions;
}
