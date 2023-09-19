package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Accounts;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    Accounts findByNumber(String number);

    List<AccountDTO> findAll();

    Accounts findById(Long id);

    void save(Accounts newAccount);

    List<Accounts> getAllAccounts();
}
