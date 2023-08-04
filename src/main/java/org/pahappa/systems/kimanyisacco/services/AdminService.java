package org.pahappa.systems.kimanyisacco.services;

public interface AdminService {
    long getTotalTransactionCount();
    long getTotalDepositCount();
    long getTotalWithdrawalCount();
    long getInternalTransferCount();
    int getTotalMemberCount();
    int getApprovedMemberCount();
    int getPendingMemberCount();
}
