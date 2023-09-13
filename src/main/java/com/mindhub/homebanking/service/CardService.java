package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Card;

public interface CardService {
    Card findByNumber(String cardNumber);

    void save(Card newCard);

    Card findById(long id);
}
