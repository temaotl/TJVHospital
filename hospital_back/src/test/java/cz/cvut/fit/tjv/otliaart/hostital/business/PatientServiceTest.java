package cz.cvut.fit.tjv.otliaart.hostital.business;

import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Patient;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientServiceTest {
    @Autowired
    private PatientService patientService;

    @MockBean
    private PatientRepository patientRepository;

    Patient patient1, patient2;

    @BeforeEach
    public void setUp() {
        patient1 = new Patient();
        patient1.setPatientID(1L);
        patient1.setFirstName("Zeev");
        patient1.setLastName("Efimov");
        patient1.setDocumentNumber("123456789");

        patient2 = new Patient();
        patient2.setPatientID(2L);
        patient2.setFirstName("Viktor");
        patient2.setLastName("Evdakimov");
        patient2.setDocumentNumber("987654321");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient1));
        when(patientRepository.findById(2L)).thenReturn(Optional.of(patient2));
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient1, patient2));
    }

    @Test
    void testCreatePatient() {
        patientService.create(patient1);
        Mockito.verify(patientRepository, Mockito.times(1)).save(patient1);
    }

    @Test
    void testReadAllPatients() {
        List<Patient> actual = patientService.readAll();
        assertThat(actual).hasSize(2).containsExactlyInAnyOrder(patient1, patient2);
    }


    @Test
    void testUpdatePatient() {
        patient1.setFirstName("Johnathan");
        patientService.update(patient1, 1L);
        Mockito.verify(patientRepository, Mockito.times(1)).save(patient1);
    }

}
