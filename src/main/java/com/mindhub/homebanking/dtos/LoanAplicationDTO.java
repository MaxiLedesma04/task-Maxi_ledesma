package com.mindhub.homebanking.dtos;

import javax.persistence.ElementCollection;
import java.util.Set;

public class LoanAplicationDTO {
    public Long id;

    public double amount;

    private Integer payments;

    public String number;


    public LoanAplicationDTO() {

    }

    public LoanAplicationDTO(Long id, double amount, Integer payments, String number) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.number = number;

    }


    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getNumber() {
        return number;
    }
}
