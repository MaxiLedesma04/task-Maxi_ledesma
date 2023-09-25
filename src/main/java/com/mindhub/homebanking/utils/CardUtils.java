package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;

public final class CardUtils {
    @Autowired
    private CardService cardService;
    public static String getCardNumber() {

        String cardNumber;
        cardNumber = (int) ((Math.random() * (9999 - 1000)) + 1000)
                + "-" + (int) ((Math.random() * (9999 - 1000)) + 1000)
                + "-" + (int) ((Math.random() * (9999 - 1000)) + 1000)
                + "-" + (int) ((Math.random() * (9999 - 1000)) + 1000);
        return cardNumber;
    }

    public static int getCvv() {
        int cvv = (int)((Math.random() * (999 - 100)) + 100);
        return cvv;
    }

}
