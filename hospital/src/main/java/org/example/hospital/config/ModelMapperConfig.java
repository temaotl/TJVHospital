package org.example.hospital.config;

import org.example.hospital.data.dto.PatientDto;
import org.example.hospital.data.entity.Patient;
import org.example.hospital.data.entity.Procedure;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Кастомный конвертер для преобразования Set<Procedure> в Set<Long>
        Converter<Set<Procedure>, Set<Long>> procedureToProcedureIdConverter = new Converter<Set<Procedure>, Set<Long>>() {
            @Override
            public Set<Long> convert(MappingContext<Set<Procedure>, Set<Long>> context) {
                // Проверяем, не null ли источник
                if (context.getSource() == null) {
                    return null;
                }
                return context.getSource().stream()
                        .map(Procedure::getProcedureID)
                        .collect(Collectors.toSet());
            }
        };

        // Добавляем кастомный конвертер в конфигурацию ModelMapper
        modelMapper.typeMap(Patient.class, PatientDto.class).addMappings(mapper ->
                mapper.using(procedureToProcedureIdConverter).map(Patient::getProcedures, PatientDto::setProcedureIds)
        );

        return modelMapper;
    }
}
