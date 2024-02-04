package org.example.hospital.data.dto.convertes.toEntity;

import org.example.hospital.data.dto.ProcedureDto;
import org.example.hospital.data.entity.Procedure;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProcedureDtoToEntity implements Function<ProcedureDto, Procedure> {

    private final ModelMapper modelMapper;

    public ProcedureDtoToEntity(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public Procedure apply(ProcedureDto procedureDto) {
        return modelMapper.map(procedureDto,Procedure.class);
    }
}
