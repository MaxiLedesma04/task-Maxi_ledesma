package com.mindhub.homebanking.dtos;

public class TransactionAplicationDTO {
    public long id;

    public String number;
    public Integer cvv;
    public String description;

    public Long amount;

    public TransactionAplicationDTO() {
    }

    public TransactionAplicationDTO(long id, String number, Integer cvv, String description, Long amount) {
        this.id = id;
        this.number = number;
        this.cvv = cvv;
        this.description = description;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public String getDescription() {
        return description;
    }

    public Long getAmount() {
        return amount;
    }
}
