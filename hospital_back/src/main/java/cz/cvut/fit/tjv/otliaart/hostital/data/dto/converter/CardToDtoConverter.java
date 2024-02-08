package cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter;

import cz.cvut.fit.tjv.otliaart.hostital.data.dto.CardDto;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Card;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CardToDtoConverter implements Function<Card, CardDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CardDto apply(Card card) {
        return modelMapper.map(card, CardDto.class);
    }
}
