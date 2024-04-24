package com.mindhub.homebanking.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.homebanking.dtos.AccountDTO;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private long id;
    private String number;
    private LocalDate dateTime;
    private double balance;
    private boolean active;

    private AccountType type;
    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();


    public Accounts(){}

    public Accounts(String number, LocalDate dateTime, double balance, boolean active, AccountType type) {
        this.number = number;
        this.dateTime = dateTime;
        this.balance = balance;
        this.active = active;
        this.type = type;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Accounts(AccountDTO accountSelect) {
    }

    public boolean active() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public LocalDate getDate() {
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


    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction tr){
        tr.setAccount(this);
        transactions.add(tr);
    }

}
