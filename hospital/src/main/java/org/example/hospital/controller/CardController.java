package org.example.hospital.controller;

import org.example.hospital.business.AbstractCrudService;
import org.example.hospital.data.dto.CardDto;
import org.example.hospital.data.dto.convertes.toDto.CardToDto;
import org.example.hospital.data.dto.convertes.toEntity.CardDtoToEntity;
import org.example.hospital.data.entity.Card;
import org.example.hospital.data.repository.CardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/cards")
public class CardController extends AbstractCrudController <Card, CardDto, CardRepository> {
    public CardController(AbstractCrudService<CardDto, Long, Card,
            CardRepository> service, CardToDto toDtoConverter, CardDtoToEntity toEntityConverter) {
        super(service, toDtoConverter, toEntityConverter);
    }


    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Card> update(@RequestBody CardDto dto, @PathVariable Long id) {
        try {
            service.update(dto, id);
            return ResponseEntity.ok().build();

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
