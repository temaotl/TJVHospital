package cz.cvut.fit.tjv.otliaart.hostital.repository;

import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Card;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Patient;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CardRepositoryTest {

    @Autowired
    private CardRepository cardRepository;

    @BeforeEach
    void clearDB() {
        cardRepository.deleteAll();
    }

    @Test
    void testSaveAndFindById() {
        Patient patient = new Patient();
        Card card = new Card(null, "Diagnosis1", "Recommendation1", patient);

        Card savedCard = cardRepository.save(card);


        assertThat(savedCard.getCardID()).isNotNull();


        Optional<Card> foundCard = cardRepository.findById(savedCard.getCardID());


        assertThat(foundCard).isPresent();
        assertThat(foundCard.get().getDiagnosis()).isEqualTo("Diagnosis1");
    }

    @Test
    void testDeleteCard() {
        Patient patient = new Patient();
        Card card = new Card(null, "Diagnosis2", "Recommendation2", patient);
        Card savedCard = cardRepository.save(card);


        cardRepository.deleteById(savedCard.getCardID());


        Optional<Card> foundCard = cardRepository.findById(savedCard.getCardID());
        assertThat(foundCard).isEmpty();
    }
}
