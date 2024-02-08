package cz.cvut.fit.tjv.otliaart.hospital_client.data;

import cz.cvut.fit.tjv.otliaart.hospital_client.model.PatientDto;
import cz.cvut.fit.tjv.otliaart.hospital_client.model.ProcedureDto;
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
public class ProcedureClient {
    private final RestTemplate restTemplate;
    private static final String PROCEDURE_URL = "http://localhost:8080/procedures";

    @Autowired
    public ProcedureClient(RestTemplateBuilder restTemplateBuilder) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.restTemplate = restTemplateBuilder
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
                .build();
    }

    public List<ProcedureDto> readAll() {
        return Arrays.asList(restTemplate.getForObject(PROCEDURE_URL, ProcedureDto[].class));
    }

    public ProcedureDto readById(Long id) {
        return restTemplate.getForObject(PROCEDURE_URL + "/" + id, ProcedureDto.class);
    }

    public ProcedureDto create(ProcedureDto procedureDto) {
        return restTemplate.postForObject(PROCEDURE_URL, procedureDto, ProcedureDto.class);
    }

    public void update(ProcedureDto procedureDto, Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProcedureDto> requestEntity = new HttpEntity<>(procedureDto, headers);
        restTemplate.exchange(PROCEDURE_URL + "/" + id, HttpMethod.PATCH, requestEntity, Void.class);
    }

    public void delete(Long id) {
        restTemplate.delete(PROCEDURE_URL + "/" + id);
    }

    public List<PatientDto> getPatientsByProcedureId(Long procedureId) {
        String url = PROCEDURE_URL + "/" + procedureId + "/patients";
        return Arrays.asList(restTemplate.getForObject(url, PatientDto[].class));
    }

}
