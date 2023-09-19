package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
@Service
public class LoanServiceImplement implements LoanService {
    @Autowired
    LoanRepository loanRepository;
    @Override
    public List<LoanDTO> findAll() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
    }

    @Override
    public Loan findById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public List<Integer> getPyments(String name) {
        Set<Integer> paymentsSet = loanRepository.findByName(name).getPayments();
        List<Integer> paymentsList = new ArrayList<>(paymentsSet);
        return paymentsList;
    }

    @Override
    public void save(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public Loan findByName(String name) {
        return loanRepository.findByName(name);
    }


}
