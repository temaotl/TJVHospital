package org.example.hospital.data.dto;



import org.example.hospital.data.dto.convertes.toDto.ProcedureToDto;
import org.example.hospital.data.dto.convertes.toEntity.ProcedureDtoToEntity;
import org.example.hospital.data.entity.Patient;

import org.example.hospital.data.entity.Procedure;
import org.example.hospital.data.repository.PatientRepository;
import org.example.hospital.data.repository.ProcedureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class ProcedureConvertersTest {

    @Autowired
    private ProcedureToDto procedureToDto;

    @Autowired
    private ProcedureDtoToEntity procedureDtoToEntity;

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void mustConvertFromEntityToDTO() {

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


        assertThat(procedureDto.getProcedureID()).isEqualTo(procedure.getProcedureID());
        assertThat(procedureDto.getName()).isEqualTo(procedure.getName());
        assertThat(procedureDto.getDuration()).isEqualTo(procedure.getDuration());
        assertThat(procedureDto.getPatientIds()).contains(patient.getPatientID());
    }

    @Test
    public void mustConvertFromDtoToEntity() {

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
        Procedure copyProcedure  = procedureDtoToEntity.apply(procedureDto);


        assertThat(copyProcedure).isEqualTo(procedure);

    }
}
