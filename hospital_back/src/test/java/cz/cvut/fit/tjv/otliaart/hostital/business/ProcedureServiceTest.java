package cz.cvut.fit.tjv.otliaart.hostital.business;

import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Procedure;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.ProcedureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class ProcedureServiceTest {
    @Autowired
    private ProcedureService procedureService;

    @MockBean
    private ProcedureRepository procedureRepository;

    Long testedId = 1L;
    Procedure procedure1, procedure2;

    @BeforeEach
    public void setUp() {
        procedure1 = new Procedure(null, "Sanacie", 60, null);
        procedure2 = new Procedure(null, "Logingoskopie", 60, null);

        Mockito.when(procedureRepository.findById(procedure1.getProcedureID())).thenReturn(Optional.of(procedure1));
        Mockito.when(procedureRepository.findById(procedure2.getProcedureID())).thenReturn(Optional.of(procedure2));
    }

    @Test
    void testCreateProcedure() {
        procedureService.create(procedure1);
        Mockito.verify(procedureRepository, Mockito.times(1)).save(procedure1);
    }

    @Test
    void testReadAllProcedures() {
        List<Procedure> actual = procedureService.readAll();
        assertThat(actual).hasSize(2).contains(procedure1, procedure2);
    }
}
