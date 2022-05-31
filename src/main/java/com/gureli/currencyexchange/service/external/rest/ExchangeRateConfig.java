package com.gureli.currencyexchange.service.external.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "exchange.rate.api")
@Getter
@Setter
public class ExchangeRateConfig {
    String address;
    String apiKey;
}
