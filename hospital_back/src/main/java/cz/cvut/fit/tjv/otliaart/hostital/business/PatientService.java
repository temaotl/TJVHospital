package cz.cvut.fit.tjv.otliaart.hostital.business;

import cz.cvut.fit.tjv.otliaart.hostital.data.dto.PatientDto;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.PatientToDtoConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Patient;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Procedure;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.PatientRepository;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.ProcedureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PatientService extends AbstractCrudService<PatientDto, Long, Patient, PatientRepository> {



    private final ProcedureRepository procedureRepository;
    public PatientService(PatientRepository repository,
                          PatientToDtoConverter patientToDtoConverter,
                          ProcedureRepository procedureRepository) {
        super(repository, patientToDtoConverter);
        this.procedureRepository = procedureRepository;
    }




    @Override
    @Transactional
    public void deleteById(Long id) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        for (Procedure procedure : patient.getProcedures()) {
            procedure.getPatients().remove(patient);
        }

        repository.deleteById(id);
    }
    @Override
    @Transactional
    public void update(Patient updatedPatient, Long id) {
        Optional<Patient> existingPatientOptional = repository.findById(id);
        if (!existingPatientOptional.isPresent()) {
            throw new NoSuchElementException("Patient with id " + id + " not found.");
        }

        Patient existingPatient = existingPatientOptional.get();

        if (updatedPatient.getFirstName() != null) {
            existingPatient.setFirstName(updatedPatient.getFirstName());
        }
        if (updatedPatient.getLastName() != null) {
            existingPatient.setLastName(updatedPatient.getLastName());
        }
        if (updatedPatient.getDocumentNumber() != null) {
            existingPatient.setDocumentNumber(updatedPatient.getDocumentNumber());
        }
        if (updatedPatient.getCard() != null && updatedPatient.getCard().getDiagnosis() != null) {
            existingPatient.getCard().setDiagnosis(updatedPatient.getCard().getDiagnosis());
        }
        if (updatedPatient.getCard() != null && updatedPatient.getCard().getDoctorRecommendations() != null) {
            existingPatient.getCard().setDoctorRecommendations(updatedPatient.getCard().getDoctorRecommendations());
        }

        for (Procedure procedure : existingPatient.getProcedures()) {
            procedure.getPatients().remove(existingPatient);
        }
        existingPatient.getProcedures().clear();

        for (Procedure procedure : updatedPatient.getProcedures()) {
            if (procedure.getProcedureID() != null) {
                Procedure existingProcedure = procedureRepository.findById(procedure.getProcedureID())
                        .orElseThrow(() -> new NoSuchElementException("Procedure with id " + procedure.getProcedureID() + " not found."));
                existingPatient.getProcedures().add(existingProcedure);
                existingProcedure.getPatients().add(existingPatient);
                procedureRepository.save(existingProcedure);
            }
        }
        repository.save(existingPatient);
    }


}
