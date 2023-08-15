package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository repositoryClient, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository) {

		return (args) -> {
			LocalDateTime today = LocalDate.now().atStartOfDay();
			LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
			LocalDateTime date = LocalDateTime.now();


			Loan hipotecario = new Loan(null, "Hipotecario", 500000,
					new HashSet<>(Arrays.asList(12, 24, 36, 48, 60)));
			Loan personal = new Loan(null, "Personal", 100000,
					new HashSet<>(Arrays.asList(6, 12, 24)));
			Loan automotriz = new Loan(null, "Automotriz", 300000,
					new HashSet<>(Arrays.asList(6, 12, 24, 36)));
			loanRepository.save(hipotecario);
			loanRepository.save(personal);
			loanRepository.save(automotriz);



			Account account1 =new Account("VIN001", today, 5000.0);
			Account account2 = new Account("VIN002", tomorrow, 7500.0);
			Client melba = new Client("Melba", "Morel","melbax@gmail.com");
			Client jorge = new Client("Jorge", "Gonzalez","jorgitox@gmail.com");
			melba.addAccount(account1);
			melba.addAccount(account2);

			Transaction transaction1 = new Transaction(5555, "venta", date, TransactionType.Credit);
			Transaction transaction2 = new Transaction(-4442, "compra", date, TransactionType.Debit);
			Transaction transaction2_1 = new Transaction(4561, "venta", tomorrow, TransactionType.Credit);
			Transaction transaction2_2 = new Transaction(-1238, "compra", tomorrow, TransactionType.Debit);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account2.addTransaction(transaction2_1);
			account2.addTransaction(transaction2_2);


			repositoryClient.save(melba);
			accountRepository.save(account1);
			accountRepository.save(account2);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction2_1);
			transactionRepository.save(transaction2_2);


			ClientLoan clientLoan1 = new ClientLoan(400000L,
					60,
					hipotecario, melba);

			ClientLoan clientLoan2 = new ClientLoan(50000L,
					12,
					personal, melba);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);

			ClientLoan clientLoan3 = new ClientLoan(100000L,
					24,
					personal, jorge);
			ClientLoan clientLoan4 = new ClientLoan(200000L,
					36,
					automotriz, jorge);


		};
	}
}
