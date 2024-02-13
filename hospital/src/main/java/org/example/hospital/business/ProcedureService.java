package org.example.hospital.business;

import org.example.hospital.data.dto.ProcedureDto;
import org.example.hospital.data.entity.Patient;
import org.example.hospital.data.entity.Procedure;
import org.example.hospital.data.repository.PatientRepository;
import org.example.hospital.data.repository.ProcedureRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProcedureService extends AbstractCrudService<ProcedureDto,Long, Procedure, ProcedureRepository> {

    private final ProcedureRepository procedureRepository;

    private final PatientRepository patientRepository;

    protected ProcedureService(Function<Procedure, ProcedureDto> toDtoConverter, ProcedureRepository repository, Function<ProcedureDto, Procedure> toEntityConverter, ModelMapper modelMapper, ProcedureRepository procedureRepository, PatientRepository patientRepository) {
        super(toDtoConverter, repository, toEntityConverter, modelMapper);
        this.procedureRepository = procedureRepository;
        this.patientRepository = patientRepository;
    }


    @Override
    public ProcedureDto create(ProcedureDto dto) {
        Procedure procedure = toEntityConverter.apply(dto);
        procedure.getPatients().forEach(
                patient -> patient.getProcedures().add(procedure)
        );
        Procedure saved = procedureRepository.save(procedure);
        return toDtoConverter.apply(saved);
    }

    @Override
    @Transactional
    public void update(ProcedureDto dto, Long id) {
        Procedure existingProcedure = procedureRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Procedure not found with ID: " + id));

        Set<Long> currentPatientIds = existingProcedure.getPatients().stream()
                .map(Patient::getPatientID)
                .collect(Collectors.toSet());


        Set<Long> patientIdsToAdd = new HashSet<>(dto.getPatientIds() == null ? Collections.emptySet() :
                new HashSet<>(dto.getPatientIds()));
        patientIdsToAdd.removeAll(currentPatientIds);

        Set<Long> patientIdsToRemove = new HashSet<>(currentPatientIds);
        if (dto.getPatientIds() != null) {
            patientIdsToRemove.removeAll(dto.getPatientIds());
        }


        patientIdsToRemove.forEach(patientId -> {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new NoSuchElementException("Patient not found with ID: " + patientId));
            existingProcedure.getPatients().remove(patient);
            patient.getProcedures().remove(existingProcedure);
        });


        patientIdsToAdd.forEach(patientId -> {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new NoSuchElementException("Patient not found with ID: " + patientId));
            existingProcedure.getPatients().add(patient);
            patient.getProcedures().add(existingProcedure);
        });
        super.update(dto,id);
    }

}
