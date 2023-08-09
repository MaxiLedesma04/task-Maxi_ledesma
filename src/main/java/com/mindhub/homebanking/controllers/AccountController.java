package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountsDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("api/accounts")
    public List<AccountsDTO> getAccounts(){
        return accountRepository.findAll().stream().map(AccountsDTO::new).collect(toList());
    }

    @RequestMapping("api/accounts/{id}")
    public AccountsDTO getAccount(@PathVariable Long id){
        return accountRepository.findById(id).map(AccountsDTO::new).orElse(null);
    }

}
