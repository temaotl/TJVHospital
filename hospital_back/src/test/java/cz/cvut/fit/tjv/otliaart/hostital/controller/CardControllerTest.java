package cz.cvut.fit.tjv.otliaart.hostital.controller;

import cz.cvut.fit.tjv.otliaart.hostital.business.CardService;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.CardToDtoConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.dto.converter.DtoToCardConverter;
import cz.cvut.fit.tjv.otliaart.hostital.data.entity.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(CardController.class)
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @MockBean
    private CardToDtoConverter cardToDtoConverter;

    @MockBean
    private DtoToCardConverter dtoToCardConverter;

    Card testCard = new Card(1L, "Aboba", "Aboba", null);

    @BeforeEach
    public void setUp() {
        when(cardService.create(testCard)).thenReturn(testCard);
    }

    @Test
    void deleteCardNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cards/2").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
