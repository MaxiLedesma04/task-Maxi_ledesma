package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.LoanAplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.mindhub.homebanking.utils.LoanUtils.calcularIntereses;


@RestController
public class LoansController {

    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TransactionService transactionService;


    @GetMapping("/api/loans")
    public List<LoanDTO> getLoans(){
        return loanService.findAll();
    }

    @Transactional
   @PostMapping("/api/loans")
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanAplicationDTO loanAplicationDTO){
        String username = authentication.getName();
        Client clientAuth = clientService.findByEmail(username);
        Loan loan = loanService.findById(loanAplicationDTO.getId());
        Accounts accountsAuth = accountService.findByNumber(loanAplicationDTO.getNumber());
        if (clientAuth == null){
            return ResponseEntity.badRequest().body("El cliente no existe");
        }
        if (loan == null){
            return new ResponseEntity<>("El préstamo no existe", HttpStatus.FORBIDDEN);
        }
        if (loanAplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("El monto solicitado excede el monto máximo del préstamo", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanAplicationDTO.getPayments())){
            return new ResponseEntity<>("No hay esas cuotas proba con otra cosa o paga todo de una", HttpStatus.FORBIDDEN);
        }
        if(loanAplicationDTO.getNumber()==null){
            return  new ResponseEntity<>("no tenes habilitada esa cuenta", HttpStatus.FORBIDDEN);

        }
       if (!clientAuth.getAccounts().contains(accountsAuth)) {
           return new ResponseEntity<>("La cuenta de destino no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
       }

        Double interes = calcularIntereses(loan, loanAplicationDTO);

       double amountLoan = ((interes/100) * loanAplicationDTO.getAmount()) + loanAplicationDTO.getAmount();

       ClientLoan newClientLoan = new ClientLoan(amountLoan, loanAplicationDTO.getPayments());

        newClientLoan.setClient(clientAuth);
        newClientLoan.setLoan(loan);
        clientLoanService.save(newClientLoan);
        Transaction transacCredit = new Transaction(loanAplicationDTO.getAmount(), loanAplicationDTO.getNumber(), LocalDateTime.now(), TransactionType.Credit, accountsAuth.getBalance());
        transactionService.save(transacCredit);
        accountService.save(accountsAuth);
        accountsAuth.addTransaction(transacCredit);

       return new ResponseEntity<>("creado con exito",HttpStatus.CREATED);
    }

    @PostMapping("/create/loans")
    public ResponseEntity<Object> createLoan(@RequestBody LoanDTO loanDTO){
        Loan loan = loanService.findByName(loanDTO.getName());
        long amount = loanDTO.getMaxAmount();
        Set<Integer> payments = loanDTO.getPayments();
        if (loan != null){
            return new ResponseEntity<>("the loan already exists",HttpStatus.FORBIDDEN);
        }
        if (amount == 0){
            return new ResponseEntity<>("Missing data",HttpStatus.FORBIDDEN);
        }
        if (payments == null){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (amount < 0) {
            return new ResponseEntity<>("The amount cannot be negative",HttpStatus.FORBIDDEN);
        }
        Loan newLoan = new Loan(loanDTO.getName(),amount,payments, loanDTO.getInteres());
        loanService.save(newLoan);
        return new ResponseEntity<>("Loan create",HttpStatus.OK);
    }
}
