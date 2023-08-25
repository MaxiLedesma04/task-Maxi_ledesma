package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Accounts;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountController {
    @Autowired
    private ClientRepository clientRepository;
    private String Rnumber(){
        String random;
        do{
            int number = (int)(Math.random()*001+999);
            random="VIN-" + number;
        }while (accountRepository.findByNumber(random) != null);
        return random;
    }
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/api/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @RequestMapping("/api/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @PostMapping("/api/clients/current/accounts")
    public ResponseEntity<Object> createAcc(Authentication authentication){
        if(clientRepository.findByEmail(authentication.getName()).getAccounts().size() <= 2){
            String numberAccount = Rnumber();
            Accounts newAccount = new Accounts (numberAccount, LocalDate.now(), 0.0);
            clientRepository.findByEmail(authentication.getName()).addAccount(newAccount);
            accountRepository.save(newAccount);
        }else{
            return new ResponseEntity<>("Cuantas cuentas queres tener??", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
