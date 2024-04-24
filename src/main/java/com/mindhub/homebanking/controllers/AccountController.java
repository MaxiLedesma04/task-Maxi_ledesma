package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Accounts;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private TransactionService transactionService;
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

    @GetMapping("/api/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.findAll();
    }

    @GetMapping("/api/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDTO::new).collect(toList());
    }

    @GetMapping("/api/clients/accounts/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable Long id, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        if (client.getAccounts() != null){
            Accounts account = client.getAccounts().stream().filter(account1 -> account1.getId() == id).findFirst().orElse(null);
            return new ResponseEntity<>(new AccountDTO(account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Account not found", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/api/clients/current/accounts")
    public ResponseEntity<Object> createAcc(@RequestParam String type ,Authentication authentication){
        if( !type.equals("CHECKING") && !type.equals("SAVINGS") ){
            return new ResponseEntity<>("Select the type", HttpStatus.FORBIDDEN);
        }
        AccountType accountType =  AccountType.valueOf(type);
        if(clientService.findByEmail(authentication.getName()).getAccounts().size() <= 2){
            String numberAccount = Rnumber();
            Accounts newAccount = new Accounts (numberAccount, LocalDate.now(), 0.0, true, accountType);
            clientService.findByEmail(authentication.getName()).addAccount(newAccount);
            accountService.save(newAccount);
        }else{
            return new ResponseEntity<>("la cantidad de cuentas excede las permitidas??", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PatchMapping("/api/clients/current/accounts/deactivate")
    public ResponseEntity<Object> elimaccount(@RequestParam String accNumber,
            Authentication authentication){
        Accounts account = accountService.findByNumber(accNumber);
        Client client = clientService.findByEmail(authentication.getName());
        Boolean existAccount = client.getAccounts().contains(account);
        Set<Transaction> transactionSet = account.getTransactions();
        Set<Accounts> accounts = client.getAccounts();
        if(account == null){
            return new ResponseEntity<>("La cuenta solicitada no existe", HttpStatus.FORBIDDEN);
        }
        if(!existAccount){
            return new ResponseEntity<>("La cuenta no pertenece al cliente", HttpStatus.FORBIDDEN);
        }

        if (account.active() == false){
            return new ResponseEntity<>("Esta tarjeta ya fue eliminada", HttpStatus.FORBIDDEN);
        }
        if(account.getBalance() > 0){
            return  new ResponseEntity<>("No puede eliminar una cuenta con balance positivo", HttpStatus.FORBIDDEN);
        }
        else {
            for (Transaction transaction : transactionSet) {
                transaction.setActive(false);
                transactionService.save(transaction);
            }
            account.setActive(false);
            accountService.save(account);
        }
        account.setActive(false);
        accountService.save(account);
        return new ResponseEntity<>("Se elimino la cuenta con exito", HttpStatus.OK);
    }

}
