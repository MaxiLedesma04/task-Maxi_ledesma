package com.mindhub.homebanking.dtos;



public class LoanAplicationDTO {
    public Long id;

    public double amount;
    private Integer payments;

    public String number;
    public double installmentAmount;

    public LoanAplicationDTO() {

    }

    public LoanAplicationDTO(Long id, double amount, Integer payments, String number, double installmentAmount) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.number = number;
        this.installmentAmount = installmentAmount;

    }

    public double getInstallmentAmount() {
        return installmentAmount;
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getNumber() {
        return number;
    }
}
