package cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter;

import cz.cvut.fit.tjv.otliaart.hostital.data.dto.PatientDto;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PatientToDtoConverter implements Function<Patient, PatientDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PatientDto apply(Patient patient) {
        return modelMapper.map(patient, PatientDto.class);
    }
}
