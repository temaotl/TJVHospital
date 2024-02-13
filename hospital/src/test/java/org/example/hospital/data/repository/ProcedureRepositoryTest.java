package org.example.hospital.data.repository;

import org.example.hospital.data.entity.Procedure;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import java.util.HashSet;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProcedureRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProcedureRepository procedureRepository;

    @Test
    public void whenSaveProcedure_thenFindById() {
        Procedure procedure = new Procedure(null, "Procedure Name", 60, new HashSet<>());
        procedure = entityManager.persistFlushFind(procedure);
        Optional<Procedure> foundProcedure = procedureRepository.findById(procedure.getProcedureID());
        assertThat(foundProcedure).isPresent();
        assertThat(foundProcedure.get().getName()).isEqualTo(procedure.getName());
    }

    @Test
    public void whenUpdateProcedure_thenFindUpdatedProcedure() {
        Procedure procedure = new Procedure(null, "Procedure Name", 45, new HashSet<>());
        procedure = entityManager.persistFlushFind(procedure);
        procedure.setName("Updated Procedure Name");
        procedure.setDuration(30);
        procedureRepository.save(procedure);
        Procedure updatedProcedure = entityManager.find(Procedure.class, procedure.getProcedureID());
        assertThat(updatedProcedure.getName()).isEqualTo("Updated Procedure Name");
        assertThat(updatedProcedure.getDuration()).isEqualTo(30);
    }

    @Test
    public void whenDeleteProcedure_thenNotFound() {
        Procedure procedure = new Procedure(null, "Procedure to Delete", 15, new HashSet<>());
        procedure = entityManager.persistFlushFind(procedure);
        procedureRepository.delete(procedure);
        Procedure deletedProcedure = entityManager.find(Procedure.class, procedure.getProcedureID());
        assertThat(deletedProcedure).isNull();
    }
}


