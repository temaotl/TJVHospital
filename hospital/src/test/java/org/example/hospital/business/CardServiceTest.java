package org.example.hospital.business;

import org.example.hospital.data.dto.CardDto;
import org.example.hospital.data.dto.convertes.toDto.CardToDto;
import org.example.hospital.data.entity.Card;
import org.example.hospital.data.entity.Patient;
import org.example.hospital.data.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CardServiceTest {

    @Autowired
    private CardService cardService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CardToDto cardToDto;

    private Patient testPatient;

    @BeforeEach
    public void setUp() {
        testPatient = new Patient();
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setDocumentNumber("123456789");
        testPatient = patientRepository.save(testPatient);
    }

    @Test
    public void testCreateReadUpdateDeleteCard() {
        Card newCard = new Card();
        newCard.setDiagnosis("Initial Diagnosis");
        newCard.setDoctorRecommendations("Initial Recommendations");
        newCard.setPatient(testPatient);

        CardDto savedCard = cardService.create(cardToDto.apply(newCard));

        assertThat(savedCard).isNotNull();
        assertThat(savedCard.getDiagnosis()).isEqualTo("Initial Diagnosis");

        Card fetchedCard = cardService.readById(savedCard.getCardID()).orElse(null);
        assertThat(fetchedCard).isNotNull();
        assertThat(fetchedCard.getPatient().getPatientID()).isEqualTo(testPatient.getPatientID());

        fetchedCard.setDiagnosis("Updated Diagnosis");
        cardService.update(cardToDto.apply(fetchedCard), fetchedCard.getCardID());

        Card updatedCard = cardService.readById(savedCard.getCardID()).orElse(null);
        assertThat(updatedCard).isNotNull();
        assertThat(updatedCard.getDiagnosis()).isEqualTo("Updated Diagnosis");

        cardService.deleteById(updatedCard.getCardID());

        boolean cardExistsAfterDeletion = cardService.readById(savedCard.getCardID()).isPresent();
        assertThat(cardExistsAfterDeletion).isFalse();
    }
}
