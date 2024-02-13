package org.example.hospital.data.dto.convertes.toEntity;

import org.example.hospital.data.dto.CardDto;
import org.example.hospital.data.entity.Card;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CardDtoToEntity implements Function<CardDto, Card> {

    private final ModelMapper modelMapper;

    public CardDtoToEntity(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public Card apply(CardDto cardDto) {
        return modelMapper.map(cardDto, Card.class);
    }
}
