package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Accounts;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

import static com.mindhub.homebanking.models.AccountType.CHECKING;
import static java.util.stream.Collectors.toList;

@RestController
public class ClientController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;
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

    @PostMapping(path = "/api/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isBlank()) {
            return new ResponseEntity<>("No introdujo un nombre", HttpStatus.FORBIDDEN);
        }
        if(lastName.isBlank()){
            return new ResponseEntity<>("No introdujo un apellido", HttpStatus.FORBIDDEN);
        }
        if(email.isBlank()){
            return new ResponseEntity<>("No introdujo un email", HttpStatus.FORBIDDEN);
        }
        if(password.isBlank()){
            return new ResponseEntity<>("No introdujo una contraseña", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>("El email ya esta en uso", HttpStatus.FORBIDDEN);
        }
        Client newClient = new Client(firstName, lastName,email, passwordEncoder.encode(password));
        clientService.save(newClient);
        String number = Rnumber();
        Accounts newaccount = new Accounts(number, LocalDate.now(),0.0, true, CHECKING);
        newClient.addAccount(newaccount);
        accountService.save(newaccount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/api/clients")
    public List<ClientDTO> getClients(){
        return clientService.findAll();
    }

    @GetMapping("/api/clients/{id}")
    public ClientDTO getClient(@PathVariable long id){
        return clientService.findById(id);
    } //Servlets


    @GetMapping("/api/clients/current")
    public ClientDTO getClient(Authentication authentication) {
        return new ClientDTO(clientService.findByEmail(authentication.getName()));
    }

}
