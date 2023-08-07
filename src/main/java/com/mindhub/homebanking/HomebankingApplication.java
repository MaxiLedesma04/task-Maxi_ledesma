package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository repositoryClient, AccountRepository accountRepository) {

		return (args) -> {
			LocalDateTime today = LocalDate.now().atStartOfDay();
			LocalDateTime tomorrow = today.plusDays(1);

			Account account1 =new Account("VIN001", today, 5000.0);
			Account account2 = new Account("VIN002", tomorrow, 7500.0);
			Client melba = new Client("Melba", "Morel","melbax@gmail.com");
			melba.addAccount(account1);
			melba.addAccount(account2);

			repositoryClient.save(melba);
			accountRepository.save(account1);
			accountRepository.save(account2);

		};
	}
}
