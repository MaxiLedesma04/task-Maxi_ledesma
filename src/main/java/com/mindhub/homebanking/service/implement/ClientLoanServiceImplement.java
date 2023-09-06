package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.service.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientLoanServiceImplement implements ClientLoanService {

   @Autowired
    ClientLoanRepository clientLoanRepository;
    @Override
    public void save(ClientLoan newClientLoan) {
        clientLoanRepository.save(newClientLoan);
    }
}
