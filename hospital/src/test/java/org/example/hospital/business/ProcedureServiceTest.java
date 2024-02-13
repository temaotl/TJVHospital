package org.example.hospital.business;

import org.example.hospital.data.dto.ProcedureDto;
import org.example.hospital.data.dto.convertes.toDto.ProcedureToDto;
import org.example.hospital.data.entity.Patient;
import org.example.hospital.data.entity.Procedure;
import org.example.hospital.data.repository.PatientRepository;
import org.example.hospital.data.repository.ProcedureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;





import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
public class ProcedureServiceTest {

    @Autowired
    private ProcedureService procedureService;

    @Autowired
    private ProcedureToDto procedureToDto;

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void shouldUpdateProcedure() {
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setDocumentNumber("123456789");
        patientRepository.save(patient);

        Procedure procedure = new Procedure();
        procedure.setName("MRI Scan");
        procedure.setDuration(60);
        procedure.getPatients().add(patient);
        procedureRepository.save(procedure);

        ProcedureDto procedureDto = procedureToDto.apply(procedure);
        procedureDto.setName("Updated Name");
        procedureDto.setPatientIds(null);
        procedureService.update(procedureDto, procedure.getProcedureID());

        Procedure updatedProcedure = procedureRepository.findById(procedure.getProcedureID())
                .orElseThrow(() -> new AssertionError("Procedure not found after update."));

        assertEquals("Updated Name", updatedProcedure.getName(), "Procedure name should be updated.");
        assertTrue(updatedProcedure.getPatients().isEmpty(), "Procedure should have no associated patients after update.");
    }
}


