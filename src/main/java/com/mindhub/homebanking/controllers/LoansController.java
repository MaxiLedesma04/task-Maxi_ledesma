package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanAplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class LoansController {


    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TransactionRepository transactionRepository;

    @RequestMapping("/api/loans")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
    }

    @Transactional
   @PostMapping("/api/loans")
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanAplicationDTO loanAplicationDTO){
        Client clientAuth = clientRepository.findByEmail(authentication.getName());
        Loan loan = loanRepository.findById(loanAplicationDTO.getId()).orElse(null);
        Accounts accountsAuth = accountRepository.findByNumber(loanAplicationDTO.getNumber());
        if (clientAuth == null){
            return ResponseEntity.badRequest().body("El cliente no existe");
        }
        if (loan == null){
            return new ResponseEntity<>("El préstamo no existe", HttpStatus.FORBIDDEN);
        }
        if (loanAplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("El monto solicitado excede el monto máximo del préstamo", HttpStatus.FORBIDDEN);
        }
        if (!loanAplicationDTO.getPayments().containsAll(loan.getPayments())){
            return new ResponseEntity<>("No hay esas cuotas proba con otra cosa o paga todo de una", HttpStatus.FORBIDDEN);
        }
        if(loanAplicationDTO.getNumber()==null){
            return  new ResponseEntity<>("no tenes habilitada esa cuenta fijate nomas que haces con tus cosas", HttpStatus.FORBIDDEN);

        }
       if (!clientAuth.getAccounts().contains(accountsAuth)) {
           return new ResponseEntity<>("La cuenta de destino no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
       }
       long amountLoan = ((loanAplicationDTO.getAmount()/100)*20) + loanAplicationDTO.getAmount();

       Loan newloan = new Loan(loanAplicationDTO.getId(), loanAplicationDTO.getNumber(), amountLoan, loanAplicationDTO.getPayments());

       Transaction transacCredit = new Transaction(loanAplicationDTO.getAmount(), loanAplicationDTO.getNumber(), LocalDateTime.now(), TransactionType.Credit);

       accountsAuth.addTransaction(transacCredit);
       transactionRepository.save(transacCredit);
       return null;

    }

}
