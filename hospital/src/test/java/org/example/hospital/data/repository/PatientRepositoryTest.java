package org.example.hospital.data.repository;

import org.example.hospital.data.entity.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void whenSavePatient_thenFindById() {
        Patient patient = new Patient(null, "John", "Doe", "123456", null, new HashSet<>());
        patient = entityManager.persistFlushFind(patient);

        Optional<Patient> foundPatient = patientRepository.findById(patient.getPatientID());
        assertThat(foundPatient).isPresent();
        assertThat(foundPatient.get().getFirstName()).isEqualTo(patient.getFirstName());
        assertThat(foundPatient.get().getLastName()).isEqualTo(patient.getLastName());
    }

    @Test
    public void whenUpdatePatient_thenFindUpdatedPatient() {
        Patient patient = new Patient(null, "Jane", "Doe", "654321", null, new HashSet<>());
        patient = entityManager.persistFlushFind(patient);

        patient.setFirstName("Jane Updated");
        patient.setLastName("Doe Updated");
        patientRepository.save(patient);

        Patient updatedPatient = entityManager.find(Patient.class, patient.getPatientID());
        assertThat(updatedPatient.getFirstName()).isEqualTo("Jane Updated");
        assertThat(updatedPatient.getLastName()).isEqualTo("Doe Updated");
    }

    @Test
    public void whenDeletePatient_thenNotFound() {
        Patient patient = new Patient(null, "Mike", "Smith", "789012", null, new HashSet<>());
        patient = entityManager.persistFlushFind(patient);

        patientRepository.delete(patient);

        Patient deletedPatient = entityManager.find(Patient.class, patient.getPatientID());
        assertThat(deletedPatient).isNull();
    }
}


