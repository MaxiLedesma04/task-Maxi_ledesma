package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long Id;

    private double amount;
    private String description;
    private LocalDateTime localdate;

    private TransactionType type;

    private double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Accounts account;

    public Transaction() { }

    public Transaction(double amount, String description, LocalDateTime localdate, TransactionType type, double balance) {
        this.amount = amount;
        this.description = description;
        this.localdate = localdate;
        this.type = type;
        this.balance = balance;
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
        return Id;
    }
}
