package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Accounts;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Accounts findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public List<AccountDTO> findAll() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @Override
    public Accounts findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Accounts newAccount) {
        accountRepository.save(newAccount);

    }

    @Override
    public List<Accounts> getAllAccounts() {
        return accountRepository.findAll();
    }


}
