package cz.cvut.fit.tjv.otliaart.hostital.controller;

import cz.cvut.fit.tjv.otliaart.hostital.business.CardService;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.CardDto;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.CardToDtoConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.DtoToCardConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Card;
import cz.cvut.fit.tjv.otliaart.hostital.data.repository.CardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cards")
public class CardController extends AbstractCrudController<Card, CardDto, CardRepository> {


    private final CardToDtoConverter cardToDtoConverter;


    private final DtoToCardConverter dtoToCardConverter;


    private final CardService cardService;

    public CardController(CardService cardService, CardToDtoConverter cardToDtoConverter, DtoToCardConverter dtoToCardConverter, CardToDtoConverter cardToDtoConverter1, DtoToCardConverter dtoToCardConverter1, CardService cardService1) {
        super(cardService, cardToDtoConverter, dtoToCardConverter);
        this.cardToDtoConverter = cardToDtoConverter1;
        this.dtoToCardConverter = dtoToCardConverter1;
        this.cardService = cardService1;
    }


    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody CardDto cardDto, @PathVariable Long id) {
        try {
            service.update(toEntityConverter.apply(cardDto), id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CardDto create(@RequestBody CardDto cardDto) {
        Card card = dtoToCardConverter.apply(cardDto);
        Card savedCard = cardService.create(card);
        return cardToDtoConverter.apply(savedCard);
    }
}
