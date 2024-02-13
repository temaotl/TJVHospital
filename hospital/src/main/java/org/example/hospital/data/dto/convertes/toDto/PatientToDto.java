package org.example.hospital.data.dto.convertes.toDto;

import org.example.hospital.data.dto.PatientDto;
import org.example.hospital.data.entity.Patient;
import org.example.hospital.data.entity.Procedure;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class PatientToDto implements Function<Patient, PatientDto> {

    private final ModelMapper modelMapper;

    public PatientToDto(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }



    @Override
    public PatientDto apply(Patient patient) {
        return  modelMapper.map(patient, PatientDto.class);
    }
}
