package cz.cvut.fit.tjv.otliaart.hospital_client.ui;


import cz.cvut.fit.tjv.otliaart.hospital_client.data.CardClient;
import cz.cvut.fit.tjv.otliaart.hospital_client.model.CardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



//TODO make normal html

@Controller
@RequestMapping("/cards")
public class CardWebController {
    private final CardClient cardClient;

    @Autowired
    public CardWebController(CardClient cardClient) {
        this.cardClient = cardClient;
    }

    @GetMapping
    public String readAll(Model model) {
        model.addAttribute("cards", cardClient.readAll());
        return "cards";
    }

    //CREATE
    @GetMapping("/add")
    public String create(Model model) {
        model.addAttribute("card", new CardDto());
        return "cardAdd";
    }

    @PostMapping
    public String createSubmit(@ModelAttribute("card") CardDto cardDto, Model model) {
        model.addAttribute("card", cardClient.create(cardDto));
        return "redirect:/cards";
    }

    //EDIT
    @GetMapping("/edit/{id}")
    public String editCard(@PathVariable Long id, Model model) {
        model.addAttribute("cardDto", cardClient.readById(id));
        return "cardEdit";
    }

    @PatchMapping("/edit/{id}")
    public String editCardSubmit(@ModelAttribute("cardDto") CardDto cardDto, @PathVariable Long id) {
        cardClient.update(cardDto, id);
        return "redirect:/cards";
    }

    //DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        cardClient.delete(id);
        return "redirect:/cards";
    }
}


