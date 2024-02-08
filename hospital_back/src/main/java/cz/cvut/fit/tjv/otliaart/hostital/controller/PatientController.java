package cz.cvut.fit.tjv.otliaart.hostital.controller;

import cz.cvut.fit.tjv.otliaart.hostital.business.PatientService;
import cz.cvut.fit.tjv.otliaart.hostital.business.ProcedureService;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.PatientDto;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.DtoToCardConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.DtoToPatientConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.PatientToDtoConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Card;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Patient;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.NoSuchElementException;

@RestController
@RequestMapping("/patients")
public class PatientController extends AbstractCrudController<Patient, PatientDto, PatientRepository> {

    private final PatientService patientService;
    private final ProcedureService procedureService;
    private final DtoToPatientConverter dtoToPatientConverter;
    private final DtoToCardConverter dtoToCardConverter;

    @Autowired
    public PatientController(PatientService patientService,
                             PatientToDtoConverter patientToDtoConverter,
                             ProcedureService procedureService, DtoToPatientConverter dtoToPatientConverter,
                             DtoToCardConverter dtoToCardConverter) {
        super(patientService, patientToDtoConverter, dtoToPatientConverter);
        this.patientService = patientService;
        this.procedureService = procedureService;
        this.dtoToPatientConverter = dtoToPatientConverter;
        this.dtoToCardConverter = dtoToCardConverter;
    }

    @PostMapping
    public PatientDto create(@RequestBody PatientDto dto) {
        Patient entity = dtoToPatientConverter.apply(dto);

        if (dto.getCard() != null) {
            Card card = dtoToCardConverter.apply(dto.getCard());
            entity.setCard(card);
        }
        Patient createdEntity = patientService.create(entity);

        if(dto.getProcedureIds() != null && !dto.getProcedureIds().isEmpty()) {
            for(Long procedureId : dto.getProcedureIds()) {
                procedureService.addPatientToExistingProcedure(procedureId, createdEntity);
            }
        }
        return toDtoConverter.apply(createdEntity);
    }


    public void update(@RequestBody PatientDto dto, @PathVariable Long id) {
        try {
            service.update(dtoToPatientConverter.apply(dto), id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

