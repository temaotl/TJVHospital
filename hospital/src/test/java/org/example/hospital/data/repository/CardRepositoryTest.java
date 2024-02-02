package org.example.hospital.data.repository;

import org.example.hospital.data.entity.Card;
import org.example.hospital.data.entity.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class CardRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CardRepository cardRepository;

    @Test
    public void whenCreateCard_thenFindById() {

        Patient patient = new Patient();
        entityManager.persist(patient);

        Card card = new Card(null, "Diagnosis Test", "Recommendations Test", patient);
        entityManager.persist(card);
        entityManager.flush();

        Card foundCard = cardRepository.findById(card.getCardID()).orElse(null);

        assertThat(foundCard).isNotNull();
        assertThat(foundCard.getDiagnosis()).isEqualTo(card.getDiagnosis());
    }

    @Test
    public void whenUpdateCard_thenFindUpdatedCard() {
        Patient patient = new Patient();
        entityManager.persist(patient);
        Card card = new Card(null, "Diagnosis Old", "Recommendations Old", patient);
        entityManager.persistAndFlush(card);

        card.setDiagnosis("Diagnosis Updated");
        cardRepository.save(card);

        Card updatedCard = cardRepository.findById(card.getCardID()).orElse(null);
        assertThat(updatedCard).isNotNull();
        assertThat(updatedCard.getDiagnosis()).isEqualTo("Diagnosis Updated");
    }

    @Test
    public void whenFindByIdForNonExistingCard_thenNotFound() {
        long nonExistingId = -1L;
        Card foundCard = cardRepository.findById(nonExistingId).orElse(null);

        assertThat(foundCard).isNull();
    }
}
