package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static com.mindhub.homebanking.models.AccountType.CHECKING;
import static com.mindhub.homebanking.models.AccountType.SAVINGS;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	 private PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*")
						.allowedOrigins("")
						.allowedMethods("")
						.allowedHeaders("")
						.allowedOriginPatterns("*");
			}
		};
	}
	@Bean
	public CommandLineRunner initData(ClientRepository repositoryClient, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {

		return (args) -> {
			LocalDate todayAcc = LocalDate.from(LocalDate.now().atStartOfDay());
			LocalDate tomorrowyAcc = LocalDate.from(LocalDate.now().plusDays(1));
			LocalDateTime today = LocalDate.now().atStartOfDay();
			LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
			LocalDateTime date = LocalDateTime.now();
			LocalDate thruDate = LocalDate.now();
			LocalDate fromDate = LocalDate.now().plusYears(5);



		Loan hipotecario = new Loan( "Hipotecario", 500000,
					new HashSet<>(Arrays.asList(12, 24, 36, 48, 60)),10.0);
			Loan personal = new Loan( "Personal", 100000,
					new HashSet<>(Arrays.asList(6, 12, 24)),15.0);
			Loan automotriz = new Loan( "Automotriz", 300000,
					new HashSet<>(Arrays.asList(6, 12, 24, 36)),15.5);
			loanRepository.save(hipotecario);
			loanRepository.save(personal);
			loanRepository.save(automotriz);

			Client admin = new Client("admin", "apellido", "admin@admin.com", passwordEncoder.encode("0012"));
			repositoryClient.save(admin);

			Client melba = new Client("Melba", "Morel","melbax@gmail.com", passwordEncoder.encode("Melba0501"));
			repositoryClient.save(melba);

			Accounts account1 =new Accounts("VIN001", todayAcc, 5000.0, true, CHECKING);
			Accounts account2 = new Accounts("VIN002", tomorrowyAcc, 7500.0, true, SAVINGS);
			melba.addAccount(account1);
			melba.addAccount(account2);
			accountRepository.save(account1);
			accountRepository.save(account2);


			Transaction transaction1 = new Transaction(5555, "venta", date, TransactionType.Credit,account1.getBalance(), true);
			Transaction transaction2 = new Transaction(-4442, "compra", date, TransactionType.Debit, account1.getBalance(), true);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);

			Card card1 = new Card("Melba", CardType.DEBIT, CardColor.GOLD, "2405 4457 45678 9875", 321, thruDate, fromDate, true);
			Card card2 = new Card("Melba", CardType.CREDIT, CardColor.TITANIUM, "2405 4835 4578 8897", 332, thruDate, fromDate, true);
			melba.addCard(card1);
			melba.addCard(card2);
			cardRepository.save(card1);
			cardRepository.save(card2);

			ClientLoan clientLoan1 = new ClientLoan(400000.0,
					60,48,true);
			ClientLoan clientLoan2 = new ClientLoan(50000.0,
					12, 6,true);
			hipotecario.addClientLoan(clientLoan1);
			personal.addClientLoan(clientLoan2);
			melba.addClientLoan(clientLoan1);
			melba.addClientLoan(clientLoan2);


			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);


			Client jorge = new Client("Jorge", "Gonzalez","jorgitox@gmail.com", passwordEncoder.encode("Jorjito10p"));
			repositoryClient.save(jorge);

			Transaction transaction2_1 = new Transaction(4561, "venta", tomorrow, TransactionType.Credit, account2.getBalance(), true);
			Transaction transaction2_2 = new Transaction(-1238, "compra", tomorrow, TransactionType.Debit, account2.getBalance(), true);

			account2.addTransaction(transaction2_1);
			account2.addTransaction(transaction2_2);
			transactionRepository.save(transaction2_1);
			transactionRepository.save(transaction2_2);




			Card card3 = new Card("Jorge", CardType.CREDIT, CardColor.SILVER, "2405 4653 5489 7896", 223, thruDate, fromDate, true);

			jorge.addCard(card3);
			cardRepository.save(card3);

//			ClientLoan clientLoan3 = new ClientLoan(100000.10,
//					24,24,true);
//
//			ClientLoan clientLoan4 = new ClientLoan(200000.0,
//					36,24,true);


		};
	}
}
