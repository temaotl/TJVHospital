package org.example.hospital.business;

import org.example.hospital.data.dto.ProcedureDto;
import org.example.hospital.data.dto.convertes.toDto.ProcedureToDto;
import org.example.hospital.data.entity.Patient;
import org.example.hospital.data.entity.Procedure;
import org.example.hospital.data.repository.PatientRepository;
import org.example.hospital.data.repository.ProcedureRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProcedureService extends AbstractCrudService<ProcedureDto,Long, Procedure, ProcedureRepository> {

    private final ProcedureRepository procedureRepository;

    private final ModelMapper modelMapper;
    private final PatientRepository patientRepository;

    protected ProcedureService(ProcedureToDto toDtoConverter, ProcedureRepository repository, ModelMapper modelMapper, ProcedureRepository procedureRepository, PatientRepository patientRepository) {
        super(toDtoConverter, repository, modelMapper);
        this.procedureRepository = procedureRepository;
        this.modelMapper = modelMapper;
        this.patientRepository = patientRepository;
    }


    private Set<Patient> updatePatientsAssociation(Procedure procedure, Set<Long> patientIds) {
        Set<Patient> tmpSetOfNewPatient = new HashSet<>();
        if (patientIds == null) {
            for (Patient patient : procedure.getPatients()) {
                patient.getProcedures().remove(procedure);
            }
        } else {

            for (Patient patient : procedure.getPatients()) {
                if (!patientIds.contains(patient.getPatientID())) {
                    patient.getProcedures().remove(procedure);
                } else {
                    tmpSetOfNewPatient.add(patient);
                }
            }

        }
        return tmpSetOfNewPatient;
    }

    @Override
    @Transactional
    public void update(ProcedureDto dto, Long id) {
        Procedure existingProcedure = procedureRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Procedure not found with ID: " + id));

        Set<Patient> updatedPatients = updatePatientsAssociation(existingProcedure, dto.getPatientIds());
        modelMapper.map(dto, existingProcedure);

        if (dto.getPatientIds() != null) {
            Set<Long> existingPatientIds = existingProcedure.getPatients().stream()
                    .map(Patient::getPatientID)
                    .collect(Collectors.toSet());

            dto.getPatientIds().stream()
                    .filter(patientId -> !existingPatientIds.contains(patientId))
                    .forEach(patientId -> patientRepository.findById(patientId).ifPresent(updatedPatients::add));
        }

        existingProcedure.setPatients(updatedPatients);
        procedureRepository.save(existingProcedure);
    }
}
