package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName;

    private String lastName;

    private String email;
    private Set<AccountsDTO> accounts = new HashSet<>();
    private Set<ClientLoanDTO> loans = new HashSet<>();

    public ClientDTO(){ }


    public ClientDTO(Client client) {

        this.id = client.getId();

        this.firstName = client.getFirstName();

        this.lastName = client.getLastName();

        this.email = client.getEmail();
        this.accounts = new HashSet<>();
        this.accounts = client.getAccounts().stream().map(account -> new AccountsDTO(account)).collect(Collectors.toSet());

        this.loans = client.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());

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

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public void setLoans(Set<ClientLoanDTO> loans) {
        this.loans = loans;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
