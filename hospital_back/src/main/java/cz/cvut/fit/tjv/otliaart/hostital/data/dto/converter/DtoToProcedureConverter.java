package cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter;

import cz.cvut.fit.tjv.otliaart.hostital.data.dto.ProcedureDto;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Patient;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Procedure;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.PatientRepository;
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
public class DtoToProcedureConverter implements Function<ProcedureDto, Procedure> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PatientRepository patientRepository;

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(ProcedureDto.class, Procedure.class)
                .addMappings(m -> m.skip(Procedure::setPatients))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public Procedure apply(ProcedureDto procedureDto) {
        Procedure procedure = modelMapper.map(procedureDto, Procedure.class);
        return procedure;
    }

    private Converter<ProcedureDto, Procedure> toEntityConverter() {
        return context -> {
            ProcedureDto source = context.getSource();
            Procedure destination = context.getDestination();
            mapPatients(source, destination);
            return context.getDestination();
        };
    }

    private void mapPatients(ProcedureDto source, Procedure destination) {
        if (source.getPatientIDs() != null) {
            List<Patient> patients = source.getPatientIDs().stream()
                    .map(id -> patientRepository.findById(id)
                            .orElseThrow(() -> new NoSuchElementException("Patient with id " + id + " not found.")))
                    .collect(Collectors.toList());
            destination.setPatients(patients);
        }
    }
}
