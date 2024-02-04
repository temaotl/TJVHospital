package org.example.hospital.data.dto;

import org.example.hospital.data.dto.convertes.toDto.PatientToDto;
import org.example.hospital.data.entity.Card;
import org.example.hospital.data.entity.Patient;
import org.example.hospital.data.entity.Procedure;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatientToDtoTest {

    @Autowired
    private PatientToDto patientToDto;

    @Test
    public void mustConvertFromEntityToDTO() {



        Procedure procedure1 = new Procedure(1L, "Procedure1", 30, new HashSet<>());
        Procedure procedure2 = new Procedure(2L, "Procedure2", 45, new HashSet<>());
        Set<Procedure> procedures = new HashSet<>();
        procedures.add(procedure1);
        procedures.add(procedure2);


        Patient patient = new Patient(10L, "John", "Doe", "123456", null, procedures);


         Card card = new Card(5L, "Diagnosis example", "Recommendations example", patient);
         patient.setCard(card);


        PatientDto patientDto = patientToDto.apply(patient);


        assertThat(patientDto.getPatientID()).isEqualTo(patient.getPatientID());
        assertThat(patientDto.getFirstName()).isEqualTo(patient.getFirstName());
        assertThat(patientDto.getLastName()).isEqualTo(patient.getLastName());
        assertThat(patientDto.getDocumentNumber()).isEqualTo(patient.getDocumentNumber());

        Set<Long> expectedProcedureIds = procedures.stream().map(Procedure::getProcedureID).collect(Collectors.toSet());
        assertThat(patientDto.getProcedureIds()).containsExactlyInAnyOrderElementsOf(expectedProcedureIds);
    }
}

