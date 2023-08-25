package com.mindhub.homebanking.controllers;




import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Accounts;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ClientController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientRepository clientRepository;
    private String Rnumber(){
        String random;
        do{
            int number = (int)(Math.random()*001+999);
            random="VIN-" + number;
        }while (accountRepository.findByNumber(random) == null);
        return random;
    }

    @PostMapping(path = "/api/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty()|| lastName.isEmpty()|| email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        Client newClient = new Client(firstName, lastName,email, passwordEncoder.encode(password));
        clientRepository.save(newClient);
        String number = Rnumber();
        Accounts newaccount = new Accounts(number, LocalDate.now(),0.0);
        newClient.addAccount(newaccount);
        accountRepository.save(newaccount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping("/api/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @RequestMapping("/api/clients/{id}")
    public ClientDTO getClient(@PathVariable long id){
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    } //Servlets


    @RequestMapping("/api/clients/current")
    public ClientDTO getClient(Authentication authentication) {

        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));

    }

}
