package cz.cvut.fit.tjv.otliaart.hostital.controller;

import cz.cvut.fit.tjv.otliaart.hostital.business.ProcedureService;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.PatientDto;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.ProcedureDto;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.DtoToProcedureConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.PatientToDtoConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.ProcedureToDtoConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Patient;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Procedure;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.ProcedureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/procedures")
public class ProcedureController extends AbstractCrudController<Procedure, ProcedureDto, ProcedureRepository> {

    private final ProcedureService procedureService;
    private final DtoToProcedureConverter dtoToProcedureConverter;
    private final PatientToDtoConverter patientToDtoConverter;

    public ProcedureController(ProcedureService procedureService,
                               ProcedureToDtoConverter procedureToDtoConverter,
                               DtoToProcedureConverter dtoToProcedureConverter, PatientToDtoConverter patientToDtoConverter) {
        super(procedureService, procedureToDtoConverter, dtoToProcedureConverter);
        this.procedureService = procedureService;
        this.dtoToProcedureConverter = dtoToProcedureConverter;
        this.patientToDtoConverter = patientToDtoConverter;
    }

    @PostMapping
    public ProcedureDto create(@RequestBody ProcedureDto dto) {
        Procedure entity = dtoToProcedureConverter.apply(dto);
        Procedure createdEntity = procedureService.create(entity);
        return toDtoConverter.apply(createdEntity);
    }

    @PatchMapping("/{id}")
    public void update(@RequestBody ProcedureDto dto, @PathVariable Long id) {
        try {
            service.update(toEntityConverter.apply(dto), id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/patients")
    public List<PatientDto> getPatientsByProcedureId(@PathVariable Long id) {
        List<Patient> patients = procedureService.getPatientsByProcedureId(id);
        return patients.stream().map(patientToDtoConverter).collect(Collectors.toList());
    }

    @GetMapping("/{id}/diagnoses")
    public List<String> getDiagnosesByProcedureId(@PathVariable Long id) {
        return procedureService.getDiagnosesByProcedureId(id);
    }

}
