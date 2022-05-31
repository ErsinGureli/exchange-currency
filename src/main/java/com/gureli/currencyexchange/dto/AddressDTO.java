package com.gureli.currencyexchange.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = "customers")
public class AddressDTO {
    private Long id;
    private String fullAddress;
    private List<CustomerDTO> customers;
}
