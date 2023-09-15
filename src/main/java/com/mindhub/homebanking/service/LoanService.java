package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface LoanService {
    List<LoanDTO> findAll();

    Loan findById(Long id);

    List<Integer> getPyments(String name);

    void save(Loan loan);
}
