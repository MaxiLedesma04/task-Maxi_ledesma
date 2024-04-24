package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Long id;
    private String name;
    private long maxAmount;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> payments= new HashSet<>();
    private Double interes;

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private  Set<ClientLoan> clientLoans = new HashSet<>();
    public Loan(){

    }

    public Loan( String name, long maxAmount, Set<Integer> payments, Double interes) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.interes = interes;
    }


    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

    public void setPayments(Set<Integer> payments) {
        this.payments = payments;
    }

    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }

    public Set<Client> getClients(){
        return clientLoans.stream().map(ClientLoan::getClient).collect(toSet());
    }
    @JsonIgnore
    public Set<ClientLoan> getClientLoansSet() {
        return clientLoans;
    }

    public void setClientLoansSet(Set<ClientLoan> clientLoansSet) {
        this.clientLoans = clientLoansSet;
    }


}
