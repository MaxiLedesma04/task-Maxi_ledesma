package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class LoansController {


    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/api/loans")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
    }

    @RequestMapping("/api/clients/current/loans")
    public ResponseEntity<Object> getLoans(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return ResponseEntity.ok(client.getClientLoans().stream().map(ClientLoanDTO::new).collect(toList()));
    }

    @PostMapping("/api/clients/current/loans")
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestParam String amount, @RequestParam String description, @RequestParam String originAccountNumber, @RequestParam String destinationAccountNumber){
        Client client = clientRepository.findByEmail(authentication.getName());
        Accounts accOrigin = accountRepository.findByNumber(originAccountNumber);
        Accounts destinationA = accountRepository.findByNumber(destinationAccountNumber);
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
        }
        Loan newLoan = new Loan();
        newLoan.setAmount(Double.parseDouble(amount));
        newLoan.setDescription(description);
        newLoan.setOriginAccount(accOrigin);
        newLoan.setDestinationAccount(destinationA);
        client.addLoan(newLoan);
        clientRepository.save(client);
        loanRepository.save(newLoan);

    }


}

