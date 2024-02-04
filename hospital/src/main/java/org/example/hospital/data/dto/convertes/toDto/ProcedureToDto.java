package org.example.hospital.data.dto.convertes.toDto;

import org.example.hospital.data.dto.ProcedureDto;
import org.example.hospital.data.entity.Procedure;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProcedureToDto implements Function<Procedure, ProcedureDto> {

    private  final ModelMapper modelMapper;

    public ProcedureToDto(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public ProcedureDto apply(Procedure procedure) {
        return modelMapper.map(procedure,ProcedureDto.class);
    }
}
