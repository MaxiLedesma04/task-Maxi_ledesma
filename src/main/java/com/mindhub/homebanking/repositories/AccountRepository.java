package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Accounts;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Accounts, Long> {
  Accounts findByNumber(String number);

}
