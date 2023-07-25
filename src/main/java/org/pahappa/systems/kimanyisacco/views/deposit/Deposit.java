package org.pahappa.systems.kimanyisacco.views.deposit;

import org.pahappa.Dao.SaccoDao;
import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.services.AccountService;
import org.pahappa.systems.kimanyisacco.services.AccountServiceImp;
import org.pahappa.systems.kimanyisacco.services.TransactionService;
import org.pahappa.systems.kimanyisacco.services.TransactionServiceImp;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
// import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Date;

@ManagedBean(name = "deposit")
@SessionScoped
public class Deposit {
    private double depositAmount;
    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    private AccountService accountService;
    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    private TransactionService transactionService;

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Deposit() {
        // Initialize the SaccoDao
        SaccoDao saccoDao = new SaccoDao();

        // Create the AccountService implementation with the SaccoDao instance
        accountService = new AccountServiceImp(saccoDao);

        // Create the TransactionService implementation with the SaccoDao instance
        transactionService = new TransactionServiceImp(saccoDao);
    }

    // Getter and Setter for depositAmount

    public void makeDeposit() {
        // Retrieve the logged-in member from the session
        Members loggedInMember = (Members) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("loggedInMember");

        // Retrieve the account associated with the member
        // Account account = member.getAccount();

        if(loggedInMember !=null){
            Account account = loggedInMember.getAccount();

            if(account !=null){
                transactionService.deposit(account, depositAmount, new Date().toString());
                depositAmount = 0.0;

                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Deposit Successful", null));
                
                String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(path + Hyperlinks.dashboard);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Account not found", null));
            }
        }else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        
    }

    // Other methods and logic as needed
}
