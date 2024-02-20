package org.example.hospital.business;

import org.example.hospital.data.dto.PatientDto;
import org.example.hospital.data.dto.convertes.toDto.CardToDto;
import org.example.hospital.data.dto.convertes.toDto.PatientToDto;
import org.example.hospital.data.entity.Card;
import org.example.hospital.data.entity.Patient;
import org.example.hospital.data.entity.Procedure;
import org.example.hospital.data.repository.CardRepository;
import org.example.hospital.data.repository.ProcedureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardToDto cardToDto;

    @Autowired
    private PatientToDto patientToDto;

    private Procedure testProcedure;
    private Card testCard;

    @BeforeEach
    public void setUp() {
        // Создание и сохранение тестовой процедуры
        testProcedure = new Procedure();
        testProcedure.setName("Procedure1");
        testProcedure.setDuration(30);
        testProcedure = procedureRepository.save(testProcedure);

        // Создание и сохранение тестовой карты
        testCard = new Card();
        testCard.setDiagnosis("Initial Diagnosis");
        testCard.setDoctorRecommendations("Initial Recommendations");
      //  testCard = cardRepository.save(testCard);
    }

    @Test
    public void testCreateReadUpdateDeletePatient() {
        PatientDto patientDto = new PatientDto();
        patientDto.setFirstName("John");
        patientDto.setLastName("Doe");
        patientDto.setDocumentNumber("123456");
        patientDto.setCard(cardToDto.apply(testCard));
        Set<Long> procedureIds = new HashSet<>();
        procedureIds.add(testProcedure.getProcedureID());
        patientDto.setProcedureIds(procedureIds);


        PatientDto savedPatientDto = patientService.create(patientDto);
        assertNotNull(savedPatientDto);
        assertEquals("John", savedPatientDto.getFirstName());


        PatientDto fetchedPatientDto = patientToDto.apply(patientService.readById(savedPatientDto.getPatientID()).orElseThrow());
        assertNotNull(fetchedPatientDto);
        assertEquals("123456", fetchedPatientDto.getDocumentNumber());

        


    }
}

