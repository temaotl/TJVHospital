package org.example.hospital.controller;

import org.example.hospital.business.AbstractCrudService;
import org.example.hospital.data.dto.ProcedureDto;
import org.example.hospital.data.dto.convertes.toDto.ProcedureToDto;
import org.example.hospital.data.dto.convertes.toEntity.ProcedureDtoToEntity;
import org.example.hospital.data.entity.Procedure;
import org.example.hospital.data.repository.ProcedureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/procedures")
public class ProcedureController extends AbstractCrudController<Procedure, ProcedureDto, ProcedureRepository>  {
    public ProcedureController(AbstractCrudService<ProcedureDto, Long, Procedure, ProcedureRepository> service, ProcedureToDto toDtoConverter, ProcedureDtoToEntity toEntityConverter) {
        super(service, toDtoConverter, toEntityConverter);
    }




    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@RequestBody ProcedureDto dto, @PathVariable Long id) {
        try {
            service.update(dto, id);
            return ResponseEntity.ok().build();

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
