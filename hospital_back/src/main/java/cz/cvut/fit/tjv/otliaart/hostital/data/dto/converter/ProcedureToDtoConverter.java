package cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter;

import cz.cvut.fit.tjv.otliaart.hostital.data.dto.ProcedureDto;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Procedure;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProcedureToDtoConverter implements Function<Procedure, ProcedureDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProcedureDto apply(Procedure procedure) {
        return modelMapper.map(procedure, ProcedureDto.class);
    }
}
