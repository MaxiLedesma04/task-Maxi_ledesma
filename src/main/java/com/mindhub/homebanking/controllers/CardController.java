package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class CardController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardRepository cardRepository;


    @RequestMapping("/api/clients/current/cards")
    public List<CardDTO> getCards(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).getCards().stream().map(CardDTO::new).collect(toList());
    }

    @PostMapping("/api/clients/current/cards")
        public ResponseEntity<Object> createCc(@RequestParam CardColor color, @RequestParam CardType type, Authentication authentication){
            Client clientAuthent = clientRepository.findByEmail(authentication.getName());
            String cardholder = clientAuthent.getFirstName() + " " + clientAuthent.getLastName();
            List<Card> filteredCardsByType = clientAuthent.getCards().stream().filter(c -> c.getType() == type).collect(toList());
            List<Card> filteredCardsByColor = filteredCardsByType.stream().filter(c -> c.getColor() == color).collect(toList());

            if(color != null || type != null){
                if(filteredCardsByType.size() <= 2){
                    if(filteredCardsByColor.isEmpty()){
                        String cardNumber;
                        do{
                            cardNumber = (int)((Math.random() * (9999-1000)) + 1000)
                                    + "-" + (int)((Math.random() * (9999-1000)) + 1000)
                                    + "-" + (int)((Math.random() * (9999-1000)) + 1000)
                                    + "-" + (int)((Math.random() * (9999-1000)) + 1000);
                        } while (cardRepository.findByNumber(cardNumber) != null);
                        int cvv = (int)((Math.random() * (999 - 100)) + 100);

                        Card newCard = new Card(cardholder, type, color, cardNumber, cvv, LocalDate.now() ,LocalDate.now().plusYears(5));
                        clientAuthent.addCard(newCard);
                        clientRepository.save(clientAuthent);
                        cardRepository.save(newCard);
                    }else{
                        return new ResponseEntity<>("Para mono no podes crear" + color.toString().toLowerCase() + "tarjeta en" + type.toString().toLowerCase(), HttpStatus.FORBIDDEN);
                    }
                } else {
                    return new ResponseEntity<>("Epa queres tener mas de 3 tarjetas... nono no se puede", HttpStatus.FORBIDDEN);
                }
            }else {
                return new ResponseEntity<>("Completa todo porfis :)", HttpStatus.FORBIDDEN);
            }
        return new ResponseEntity<>("Esooo endeudese con gusto", HttpStatus.CREATED);
       }
}



