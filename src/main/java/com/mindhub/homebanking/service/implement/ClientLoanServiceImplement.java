package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.service.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImplement implements ClientLoanService {

   @Autowired
    ClientLoanRepository clientLoanRepository;
    @Override
    public void save(ClientLoan newClientLoan) {
        clientLoanRepository.save(newClientLoan);
    }

    @Override
    public ClientLoan findById(long loanId) {
        return clientLoanRepository.findById(loanId).orElse(null);
    }

    @Override
    public ClientLoan findClientLoanById(long loanId) {
        return clientLoanRepository.findClientLoanById(loanId);
    }
}
