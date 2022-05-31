package com.gureli.currencyexchange.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class CurrencyDTO {
    private Long id;
    private String code;
    private String description;
}
