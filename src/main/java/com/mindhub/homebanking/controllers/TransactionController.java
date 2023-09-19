package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.LoanAplicationDTO;
import com.mindhub.homebanking.dtos.TransactionAplicationDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @Autowired
    private  CardService cardService;
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(@RequestParam String amount,
                                                    @RequestParam String description,
                                                    @RequestParam String originAccountNumber,
                                                    @RequestParam String destinationAccountNumber, Authentication authentication){
        Client currentC = clientService.findByEmail(authentication.getName());
        Accounts accOrigin = accountService.findByNumber(originAccountNumber);
        Accounts destinationA = accountService.findByNumber(destinationAccountNumber);
        if (destinationAccountNumber.isBlank()){
            return new ResponseEntity<>("Destination account number cannot be empty", HttpStatus.FORBIDDEN);
        }
        if (originAccountNumber.isBlank()){
            return new ResponseEntity<>("Origin account number cannot be empty", HttpStatus.FORBIDDEN);
        }
        if (accOrigin == null){
            return new ResponseEntity<>("Origin account not found", HttpStatus.FORBIDDEN);
        }
        if(destinationA == null){
            return new ResponseEntity<>("Destination account not found", HttpStatus.FORBIDDEN);
        }
        if(amount.isBlank() || Double.parseDouble(amount) <= 0){
            return new ResponseEntity<>("Please enter a valid amount", HttpStatus.FORBIDDEN);
        }
        if (description.isBlank()){
            return new ResponseEntity<>("Description cannot be empty", HttpStatus.FORBIDDEN);
        }
        if(accOrigin.getNumber() == destinationA.getNumber()){
            return new ResponseEntity<>("You cannot transfer to the same account", HttpStatus.FORBIDDEN);
        }
        if( accOrigin.getBalance() < Double.parseDouble(amount) ){
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }else{
            accOrigin.setBalance(accOrigin.getBalance() - Double.parseDouble(amount));
            destinationA.setBalance(destinationA.getBalance() + Double.parseDouble(amount));
            Transaction TransacDebit = new Transaction(Double.parseDouble(amount),description, LocalDateTime.now(), TransactionType.Debit, accOrigin.getBalance());
            Transaction TransacCredit = new Transaction(Double.parseDouble(amount),description, LocalDateTime.now(), TransactionType.Credit, destinationA.getBalance());
            accOrigin.addTransaction(TransacDebit);
            destinationA.addTransaction(TransacCredit);
            accountService.save(accOrigin);
            accountService.save(destinationA);
            transactionService.save(TransacDebit);
            transactionService.save(TransacCredit);
        }
        return new ResponseEntity<>("Transaction succesfully created", HttpStatus.CREATED);
    }



    }



