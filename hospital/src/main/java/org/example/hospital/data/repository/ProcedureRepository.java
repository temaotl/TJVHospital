package org.example.hospital.data.repository;

import org.example.hospital.data.entity.Procedure;
import org.springframework.data.repository.CrudRepository;

public interface ProcedureRepository extends CrudRepository<Procedure, Long> {
}
