package cz.cvut.fit.tjv.otliaart.hostital.business;

import cz.cvut.fit.tjv.otliaart.hostital.data.dto.ProcedureDto;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.ProcedureToDtoConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Patient;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Procedure;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.PatientRepository;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.ProcedureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProcedureService extends AbstractCrudService<ProcedureDto, Long, Procedure, ProcedureRepository> {

    private final ProcedureRepository procedureRepository;
    private final PatientRepository patientRepository;

    public ProcedureService(ProcedureRepository repository, ProcedureToDtoConverter procedureToDtoConverter, PatientRepository patientRepository) {
        super(repository, procedureToDtoConverter);
        this.procedureRepository = repository;
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public void update(Procedure updatedProcedure, Long id) {
        Optional<Procedure> existingProcedureOptional = procedureRepository.findById(id);
        if (!existingProcedureOptional.isPresent()) {
            throw new NoSuchElementException("Procedure with id " + id + " not found.");
        }

        Procedure existingProcedure = existingProcedureOptional.get();

        if (updatedProcedure.getName() != null) {
            existingProcedure.setName(updatedProcedure.getName());
        }
        if (updatedProcedure.getDuration() != 0) {
            existingProcedure.setDuration(updatedProcedure.getDuration());
        }

        for (Patient patient : existingProcedure.getPatients()) {
            patient.getProcedures().remove(existingProcedure);
        }
        existingProcedure.getPatients().clear();

        if (updatedProcedure.getPatients() != null) {
            for (Patient patient : updatedProcedure.getPatients()) {
                if (patient.getPatientID() != null) {
                    Patient existingPatient = patientRepository.findById(patient.getPatientID())
                            .orElseThrow(() -> new NoSuchElementException("Patient with id " + patient.getPatientID() + " not found."));
                    existingProcedure.getPatients().add(existingPatient);
                    existingPatient.getProcedures().add(existingProcedure);  // Add procedure to the patient
                }
            }
        }

        procedureRepository.save(existingProcedure);
    }
    @Transactional
    public void addPatientToExistingProcedure(Long procedureID, Patient patient) {
        Optional<Procedure> procedureOptional = procedureRepository.findById(procedureID);
        if (procedureOptional.isPresent()) {
            Procedure procedure = procedureOptional.get();
            procedure.getPatients().add(patient);
            procedureRepository.save(procedure);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Procedure not found");
        }
    }


    public List<Patient> getPatientsByProcedureId(Long id) {
        Procedure procedure = readById(id)
                .orElseThrow(() -> new NoSuchElementException("Procedure with id " + id + " not found."));
        return new ArrayList<>(procedure.getPatients());
    }

    public List<String> getDiagnosesByProcedureId(Long id) {
        Optional<Procedure> procedureOptional = repository.findById(id);
        if (!procedureOptional.isPresent()) {
            throw new NoSuchElementException("Procedure with id " + id + " not found.");
        }
        return procedureOptional.get().getPatients().stream()
                .filter(patient -> patient.getCard() != null && patient.getCard().getDiagnosis() != null)
                .map(patient -> patient.getCard().getDiagnosis())
                .collect(Collectors.toList());
    }


}