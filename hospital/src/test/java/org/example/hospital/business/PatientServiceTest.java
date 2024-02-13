package org.example.hospital.business;

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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PatientServiceTest {


    @Autowired
    PatientService patientService;

    @Autowired
    ProcedureService procedureService;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ProcedureRepository procedureRepository;

    @Autowired
    PatientToDto patientToDto;



    private Procedure testProcedure;
    private Card testCard;


    @BeforeEach
    public  void  setUp()
    {
        testProcedure = new Procedure();
        testProcedure.setName("Procedure1");
        testProcedure.setDuration(30);
        testProcedure = procedureRepository.save(testProcedure);


        testCard = new Card();
        testCard.setDiagnosis("Initial Diagnosis");
        testCard.setDoctorRecommendations("Initial Recommendations");
        testCard  = cardRepository.save(testCard);
    }

    @Test
    public void testCreateReadUpdateDeleteCard() {

        Set<Procedure> myProcedure = new HashSet<>();
        myProcedure.add(testProcedure);

        Patient newPatient = new Patient();
        newPatient.setFirstName("Joe");
        newPatient.setLastName("Doe");
        newPatient.setDocumentNumber("123456");
        newPatient.setCard(testCard);
        newPatient.setProcedures(myProcedure);

        Patient savedPatient = patientService.create(newPatient);

        assertThat(savedPatient).isNotNull();
        assertThat(savedPatient.getDocumentNumber()).isEqualTo(newPatient.getDocumentNumber());

        Patient fetchedPatient = patientService.readById(savedPatient.getPatientID()).orElse(null);
        assertThat(fetchedPatient).isNotNull();
        assertThat(fetchedPatient.getCard().getCardID()).isEqualTo(testCard.getCardID());

        Procedure testProcedure1 = new Procedure();
        testProcedure1.setName("Procedure1");
        testProcedure1.setDuration(30);
        Procedure testProcedure2 = procedureRepository.save(testProcedure1);
        myProcedure.add(testProcedure2);

        fetchedPatient.setProcedures(myProcedure);


        patientService.update(patientToDto.apply(fetchedPatient), fetchedPatient.getPatientID());

        Patient updatedPatient = patientService.readById(savedPatient.getPatientID()).orElse(null);
        assertThat(updatedPatient).isNotNull();
        assertThat(updatedPatient.getProcedures()).isEqualTo(myProcedure);

        patientService.deleteById(updatedPatient.getPatientID());

        boolean cardExistsAfterDeletion = patientService.readById(savedPatient.getPatientID()).isPresent();
        assertThat(cardExistsAfterDeletion).isFalse();
    }

}
