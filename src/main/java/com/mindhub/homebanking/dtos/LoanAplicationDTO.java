package com.mindhub.homebanking.dtos;

import javax.persistence.ElementCollection;
import java.util.Set;

public class LoanAplicationDTO {
    public Long id;

    public long amount;
    @ElementCollection
    private Set<Integer> payments;

    public String number;

    public LoanAplicationDTO() {

    }

    public LoanAplicationDTO(Long id, long amount, Set<Integer> payments, String number) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

    public String getNumber() {
        return number;
    }
}
