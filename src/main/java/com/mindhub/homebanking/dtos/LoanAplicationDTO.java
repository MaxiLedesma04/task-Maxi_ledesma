package com.mindhub.homebanking.dtos;

public class LoanAplicationDTO {
    public Long id;
    public long amount;
    public Integer payments;
    public Long idClientLoan;

    public LoanAplicationDTO() {

    }

    public LoanAplicationDTO(Long id, long amount, Integer payments, Long idClientLoan) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.idClientLoan = idClientLoan;
    }

    public Long getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public Long getIdClientLoan() {
        return idClientLoan;
    }
}
