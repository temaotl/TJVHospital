package org.example.hospital.controller;

import org.example.hospital.business.AbstractCrudService;
import org.example.hospital.data.dto.PatientDto;
import org.example.hospital.data.dto.convertes.toDto.PatientToDto;
import org.example.hospital.data.dto.convertes.toEntity.PatientDtoToEntity;
import org.example.hospital.data.entity.Patient;
import org.example.hospital.data.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/patients")
public class PatientController extends AbstractCrudController<Patient, PatientDto, PatientRepository> {
    public PatientController(AbstractCrudService<PatientDto, Long, Patient, PatientRepository> service, PatientToDto toDtoConverter, PatientDtoToEntity toEntityConverter) {
        super(service, toDtoConverter, toEntityConverter);
    }

    @Override
    @PostMapping
    public ResponseEntity<PatientDto> create(@RequestBody PatientDto dto) {
        Patient patient = toEntityConverter.apply(dto);
        Patient savedPatient = service.create(patient);
        PatientDto savedDto = toDtoConverter.apply(savedPatient);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@RequestBody PatientDto dto, @PathVariable Long id) {
        try {
            service.update(dto, id);
            return ResponseEntity.ok().build();

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
