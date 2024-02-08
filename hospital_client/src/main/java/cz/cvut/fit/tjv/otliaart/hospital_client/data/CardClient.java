package cz.cvut.fit.tjv.otliaart.hospital_client.data;

import cz.cvut.fit.tjv.otliaart.hospital_client.model.CardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


import java.util.Arrays;
import java.util.List;

@Component
public class CardClient {

    private final RestTemplate restTemplate;
    private static final String CARD_URL = "http://localhost:8080/cards";



    @Autowired
    public CardClient(RestTemplateBuilder restTemplateBuilder) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.restTemplate = restTemplateBuilder
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
                .build();
    }

    public List<CardDto> readAll() {
        return Arrays.asList(restTemplate.getForObject(CARD_URL, CardDto[].class));
    }

    public CardDto readById(Long id) {
        return restTemplate.getForObject(CARD_URL + "/" + id, CardDto.class);
    }

    public CardDto create(CardDto cardDto) {
        return restTemplate.postForObject(CARD_URL, cardDto, CardDto.class);
    }

    public void update(CardDto cardDto, Long id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CardDto> requestEntity = new HttpEntity<>(cardDto, headers);
        restTemplate.exchange(CARD_URL + "/" + id, HttpMethod.PATCH, requestEntity, Void.class);
    }

    public void delete(Long id) {
        restTemplate.delete(CARD_URL + "/" + id);
    }
}
