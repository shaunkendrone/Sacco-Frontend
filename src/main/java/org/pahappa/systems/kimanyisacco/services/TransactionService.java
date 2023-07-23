package org.pahappa.systems.kimanyisacco.services;

import java.util.List;

import org.pahappa.systems.kimanyisacco.models.Account;
// import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.models.Transactions;

public interface TransactionService {
    // Perform a deposit transaction
    void deposit(Account account, double amount, String transactionDate);

    // Perform a withdrawal transaction
    void withdraw(Account account, double amount, String transactionDate);

    // Get transaction by transaction ID
    Transactions getTransactionById(int transactionId);

    // Get all transactions for an account
    List<Transactions> getTransactionsForAccount(Account account);
}
