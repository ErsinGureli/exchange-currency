package com.gureli.currencyexchange.repository;

import com.gureli.currencyexchange.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
