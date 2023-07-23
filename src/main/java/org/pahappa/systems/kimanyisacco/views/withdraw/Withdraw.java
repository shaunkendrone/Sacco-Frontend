package org.pahappa.systems.kimanyisacco.views.withdraw;

import java.io.IOException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.pahappa.Dao.SaccoDao;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.services.AccountService;
import org.pahappa.systems.kimanyisacco.services.AccountServiceImp;
import org.pahappa.systems.kimanyisacco.services.TransactionService;
import org.pahappa.systems.kimanyisacco.services.TransactionServiceImp;

@ManagedBean(name = "withdraw")
@SessionScoped
public class Withdraw {
    public double withdrawAmount;
    public double getWithdrawAmount() {
        return withdrawAmount;
    }
    public void setWithdrawAmount(double withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }
    public AccountService accountService;

    public AccountService getAccountService() {
        return accountService;
    }
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public TransactionService transactionService;
    public TransactionService getTransactionService() {
        return transactionService;
    }
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    public Withdraw(){
        // Initialize the SaccoDao
        SaccoDao saccoDao = new SaccoDao();

        // Create the AccountService implementation with the SaccoDao instance
        accountService = new AccountServiceImp(saccoDao);

        // Create the TransactionService implementation with the SaccoDao instance
        transactionService = new TransactionServiceImp(saccoDao);
    }

    public void makeWithdraw() {
        Members loggedInMember = (Members) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("loggedInMember");

        if(loggedInMember !=null){
            Account account = loggedInMember.getAccount();

            if(account !=null){
                transactionService.withdraw(account, withdrawAmount, new Date().toString());
                withdrawAmount = 0.0;
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
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
}
