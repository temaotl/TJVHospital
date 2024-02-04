package org.example.hospital.data.dto.convertes.toDto;

import org.example.hospital.data.dto.CardDto;
import org.example.hospital.data.entity.Card;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CardToDto implements Function<Card, CardDto> {

    private  final ModelMapper modelMapper;

    public CardToDto(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CardDto apply(Card card) {
        return modelMapper.map(card,CardDto.class);
    }
}
