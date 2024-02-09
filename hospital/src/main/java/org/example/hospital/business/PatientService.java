package org.example.hospital.business;

import org.example.hospital.data.dto.PatientDto;
import org.example.hospital.data.dto.convertes.toDto.PatientToDto;
import org.example.hospital.data.entity.Card;
import org.example.hospital.data.entity.Patient;
import org.example.hospital.data.entity.Procedure;
import org.example.hospital.data.repository.CardRepository;
import org.example.hospital.data.repository.PatientRepository;
import org.example.hospital.data.repository.ProcedureRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientService extends AbstractCrudService<PatientDto,Long, Patient, PatientRepository> {

    private final ProcedureRepository procedureRepository;

    private final PatientRepository patientRepository;
    private final CardRepository cardRepository;

    protected PatientService(PatientToDto toDtoConverter, PatientRepository repository, ModelMapper modelMapper, ProcedureRepository procedureRepository, PatientRepository patientRepository,
                             CardRepository cardRepository) {
        super(toDtoConverter, repository, modelMapper);
        this.procedureRepository = procedureRepository;
        this.patientRepository = patientRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public Patient create(Patient entity) {

        Card patientCard = entity.getCard();
        if (patientCard != null && (patientCard.getCardID() == null || cardRepository.findById(patientCard.getCardID()).isEmpty())) {
            entity.setCard(cardRepository.save(patientCard));
            entity.getCard().setPatient(entity);
        }
        return super.create(entity);
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

