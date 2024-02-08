package cz.cvut.fit.tjv.otliaart.hostital.business;

import cz.cvut.fit.tjv.otliaart.hostital.data.dto.CardDto;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.CardToDtoConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Card;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.CardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
public class CardService extends AbstractCrudService<CardDto, Long, Card, CardRepository> {
    public CardService(CardRepository repository, CardToDtoConverter cardToDtoConverter) {
        super(repository, cardToDtoConverter);
    }
    @Override
    @Transactional
    public void update(Card entity, Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Card existingCard = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        if (entity.getDiagnosis() != null) {
            existingCard.setDiagnosis(entity.getDiagnosis());
        }
        if (entity.getDoctorRecommendations() != null) {
            existingCard.setDoctorRecommendations(entity.getDoctorRecommendations());
        }
        repository.save(existingCard);
    }

}
