package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client findByEmail(String email);

    void save(Client newClient);

    List<ClientDTO> findAll();

    ClientDTO findById(long id);

    Client findByCardNumber(String number);
}
