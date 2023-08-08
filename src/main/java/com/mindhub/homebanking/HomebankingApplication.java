package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
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
	public CommandLineRunner initData(ClientRepository repositoryClient, AccountRepository accountRepository, TransactionRepository transactionRepository) {

		return (args) -> {
			LocalDateTime today = LocalDate.now().atStartOfDay();
			LocalDateTime tomorrow = today.plusDays(1);
			LocalDateTime date = LocalDateTime.now();


			Account account1 =new Account("VIN001", today, 5000.0);
			Account account2 = new Account("VIN002", tomorrow, 7500.0);
			Client melba = new Client("Melba", "Morel","melbax@gmail.com");
			melba.addAccount(account1);
			melba.addAccount(account2);

			Transaction transaction1 = new Transaction(5555, "venta", date, TransactionType.Credit);
			Transaction transaction2 = new Transaction(-4442, "compra", date, TransactionType.Debit);
			Transaction transaction2_1 = new Transaction(4561, "venta", date, TransactionType.Credit);
			Transaction transaction2_2 = new Transaction(-1238, "compra", date, TransactionType.Debit);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account2.addTransaction(transaction2_1);


			repositoryClient.save(melba);
			accountRepository.save(account1);
			accountRepository.save(account2);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction2_1);
			transactionRepository.save(transaction2_2);

		};
	}
}
