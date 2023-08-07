package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.HashSet;
import java.util.Set;

public class ClientDTO {
    private Long id;
    private String firstName;

    private String lastName;

    private String email;
    private Set<AccountsDTO> accounts = new HashSet<>();

    public ClientDTO(){ }


    public ClientDTO(Client client) {

        this.id = client.getId();

        this.firstName = client.getFirstName();

        this.lastName = client.getLastName();

        this.email = client.getEmail();
        this.accounts = new HashSet<>();
        for (Account account: client.getAccounts()
             ) {
            this.accounts.add(new AccountsDTO(account));
        }

    }

    public Long getId() {
        return id;
    }

    public Set<AccountsDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountsDTO> accounts) {
        this.accounts = accounts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
