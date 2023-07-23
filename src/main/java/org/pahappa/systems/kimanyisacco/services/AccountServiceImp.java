package org.pahappa.systems.kimanyisacco.services;

import org.pahappa.Dao.SaccoDao;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Members;

public class AccountServiceImp implements AccountService {
    private SaccoDao saccoDao;

    public AccountServiceImp(SaccoDao saccoDao) {
        this.saccoDao = saccoDao;
    }

    @Override
    public void createAccountForMember(Members member) {
        Account account = new Account();
        // Set any initial values for the account, such as account number, balance, etc.
        account.setBalance(0.0);
        member.setAccount(account);
        saccoDao.saveAccount(account);
    }

    @Override
    public Account getAccountByAccountId(int accountId) {
        return saccoDao.getAccountByAccountId(accountId);
    }

    @Override
    public Members getMemberByEmail(String email) {
        return saccoDao.getMemberByEmail(email);
    }

    @Override
    public void updateAccountBalance(Account account, double newBalance) {
        if (account != null) {
            account.setBalance(newBalance);
            saccoDao.saveAccount(account);
        }
    }
}
