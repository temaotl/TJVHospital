package org.example.hospital.business;

import org.example.hospital.data.dto.CardDto;
import org.example.hospital.data.dto.convertes.toDto.CardToDto;
import org.example.hospital.data.entity.Card;
import org.example.hospital.data.repository.CardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
public class CardService extends  AbstractCrudService<CardDto,Long, Card, CardRepository > {

    protected CardService(CardToDto toDtoConverter, CardRepository repository, ModelMapper modelMapper) {
        super(toDtoConverter, repository, modelMapper);
    }
}
