package org.pahappa.systems.kimanyisacco.views.withdraw;

import java.io.IOException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.pahappa.Dao.SaccoDao;
import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
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

    public Withdraw() {
        // Initialize the SaccoDao
        SaccoDao saccoDao = new SaccoDao();

        // Create the AccountService implementation with the SaccoDao instance
        accountService = new AccountServiceImp(saccoDao);

        // Create the TransactionService implementation with the SaccoDao instance
        transactionService = new TransactionServiceImp(saccoDao);
    }

    private void addFlashMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        facesContext.addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void makeWithdraw() {
        Members loggedInUser = (Members) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("loggedInUser");

        if (loggedInUser != null) {
            Account account = loggedInUser.getAccount();

            if (account != null) {
                transactionService.withdraw(account, withdrawAmount, new Date().toString());
                withdrawAmount = 0.0;

                addFlashMessage(FacesMessage.SEVERITY_INFO, "Success", "Withdraw made successfully");

                String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(path + Hyperlinks.dashboard);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Account not found", null));
            }
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getNumberOfWithdraws() {
        Members loggedInUser = (Members) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .get("loggedInUser");
        if (loggedInUser != null) {
            SaccoDao saccoDao = new SaccoDao();
            return saccoDao.getWithdrawCountForUser(loggedInUser);
        } else {
            return 0;
        }
    }
}
