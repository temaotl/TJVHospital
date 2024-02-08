package cz.cvut.fit.tjv.otliaart.hostital.controller;

import cz.cvut.fit.tjv.otliaart.hostital.business.PatientService;
import cz.cvut.fit.tjv.otliaart.hostital.business.ProcedureService;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.DtoToCardConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.DtoToPatientConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private ProcedureService procedureService;

    @MockBean
    private DtoToPatientConverter dtoToPatientConverter;

    @MockBean
    private DtoToCardConverter dtoToCardConverter;

    Patient testPatient = new Patient(1L, "Grisha", "Petrovic", "228", null, new ArrayList<>());

    @BeforeEach
    public void setUp() {
        when(patientService.create(testPatient)).thenReturn(testPatient);
    }

    @Test
    void deletePatientNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/2").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
