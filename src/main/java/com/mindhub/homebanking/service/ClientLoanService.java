package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.ClientLoan;

public interface ClientLoanService {
    void save(ClientLoan newClientLoan);

    ClientLoan findById(long loanId);

    ClientLoan findClientLoanById(long loanId);
}
