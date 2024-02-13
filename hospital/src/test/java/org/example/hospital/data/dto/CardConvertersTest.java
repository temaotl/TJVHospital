package org.example.hospital.data.dto;


import org.example.hospital.data.dto.convertes.toDto.CardToDto;
import org.example.hospital.data.dto.convertes.toEntity.CardDtoToEntity;
import org.example.hospital.data.entity.Card;
import org.example.hospital.data.entity.Patient;
import org.example.hospital.data.repository.CardRepository;
import org.example.hospital.data.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CardConvertersTest {

    @Autowired
    private CardToDto cardToDto;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardDtoToEntity cardDtoToEntity;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void mustConvertFromEntityToDTO() {
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setDocumentNumber("123456");
        patientRepository.save(patient);

        Card card = new Card();
        card.setDiagnosis("Diagnosis example");
        card.setDoctorRecommendations("Recommendations example");
        card.setPatient(patient);

        cardRepository.save(card);

        CardDto cardDto = cardToDto.apply(card);

        assertThat(cardDto.getCardID()).isEqualTo(card.getCardID());
        assertThat(cardDto.getDiagnosis()).isEqualTo(card.getDiagnosis());
        assertThat(cardDto.getDoctorRecommendations()).isEqualTo(card.getDoctorRecommendations());
    }

    @Test
    public void mustConvertFromDtoToEntity() {
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setDocumentNumber("123456");
        patientRepository.save(patient);

        CardDto cardDto = new CardDto();
        cardDto.setDiagnosis("Diagnosis example");
        cardDto.setDoctorRecommendations("Recommendations example");

        Card card = cardDtoToEntity.apply(cardDto);
        card.setPatient(patient);

        assertThat(card.getDiagnosis()).isEqualTo(cardDto.getDiagnosis());
        assertThat(card.getDoctorRecommendations()).isEqualTo(cardDto.getDoctorRecommendations());

        assertThat(card.getPatient().getFirstName()).isEqualTo(patient.getFirstName());
        assertThat(card.getPatient().getLastName()).isEqualTo(patient.getLastName());
        assertThat(card.getPatient().getDocumentNumber()).isEqualTo(patient.getDocumentNumber());
    }
}
