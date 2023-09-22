package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.TransactionAplicationDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Set;
@RestController
@RequestMapping("/api")
@CrossOrigin
public class Corsscontroller {

    @Autowired
    TransactionService transactionService;
    @Autowired
    CardService cardService;
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;

    @Transactional
    @PostMapping("/transaction/payment")
    public ResponseEntity<Object> createPayment(@RequestBody TransactionAplicationDTO transactionAplicationDTO){

        Card card = cardService.findByNumber(transactionAplicationDTO.getNumber());
        if (card == null){
            return new ResponseEntity<>("No es una tarjeta Valida", HttpStatus.FORBIDDEN);
        }
        if(!card.isActive()){
            return new ResponseEntity<>("La tarjeta no esta activa", HttpStatus.FORBIDDEN);
        }
        if(card.getFromDate().isBefore(ChronoLocalDate.from(LocalDateTime.now()))){
            return new ResponseEntity<>("Card is not active", HttpStatus.FORBIDDEN);
        }
        Client client = card.getClient();
        if(client == null){
            return  new ResponseEntity<>("Cliente no autorizado", HttpStatus.FORBIDDEN);
        }
        Set<Accounts> account = client.getAccounts();
        Accounts accountSelect = account.stream().filter(account1 -> account1.getBalance() >= transactionAplicationDTO.getAmount()).findFirst().orElse(null);
        Accounts accountPayment = new Accounts(new AccountDTO(accountSelect));
        if(accountSelect == null){
            return new ResponseEntity<>("No dispone de ese monto", HttpStatus.FORBIDDEN);
        }

        if(transactionAplicationDTO.getAmount() <= 0){
            return new ResponseEntity<>("Por favor ingrese un valor mayor a 0", HttpStatus.FORBIDDEN);
        }

        if(transactionAplicationDTO.getDescription().isBlank()){
            return new ResponseEntity<>("Ingrese una descripcion", HttpStatus.FORBIDDEN);
        }
        if(transactionAplicationDTO.getDescription().length() > 15){
            return new ResponseEntity<>("La descripcion no puede superar los 15 caracteres", HttpStatus.FORBIDDEN);

        }
        Transaction transaction = new Transaction(transactionAplicationDTO.getAmount(), transactionAplicationDTO.getDescription(), LocalDateTime.now(), TransactionType.Debit, accountSelect.getBalance(), true);
        accountPayment.addTransaction(transaction);
        accountPayment.setBalance(accountPayment.getBalance() - transactionAplicationDTO.getAmount());
        transactionService.save(transaction);
        accountService.save(accountPayment);
        return new ResponseEntity<>("Operacion realizada con exito", HttpStatus.CREATED);
    }
}
