package org.pahappa.systems.kimanyisacco.views.transaction;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.pahappa.Dao.SaccoDao;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.models.Transactions;
import org.pahappa.systems.kimanyisacco.services.AccountService;
import org.pahappa.systems.kimanyisacco.services.AccountServiceImp;
import org.pahappa.systems.kimanyisacco.services.TransactionService;
import org.pahappa.systems.kimanyisacco.services.TransactionServiceImp;

@ManagedBean(name = "myTransactions")
@SessionScoped // Use SessionScoped to retain data across multiple requests
public class MyTransactions {
    private AccountService accountService;
    private TransactionService transactionService;
    private List<Transactions> sortedTransactions;

    public MyTransactions() {
        // Initialize the SaccoDao
        SaccoDao saccoDao = new SaccoDao();

        // Create the AccountService implementation with the SaccoDao instance
        accountService = new AccountServiceImp(saccoDao);

        // Create the TransactionService implementation with the SaccoDao instance
        transactionService = new TransactionServiceImp(saccoDao);
    }

    public List<Transactions> getTransactions() {
        // Clear the sortedTransactions list before fetching new transactions
        sortedTransactions = null;

        // Get the currently logged in user
        Members loggedInUser = (Members) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .get("loggedInUser");

        if (loggedInUser != null) {
            // Get the account of the logged in user
            Account account = loggedInUser.getAccount();

            if (account != null) {
                // Get the transactions of the account
                List<Transactions> transactions = transactionService.getTransactionsForAccount(account);

                // Sort the transactions in descending order based on the transactionDate
                Collections.sort(transactions, new Comparator<Transactions>() {
                    @Override
                    public int compare(Transactions t1, Transactions t2) {
                        // Sort in descending order by comparing the transactionDate
                        return t2.getTransactionDate().compareTo(t1.getTransactionDate());
                    }
                });

                sortedTransactions = transactions;
            }
        }

        return sortedTransactions;
    }

    public double getAccountBalance() {
        // Get the currently logged in user
        Members loggedInUser = (Members) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("loggedInUser");

        if (loggedInUser != null) {
            // Get the account of the logged in user
            Account account = loggedInUser.getAccount();

            if (account != null) {
                // Get the account balance from the database using the AccountService
                return accountService.getAccountBalance(account.getAccountId());
            }
        }
        // Return 0 if the account or user is not found
        return 0.0;
    }

    public int getNumberOfTransactions(){
        Members loggedInUser = (Members) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .get("loggedInUser");
        if (loggedInUser != null) {
            SaccoDao saccoDao = new SaccoDao();
            return saccoDao.getDepositCountForUser(loggedInUser)+saccoDao.getWithdrawCountForUser(loggedInUser);
        } else {
            return 0;
        }
    }
}
