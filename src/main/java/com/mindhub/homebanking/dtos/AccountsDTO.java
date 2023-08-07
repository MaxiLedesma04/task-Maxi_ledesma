package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDateTime;

public class AccountsDTO {
    private long id;
    private String number;
    private LocalDateTime dateTime;
    private double balance;
    public AccountsDTO(){ }

    public AccountsDTO(long id, String number, LocalDateTime dateTime, double balance) {
        this.id = id;
        this.number = number;
        this.dateTime = dateTime;
        this.balance = balance;
    }

    public AccountsDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.dateTime = account.getDateTime();
        this.balance = account.getBalance();
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
