package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Accounts;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDate dateTime;
    private double balance;
    private Set<TransactionDTO> transactions = new HashSet<>();
    private boolean active;
    public AccountDTO(){ }


    public AccountDTO(Accounts account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.dateTime = account.getDate();
        this.balance = account.getBalance();
        this.transactions = new HashSet<>();
        for (Transaction transaction : account.getTransactions()){
            this.transactions.add(new TransactionDTO(transaction));
        }
        this.active = account.active();
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
