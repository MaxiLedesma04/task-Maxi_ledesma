package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class AccountsDTO {
    private long id;
    private String number;
    private LocalDateTime dateTime;
    private double balance;
    private Set<TransactionDTO> transactions = new HashSet<>();
    public AccountsDTO(){ }

    /*public AccountsDTO(long id, String number, LocalDateTime dateTime, double balance) {
        this.id = id;
        this.number = number;
        this.dateTime = dateTime;
        this.balance = balance;
    }*/

    public AccountsDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.dateTime = account.getDateTime();
        this.balance = account.getBalance();
        this.transactions = new HashSet<>();
        for (Transaction transaction : account.getTransactions()){
            this.transactions.add(new TransactionDTO(transaction));
        }
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
