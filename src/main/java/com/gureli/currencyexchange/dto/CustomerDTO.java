package com.gureli.currencyexchange.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"accounts", "addresses"})
public class CustomerDTO {
    private Long id;
    private String fullName;
    private List<AccountDTO> accounts;
    private List<AddressDTO> addresses;
}
