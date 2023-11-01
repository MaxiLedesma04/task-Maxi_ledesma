//package com.mindhub.homebanking;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//
//import static org.hamcrest.Matchers.*;
//
//import com.mindhub.homebanking.models.*;
//import com.mindhub.homebanking.repositories.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//
//
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class RepositoriesTest {
//
//
//    @Autowired
//    private LoanRepository loanRepository;
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Autowired
//    CardRepository cardRepository;
//
//    @Autowired
//    ClientRepository clientRepository;
//
//    @Autowired
//    TransactionRepository transactionRepository;
//
//    @Test
//    public void existLoans(){
//
//        List<Loan> loans = loanRepository.findAll();
//
//        assertThat(loans,is(not(empty())));
//
//    }
//
//    @Test
//    public void existPersonalLoan(){
//
//        List<Loan> loans = loanRepository.findAll();
//
//        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
//
//    }
//
//    @Test
//    public void existsLoans() {
//        List<Loan> loans = loanRepository.findAll();
//        assertThat(loans, is(not(empty())));
//    }
//
//    @Test
//    public void existsPersonalLoan() {
//        List<Loan> loans = loanRepository.findAll();
//        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
//    }
//
//    @Test
//    public void existsAccount() {
//        List<Accounts> accounts = accountRepository.findAll();
//        assertThat(accounts,notNullValue());
//    }
//
//    @Test
//    public void positiveBalance(){
//        List<Accounts> accounts = accountRepository.findAll();
//        assertThat(accounts,hasItem(hasProperty("balance",greaterThanOrEqualTo(0.0))));
//    }
//
//    @Test
//    public void existsCard() {
//        List<Card> cards = cardRepository.findAll();
//        assertThat(cards, notNullValue());
//    }
//
//    @Test
//    public void threeDigitsCvvCard() {
//        List<Card> cards = cardRepository.findAll();
//        assertThat(cards, hasItem(hasProperty("cvv", isA(Integer.class))));
//    }
//
//    @Test
//    public void quantityClient() {
//        List<Client> clients = clientRepository.findAll();
//        assertThat(clients, hasSize(greaterThanOrEqualTo(3)));
//    }
//
//    @Test
//    public void correctEmail(){
//        List<Client> clients = clientRepository.findAll();
//        assertThat(clients, hasItem(hasProperty("email", stringContainsInOrder( "@",".com"))));
//    }
//
//    @Test
//    public void positiveAmount(){
//        List<Transaction> transactions = transactionRepository.findAll();
//        assertThat(transactions,hasItem(hasProperty("amount",greaterThanOrEqualTo(0.0))));
//    }
//
//    @Test
//    public void existsTransactions(){
//        List<Transaction> transactions = transactionRepository.findAll();
//        assertThat(transactions, notNullValue());
//    }
//
//}
