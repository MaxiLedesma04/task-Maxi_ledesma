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
    private Long amount;

    private Integer payments;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "Loan")
    private Loan loan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public ClientLoan() {
    }

    public ClientLoan(Long amount, Integer payments, Loan loan, Client client) {
        this.amount = amount;
        this.payments = payments;
        this.loan = loan;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
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

}
