package org.pahappa.systems.kimanyisacco.services;

import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Members;

public interface AccountService {
    void createAccountForMember(Members member);
    Account getAccountByAccountId(int accountId);
    Members getMemberByEmail(String email);
    double getAccountBalance(int accountId);
}
