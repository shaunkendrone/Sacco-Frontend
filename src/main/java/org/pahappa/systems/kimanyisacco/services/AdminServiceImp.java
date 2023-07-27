package org.pahappa.systems.kimanyisacco.services;

import org.pahappa.Dao.SaccoDao;

public class AdminServiceImp implements AdminService {
    private SaccoDao saccoDao;

    public AdminServiceImp(SaccoDao saccoDao){
        this.saccoDao = saccoDao;
    }

    @Override
    public long getTotalTransactionCount() {
        return saccoDao.getTotalTransactionCount();
    }

    @Override
    public int getTotalMemberCount() {
        return saccoDao.getTotalMemberCount();
    }

    @Override
    public int getApprovedMemberCount() {
        return saccoDao.approvedMemberCount();
    }

    @Override
    public int getPendingMemberCount() {
        return saccoDao.pendingMemberCount();
    }
}
