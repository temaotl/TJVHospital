package org.example.hospital.config;

import org.example.hospital.data.dto.PatientDto;
import org.example.hospital.data.entity.Patient;
import org.example.hospital.data.entity.Procedure;
import org.example.hospital.data.repository.ProcedureRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    private final ProcedureRepository procedureRepository;

    public ModelMapperConfig(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Set<Procedure>, Set<Long>> procedureToProcedureIdConverter = new Converter<Set<Procedure>, Set<Long>>() {
            @Override
            public Set<Long> convert(MappingContext<Set<Procedure>, Set<Long>> context) {
                if (context.getSource() == null) {
                    return null;
                }
                return context.getSource().stream()
                        .map(Procedure::getProcedureID)
                        .collect(Collectors.toSet());
            }
        };

        Converter<Set<Long>, Set<Procedure>> procedureIdToProcedureConverter = new Converter<Set<Long>, Set<Procedure>>() {
            @Override
            public Set<Procedure> convert(MappingContext<Set<Long>, Set<Procedure>> context) {
               if(context.getSource() == null){
                   return  null;
               }
               Set<Procedure> procedureSet = new HashSet<>();
               for( Long i : context.getSource()  )
               {
                   procedureRepository.findById(i).ifPresent(procedureSet::add);
               }
               return  procedureSet;
            }
        };

        modelMapper.typeMap(Patient.class, PatientDto.class).addMappings(mapper ->
                mapper.using(procedureToProcedureIdConverter).map(Patient::getProcedures, PatientDto::setProcedureIds)

        );
       modelMapper.typeMap(PatientDto.class, Patient.class).addMappings(
               mapper -> mapper.using(procedureIdToProcedureConverter)
                       .map(PatientDto::getProcedureIds,Patient::setProcedures)
       );

        return modelMapper;
    }
}
