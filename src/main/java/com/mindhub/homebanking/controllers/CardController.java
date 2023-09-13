package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class CardController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private CardService cardService;


    @GetMapping("/api/clients/current/cards")
    public List<CardDTO> getCards(Authentication authentication){
        return clientService.findByEmail(authentication.getName()).getCards().stream().map(CardDTO::new).collect(toList());
    }

    @PostMapping("/api/clients/current/cards")
        public ResponseEntity<Object> createCc(@RequestParam CardColor color, @RequestParam CardType type, Authentication authentication){
            Client clientAuthent = clientService.findByEmail(authentication.getName());
            String cardholder = clientAuthent.getFirstName() + " " + clientAuthent.getLastName();
            List<Card> filteredCardsByType = clientAuthent.getCards().stream().filter(c -> c.getType() == type).collect(toList());
            List<Card> filteredCardsByColor = filteredCardsByType.stream().filter(c -> c.getColor() == color).collect(toList());

            if(color != null || type != null){
                if(filteredCardsByType.size() <= 2){
                    if(filteredCardsByColor.isEmpty()){
                        String cardNumber = CardUtils.getCardNumber();
                        int cvv = getCvv();

                        Card newCard = new Card(cardholder, type, color, cardNumber, cvv, LocalDate.now() ,LocalDate.now().plusYears(5), true);
                        clientAuthent.addCard(newCard);
                        clientService.save(clientAuthent);
                        cardService.save(newCard);
                    }else{
                        return new ResponseEntity<>("Es imposible crear una tarjerta de " + color.toString().toLowerCase() + " en " + type.toString().toLowerCase(), HttpStatus.FORBIDDEN);
                    }
                } else {
                    return new ResponseEntity<>("Lo sentimos no puede tener mas de 3 Tarjetas", HttpStatus.FORBIDDEN);
                }
            }else {
                return new ResponseEntity<>("Completa todo porfis :)", HttpStatus.FORBIDDEN);
            }
        return new ResponseEntity<>("Perfecto a obtenido una tarjeta nueva", HttpStatus.CREATED);
       }

    public static int getCvv() {
        int cvv = (int)((Math.random() * (999 - 100)) + 100);
        return cvv;
    }

    @PatchMapping("/api/clients/current/cards/deactivate")
    public ResponseEntity<Object> disableCard(@RequestParam long id, Authentication authentication){
        Card card = cardService.findById(id);
        Client client = clientService.findByEmail(authentication.getName());
        Boolean existCard = client.getCards().contains(card);
        if(card == null){
            return  new ResponseEntity<>("La tarjeta no existe", HttpStatus.FORBIDDEN);
        }
        if (!existCard){
            return  new ResponseEntity<>("Esta tarjeta no pertece a este cliente", HttpStatus.FORBIDDEN);
        }
        if(card.isActive() == false){
            return new ResponseEntity<>("Esta tarjeta ya fue eliminada", HttpStatus.FORBIDDEN);
        }
        card.setActive(false);
        cardService.save(card);
        return  new ResponseEntity<>("Se a eliminado la tarjeta con exito", HttpStatus.OK);
    }

}



