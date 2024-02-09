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

    private final ModelMapper modelMapper;
    private final PatientRepository patientRepository;

    protected ProcedureService(Function<Procedure, ProcedureDto> toDtoConverter, ProcedureRepository repository, Function<ProcedureDto, Procedure> toEntityConverter, ModelMapper modelMapper, ProcedureRepository procedureRepository, ModelMapper modelMapper1, PatientRepository patientRepository) {
        super(toDtoConverter, repository, toEntityConverter, modelMapper);
        this.procedureRepository = procedureRepository;
        this.modelMapper = modelMapper1;
        this.patientRepository = patientRepository;
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


        modelMapper.map(dto, existingProcedure);


        procedureRepository.save(existingProcedure);
    }

}
