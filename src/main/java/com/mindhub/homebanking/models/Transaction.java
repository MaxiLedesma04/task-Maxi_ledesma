package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Long id;

    private double amount;
    private String description;
    private LocalDateTime localdate;

    private TransactionType type;

    private double balance;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Accounts account;

    public Transaction() { }

    public Transaction(double amount, String description, LocalDateTime localdate, TransactionType type, double balance, boolean isActive) {
        this.amount = amount;
        this.description = description;
        this.localdate = localdate;
        this.type = type;
        this.balance = balance;
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLocaldate() {
        return localdate;
    }

    public void setLocaldate(LocalDateTime localdate) {
        this.localdate = localdate;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public Long getId() {
        return id;
    }
}
