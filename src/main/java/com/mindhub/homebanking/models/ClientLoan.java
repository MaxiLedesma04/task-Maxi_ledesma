package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private Double amount;

    private Integer payments;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "Loan")
    private Loan loan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
    private double installmentAmount;

    private boolean isActive;
    public ClientLoan() {
    }

    public ClientLoan(Double amount, Integer payment, double installmentAmount, boolean isActive) {
        this.amount = amount;
        this.payments = payment;
        this.installmentAmount = installmentAmount;
        this.isActive = isActive;
    }

    public double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public boolean isActive() {
        return isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public Loan getLoan() {
        return loan;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
    }

    public void setLoan(Loan loan) {
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
