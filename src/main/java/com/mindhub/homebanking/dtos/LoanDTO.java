package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import javax.persistence.ElementCollection;
import java.util.Set;

public class LoanDTO {
    private Long id;
    private String name;
    private long maxAmount;
    @ElementCollection
    private Set<Integer> payments;
    private Double interes;

    public LoanDTO() {

    }

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.interes = loan.getInteres();
    }

    public Double getInteres() {
        return interes;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getMaxAmount() {
        return maxAmount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

    public void setPayments(Set<Integer> payments) {
        this.payments = payments;
    }
}
