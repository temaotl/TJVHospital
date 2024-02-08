package cz.cvut.fit.tjv.otliaart.hostital.repository;

import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Procedure;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.ProcedureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProcedureRepositoryTest {

    @Autowired
    private ProcedureRepository procedureRepository;

    @BeforeEach
    void clearDB() {
        procedureRepository.deleteAll();
    }

    @Test
    void testSaveAndFindById() {

        Procedure procedure = new Procedure(null, "Sanacie", 60, null);


        Procedure savedProcedure = procedureRepository.save(procedure);


        assertThat(savedProcedure.getProcedureID()).isNotNull();


        Optional<Procedure> foundProcedure = procedureRepository.findById(savedProcedure.getProcedureID());


        assertThat(foundProcedure).isPresent();
        assertThat(foundProcedure.get().getName()).isEqualTo("Test Procedure");
    }

    @Test
    void testDeleteProcedure() {

        Procedure procedure = new Procedure(null, "Test Procedure for Delete", 120, null);
        Procedure savedProcedure = procedureRepository.save(procedure);


        procedureRepository.deleteById(savedProcedure.getProcedureID());


        Optional<Procedure> foundProcedure = procedureRepository.findById(savedProcedure.getProcedureID());
        assertThat(foundProcedure).isEmpty();
    }
}
