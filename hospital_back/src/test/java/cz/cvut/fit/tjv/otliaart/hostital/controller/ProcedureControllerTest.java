package cz.cvut.fit.tjv.otliaart.hostital.controller;

import cz.cvut.fit.tjv.otliaart.hostital.business.PatientService;
import cz.cvut.fit.tjv.otliaart.hostital.business.ProcedureService;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.DtoToProcedureConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Procedure;
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

@WebMvcTest(ProcedureController.class)
class ProcedureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcedureService procedureService;

    @MockBean
    private PatientService patientService;

    @MockBean
    private DtoToProcedureConverter dtoToProcedureConverter;

    Procedure testProcedure = new Procedure(1L, "Loringoskopia", 60, new ArrayList<>());

    @BeforeEach
    public void setUp() {
        when(procedureService.create(testProcedure)).thenReturn(testProcedure);
    }

    @Test
    void deleteProcedureNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/procedures/2").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
