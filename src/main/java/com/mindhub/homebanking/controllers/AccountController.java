package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Accounts;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
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
    private ClientService clientService;
    private String Rnumber(){
        String random;
        do{
            int number = (int)(Math.random()*(001+999));
            random="VIN-" + number;
        }while (accountService.findByNumber(random) != null);
        return random;
    }
    @Autowired
    private AccountService accountService;

    @RequestMapping("/api/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.findAll();
    }

    @RequestMapping("/api/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDTO::new).collect(toList());
    }

    @RequestMapping("/api/clients/accounts/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable Long id, Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        Accounts acc = accountService.findById(id);
        if (client.getId() == acc.getClient().getId()) {
            return new ResponseEntity<>(new AccountDTO(acc), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No tienes permiso para ver esta cuenta", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/api/clients/current/accounts")
    public ResponseEntity<Object> createAcc(Authentication authentication){
        if(clientService.findByEmail(authentication.getName()).getAccounts().size() <= 2){
            String numberAccount = Rnumber();
            Accounts newAccount = new Accounts (numberAccount, LocalDate.now(), 0.0);
            clientService.findByEmail(authentication.getName()).addAccount(newAccount);
            accountService.save(newAccount);
        }else{
            return new ResponseEntity<>("la cantidad de cuentas excede las permitidas??", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
