package cz.cvut.fit.tjv.otliaart.hostital.data.repository;

import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
}
