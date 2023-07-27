package org.pahappa.systems.kimanyisacco.services;

public interface AdminService {
    long getTotalTransactionCount();
    int getTotalMemberCount();
    int getApprovedMemberCount();
    int getPendingMemberCount();
}
