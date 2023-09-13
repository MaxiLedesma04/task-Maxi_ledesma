package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.service.CardService;

public final class CardUtils {
    public static String getCardNumber() {

        CardService cardService = null;

        String cardNumber;
        do{
            cardNumber = (int)((Math.random() * (9999-1000)) + 1000)
                    + "-" + (int)((Math.random() * (9999-1000)) + 1000)
                    + "-" + (int)((Math.random() * (9999-1000)) + 1000)
                    + "-" + (int)((Math.random() * (9999-1000)) + 1000);
        } while (cardService.findByNumber(cardNumber) != null);
        return cardNumber;
    }

    public static int getCvv() {
        int cvv = (int)((Math.random() * (999 - 100)) + 100);
        return cvv;
    }

}
