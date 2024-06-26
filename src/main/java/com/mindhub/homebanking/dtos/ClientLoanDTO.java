package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private long loanId;
    private long idClientLoan;
    private String name;
    private double amount;
    private Integer payments;
    public ClientLoanDTO(){

    }

    public ClientLoanDTO(ClientLoan clientLoan){
        this.loanId= clientLoan.getLoan().getId();
        this.idClientLoan = clientLoan.getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public long getLoanId() {
        return loanId;
    }

    public long getIdClientLoan() {
        return idClientLoan;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }
}
