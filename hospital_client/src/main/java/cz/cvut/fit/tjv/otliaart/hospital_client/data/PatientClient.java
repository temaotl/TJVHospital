package cz.cvut.fit.tjv.otliaart.hospital_client.data;


import cz.cvut.fit.tjv.otliaart.hospital_client.model.PatientDto;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class PatientClient {
    private final RestTemplate restTemplate;
    private static final String PATIENT_URL = "http://localhost:8080/patients";

    @Autowired
    public PatientClient(RestTemplateBuilder restTemplateBuilder) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.restTemplate = restTemplateBuilder
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
                .build();
    }

    public List<PatientDto> readAll() {
        return Arrays.asList(restTemplate.getForObject(PATIENT_URL, PatientDto[].class));
    }

    public PatientDto readById(Long id) {
        return restTemplate.getForObject(PATIENT_URL + "/" + id, PatientDto.class);
    }

    public PatientDto create(PatientDto patientDto) {
        return restTemplate.postForObject(PATIENT_URL, patientDto, PatientDto.class);
    }

    public void update(PatientDto patientDto, Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PatientDto> requestEntity = new HttpEntity<>(patientDto, headers);
        restTemplate.exchange(PATIENT_URL + "/" + id, HttpMethod.PATCH, requestEntity, Void.class);
    }

    public void delete(Long id) {
        restTemplate.delete(PATIENT_URL + "/" + id);
    }
}