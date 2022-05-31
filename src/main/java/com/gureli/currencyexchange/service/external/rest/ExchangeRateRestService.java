package com.gureli.currencyexchange.service.external.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateRestService {

    private final ExchangeRateConfig config;
    private final ObjectMapper objectMapper;

    public BigDecimal getExchangeRate(String source, String destination) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", config.apiKey);

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(config.address)
                .queryParam("base", "{base}")
                .queryParam("symbols", "{symbols}")
                .encode()
                .toUriString();

        Map<String, String> params = new HashMap<>();
        params.put("base", destination);
        params.put("symbols", source);


        ResponseEntity<String> respEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class, params);

        String resp = respEntity.getBody();
        log.info(resp);

        BigDecimal amount = null;

        try {
            amount = (BigDecimal) new JSONObject(resp).getJSONObject("rates").get(source);
        } catch (ClassCastException e) {
            amount = BigDecimal.valueOf((Double) new JSONObject(resp).getJSONObject("rates").get(source));
        }
        return amount;
    }
}
