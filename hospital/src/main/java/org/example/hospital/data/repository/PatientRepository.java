package org.example.hospital.data.repository;

import org.example.hospital.data.entity.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Long> {
}
