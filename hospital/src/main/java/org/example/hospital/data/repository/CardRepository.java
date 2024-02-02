package org.example.hospital.data.repository;

import org.example.hospital.data.entity.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card,Long> {
}
