package cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter;


import cz.cvut.fit.tjv.otliaart.hostital.data.dto.PatientDto;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Patient;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Procedure;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.ProcedureRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class DtoToPatientConverter implements Function<PatientDto, Patient> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProcedureRepository procedureRepository;

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(PatientDto.class, Patient.class)
                .addMappings(m -> m.skip(Patient::setProcedures))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public Patient apply(PatientDto patientDto) {
        Patient patient = modelMapper.map(patientDto, Patient.class);
        return patient;
    }

    private Converter<PatientDto, Patient> toEntityConverter() {
        return context -> {
            PatientDto source = context.getSource();
            Patient destination = context.getDestination();
            mapProcedures(source, destination);
            return context.getDestination();
        };
    }

    private void mapProcedures(PatientDto source, Patient destination) {
        if (source.getProcedureIds() != null) {
            List<Procedure> procedures = source.getProcedureIds().stream()
                    .map(id -> procedureRepository.findById(id)
                            .orElseThrow(() -> new NoSuchElementException("Procedure with id " + id + " not found.")))
                    .collect(Collectors.toList());
            destination.setProcedures(procedures);
        }
    }

}

