package cz.cvut.fit.tjv.otliaart.hostital.data.repository;

import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {
}
