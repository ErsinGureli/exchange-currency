package com.gureli.currencyexchange.service.external.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExchangeRateRestServiceTest {

    @Autowired
    ExchangeRateRestService rateService;

    @Test
    void testGetExchangeRate() throws JsonProcessingException {
        BigDecimal exchangeRate = rateService.getExchangeRate("TRY", "USD");
        assertNotNull(exchangeRate);
    }

}