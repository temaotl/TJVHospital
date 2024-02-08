package cz.cvut.fit.tjv.otliaart.hostital.repository;

import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Patient;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Card;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Procedure;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void clearDB() {
        patientRepository.deleteAll();
    }

    @Test
    void testSaveAndFindById() {
        Card card = new Card(null, "Diagnosis1", "Recommendation1", null);
        Procedure procedure1 = new Procedure();
        Procedure procedure2 = new Procedure();

        Patient patient = new Patient(null, "John", "Doe", "123456", card, Arrays.asList(procedure1, procedure2));


        Patient savedPatient = patientRepository.save(patient);


        assertThat(savedPatient.getPatientID()).isNotNull();


        Optional<Patient> foundPatient = patientRepository.findById(savedPatient.getPatientID());


        assertThat(foundPatient).isPresent();
        assertThat(foundPatient.get().getFirstName()).isEqualTo("John");
        assertThat(foundPatient.get().getCard().getDiagnosis()).isEqualTo("Diagnosis1");
        assertThat(foundPatient.get().getProcedures()).hasSize(2);
    }

    @Test
    void testDeletePatient() {
        Card card = new Card(null, "Diagnosis2", "Recommendation2", null);
        Patient patient = new Patient(null, "Jane", "Doe", "654321", card, null);
        Patient savedPatient = patientRepository.save(patient);


        patientRepository.deleteById(savedPatient.getPatientID());


        Optional<Patient> foundPatient = patientRepository.findById(savedPatient.getPatientID());
        assertThat(foundPatient).isEmpty();
    }
}
