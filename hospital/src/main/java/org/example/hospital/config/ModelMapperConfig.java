package org.example.hospital.config;

import org.example.hospital.data.dto.PatientDto;
import org.example.hospital.data.dto.ProcedureDto;
import org.example.hospital.data.entity.Patient;
import org.example.hospital.data.entity.Procedure;
import org.example.hospital.data.repository.PatientRepository;
import org.example.hospital.data.repository.ProcedureRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    private final ProcedureRepository procedureRepository;
    private final PatientRepository patientRepository;


    public ModelMapperConfig(ProcedureRepository procedureRepository, PatientRepository patientRepository) {
        this.procedureRepository = procedureRepository;
        this.patientRepository = patientRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Set<Procedure>, Set<Long>> procedureToProcedureIdConverter = context -> {
            if (context.getSource() == null) {
                return null;
            }
            return context.getSource().stream()
                    .map(Procedure::getProcedureID)
                    .collect(Collectors.toSet());
        };


        Converter<Set<Long>, Set<Procedure>> procedureIdToProcedureConverter = context -> {
           if(context.getSource() == null){
               return  null;
           }
           Set<Procedure> procedureSet = new HashSet<>();
           for( Long i : context.getSource()  )
           {
               procedureRepository.findById(i).ifPresent(procedureSet::add);
           }
           return  procedureSet;
        };


        Converter <Set<Patient>, Set<Long>> patientToPatientIdConverter = context -> {
            if(context.getSource() == null){
                return null;
            }
            return  context.getSource().stream()
                    .map(Patient::getPatientID)
                    .collect(Collectors.toSet());
        };

        Converter < Set<Long>, Set<Patient>> patientIdToPatientConverter = context -> {
            if ( context.getSource() == null){
                return null;
            }
            Set<Patient> patientSet = new HashSet<>();
            for (Long i : context.getSource())
            {
                patientRepository.findById(i).ifPresent(patientSet::add);
            }
            return patientSet;
        };



        modelMapper.typeMap(Patient.class, PatientDto.class).addMappings(mapper ->
                mapper.using(procedureToProcedureIdConverter).map(Patient::getProcedures, PatientDto::setProcedureIds)

        );
       modelMapper.typeMap(PatientDto.class, Patient.class).addMappings(
               mapper -> mapper.using(procedureIdToProcedureConverter)
                       .map(PatientDto::getProcedureIds,Patient::setProcedures)
       );

       modelMapper.typeMap(Procedure.class, ProcedureDto.class).addMappings(
               mapper -> mapper.using(patientToPatientIdConverter).map(Procedure::getPatients,ProcedureDto::setPatientIds)
       );

       modelMapper.typeMap(ProcedureDto.class,Procedure.class).addMappings(
               mapper -> mapper.using(patientIdToPatientConverter)
                       .map(ProcedureDto::getPatientIds,Procedure::setPatients)
       );

       // skip null value for update
       modelMapper.getConfiguration()
               .setSkipNullEnabled(true);


        return modelMapper;
    }
}
