package org.example.hospital.business;

import org.example.hospital.data.dto.PatientDto;
import org.example.hospital.data.dto.convertes.toDto.PatientToDto;
import org.example.hospital.data.entity.Patient;
import org.example.hospital.data.entity.Procedure;
import org.example.hospital.data.repository.PatientRepository;
import org.example.hospital.data.repository.ProcedureRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PatientService extends AbstractCrudService<PatientDto,Long, Patient, PatientRepository> {

    private final ProcedureRepository procedureRepository;

    private final ModelMapper modelMapper;
    private final PatientRepository patientRepository;

    protected PatientService(PatientToDto toDtoConverter, PatientRepository repository, ModelMapper modelMapper, ProcedureRepository procedureRepository, PatientRepository patientRepository) {
        super(toDtoConverter, repository, modelMapper);
        this.procedureRepository = procedureRepository;
        this.modelMapper = modelMapper;
        this.patientRepository = patientRepository;
    }

    @Override
    public void update(PatientDto dto, Long id) {
        Patient existedPatient = patientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Patient not found with ID: " + id));

        Set<Long> currentProcedureIds = existedPatient.getProcedures().stream()
                .map(Procedure::getProcedureID)
                .collect(Collectors.toSet());

        Set<Long> dtoProcedureIds = Optional.ofNullable(dto.getProcedureIds()).orElse(Collections.emptySet());

        Set<Long> procedureIdsToAdd = new HashSet<>(dtoProcedureIds);
        procedureIdsToAdd.removeAll(currentProcedureIds);

        Set<Long> procedureIdsToRemove = new HashSet<>(currentProcedureIds);
        procedureIdsToRemove.removeAll(dtoProcedureIds);

        procedureIdsToRemove.forEach(procedureId -> {
            Procedure procedure = procedureRepository.findById(procedureId)
                    .orElseThrow(() -> new NoSuchElementException("Procedure not found with ID: " + procedureId));
            existedPatient.getProcedures().remove(procedure);
            procedure.getPatients().remove(existedPatient);
        });

        procedureIdsToAdd.forEach(procedureId -> {
            Procedure procedure = procedureRepository.findById(procedureId)
                    .orElseThrow(() -> new NoSuchElementException("Procedure not found with ID: " + procedureId));
            existedPatient.getProcedures().add(procedure);
            procedure.getPatients().add(existedPatient);
        });
    }
}

