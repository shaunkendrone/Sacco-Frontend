package org.pahappa.systems.kimanyisacco.services;

import java.util.List;

import org.pahappa.Dao.SaccoDao;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.models.Transactions;

public class TransactionServiceImp implements TransactionService {
    private SaccoDao saccoDao;

    public TransactionServiceImp(SaccoDao saccoDao) {
        this.saccoDao = saccoDao;
    }

    @Override
    public void deposit(Account account, double amount, String transactionDate) {
        // Create a new transaction
        Transactions transaction = new Transactions();
        transaction.setTransactionType("Deposit");
        transaction.setTransactionAmount(amount);
        transaction.setTransactionDate(transactionDate);
        transaction.setAccount(account);

        // Update the account balance
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);

        // Save the transaction and update the account in the database
        saccoDao.saveTransaction(transaction);
        saccoDao.saveAccount(account);
    }

    @Override
    public void withdraw(Account account, double amount, String transactionDate) {
        // Create a new transaction
        Transactions transaction = new Transactions();
        transaction.setTransactionType("Withdrawal");
        transaction.setTransactionAmount(amount);
        transaction.setTransactionDate(transactionDate);
        transaction.setAccount(account);

        // Check if the account has sufficient balance for the withdrawal
        if (account.getBalance() >= amount) {
            // Update the account balance
            double newBalance = account.getBalance() - amount;
            account.setBalance(newBalance);

            // Save the transaction and update the account in the database
            saccoDao.saveTransaction(transaction);
            saccoDao.saveAccount(account);
        } else {
            // Insufficient balance, handle the situation as needed
            // For example, throw an exception or show an error message
        }
    }

    @Override
    public Transactions getTransactionById(int transactionId) {
        return saccoDao.getTransactionById(transactionId);
    }

    @Override
    public List<Transactions> getTransactionsForAccount(Account account) {
        return saccoDao.getTransactionsForAccount(account);
    }

     @Override
    public int getDepositCountForUser(Members member) {
        SaccoDao saccoDao = new SaccoDao();
        return saccoDao.getDepositCountForUser(member);
    }

    @Override
    public int getWithdrawCountForUser(Members member) {
        SaccoDao saccoDao = new SaccoDao();
        return saccoDao.getWithdrawCountForUser(member);
    }

    @Override
    public int getInternalTransferCountForUser(Members member) {
        SaccoDao saccoDao = new SaccoDao();
        return saccoDao.getInternalTransferCountForUser(member);
    }

    @Override
    public void internalTransfer(Account senderAccount, Account recipientAccount, double amount, String transactionDate){
        // Check if the sender's account has sufficient balance for the transfer
        if (senderAccount.getBalance() >= amount) {
            // Deduct the amount from the sender's account balance
            double newSenderBalance = senderAccount.getBalance() - amount;
            senderAccount.setBalance(newSenderBalance);

            // Add the amount to the recipient's account balance
            double newRecipientBalance = recipientAccount.getBalance() + amount;
            recipientAccount.setBalance(newRecipientBalance);

            // Create a new transaction for the sender
            Transactions senderTransaction = new Transactions();
            senderTransaction.setTransactionType("Internal Transfer");
            senderTransaction.setTransactionAmount(-amount);
            senderTransaction.setTransactionDate(transactionDate);
            senderTransaction.setAccount(senderAccount);

            // Create a new transaction for the recipient
            Transactions recipientTransaction = new Transactions();
            recipientTransaction.setTransactionType("Internal Transfer");
            recipientTransaction.setTransactionAmount(amount);
            recipientTransaction.setTransactionDate(transactionDate);
            recipientTransaction.setAccount(recipientAccount);

            // Save the transactions and update the accounts in the database
            saccoDao.saveTransaction(senderTransaction);
            saccoDao.saveTransaction(recipientTransaction);
            saccoDao.saveAccount(senderAccount);
            saccoDao.saveAccount(recipientAccount);
        } else {
            // Insufficient balance, handle the situation as needed
            // For example, throw an exception or show an error message
        }
    }
}


