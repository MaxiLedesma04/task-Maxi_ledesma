package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long Id;

    private double amount;
    private String description;
    private LocalDateTime localdate;

    private TransactionType type;
    private double balance;

    public TransactionDTO(){}

    public TransactionDTO(Transaction transaction) {
        this.Id = transaction.getId();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.localdate = transaction.getLocaldate();
        this.type = transaction.getType();
        this.balance = transaction.getBalance();
    }

    public double getBalance() {
        return balance;
    }

    public Long getId() {
        return Id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getLocaldate() {
        return localdate;
    }

    public TransactionType getType() {
        return type;
    }
}
