package org.example.hospital.data.dto.convertes.toEntity;

import org.example.hospital.data.dto.PatientDto;
import org.example.hospital.data.entity.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PatientDtoToEntity implements Function<PatientDto, Patient> {
    private final ModelMapper modelMapper;

    public PatientDtoToEntity(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Patient apply(PatientDto patientDto) {
        return modelMapper.map(patientDto, Patient.class);
    }
}
