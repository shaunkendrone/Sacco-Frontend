package org.pahappa.Dao;

import java.util.List;
import javax.persistence.NoResultException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.models.Transactions;


public class SaccoDao {

    public void saveMember(Members member) {
        Session session = SessionConfiguration.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(member);
        session.getTransaction().commit();
        session.close();
    }

    public Members getMemberByEmail(String email) {
        Session session = null;
        Members member = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Members.class);
            criteria.add(Restrictions.eq("email", email));
            member = (Members) criteria.uniqueResult();
        } catch (NoResultException ex) {
            // If no member found, return null or handle the situation as needed
            member = null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return member;
    }

    public List<Members> getAllMembers() {
        Session session = SessionConfiguration.getSessionFactory().openSession();
        session.beginTransaction();
    
        String hql = "FROM Members m WHERE m.status = :status";
        Query query = session.createQuery(hql);
        query.setParameter("status", "Pending");
        List<Members> membersList = query.list();
        session.getTransaction().commit();
        session.close();
    
        return membersList;
    }

    public List<Members> getApprovedMembers(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        session.beginTransaction();
    
        String hql = "FROM Members m WHERE m.status = :status";
        Query query = session.createQuery(hql);
        query.setParameter("status", "Approved");
        List<Members> membersList = query.list();
        session.getTransaction().commit();
        session.close();
    
        return membersList;
    }

    public int approvedMemberCount(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        session.beginTransaction();
    
        String hql = "SELECT COUNT(*) FROM Members m WHERE m.status = :status";
        Query query = session.createQuery(hql);
        query.setParameter("status", "Approved");
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
    
        return count.intValue();
    }

    public int pendingMemberCount(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        session.beginTransaction();
    
        String hql = "SELECT COUNT(*) FROM Members m WHERE m.status = :status";
        Query query = session.createQuery(hql);
        query.setParameter("status", "Pending");
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
    
        return count.intValue();
    }


    public void updateMemberStatus(Members member) {
        Session session = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
    
            // Set the status to "Approved"
            member.setStatus("Approved");
    
            // Creating a new account and setting the initial balance
            Account account = new Account();
            account.setBalance(0.0);
    
            // Set the account object in the member entity
            member.setAccount(account);
    
            // Save the member entity to update it in the database along with the associated account
            session.saveOrUpdate(member);
    
            session.getTransaction().commit();
        } catch (HibernateException e) {
            // Handle any exceptions if necessary
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    

    public void updateMemberStatusToRejected(Members member) {
        Session session = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            member.setStatus("Rejected"); // Set the status to "Rejected"
            session.update(member);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            // Handle any exceptions if necessary
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void updateMember(Members member){
        Session session = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(member);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            // Handle any exceptions if necessary
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean isEmailExists(String email) {
        Session session = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Members.class);
            criteria.add(Restrictions.eq("email", email));
            Members member = (Members) criteria.uniqueResult();
            return member != null;
        } catch (HibernateException ex) {
            // Handle any exceptions or log errors as needed
            ex.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean isPhoneNumberExists(String phoneNumber) {
        Session session = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Members.class);
            criteria.add(Restrictions.eq("phoneNumber", phoneNumber));
            Members member = (Members) criteria.uniqueResult();
            return member != null;
        } catch (HibernateException ex) {
            // Handle any exceptions or log errors as needed
            ex.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    public void saveAccount(Account account) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(account);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public int getDepositCountForUser(Members member) {
        Session session = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Transactions.class);
            criteria.add(Restrictions.eq("account", member.getAccount()));
            criteria.add(Restrictions.eq("transactionType", "Deposit")); // Specify "Deposit" as the transaction type
            criteria.setProjection(Projections.rowCount());
            Long count = (Long) criteria.uniqueResult();
            return count.intValue();
        } catch (HibernateException e) {
            // Handle any exceptions if necessary
            e.printStackTrace();
            return 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public int getWithdrawCountForUser(Members member) {
        Session session = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Transactions.class);
            criteria.add(Restrictions.eq("account", member.getAccount()));
            criteria.add(Restrictions.eq("transactionType", "Withdrawal")); // Specify "Deposit" as the transaction type
            criteria.setProjection(Projections.rowCount());
            Long count = (Long) criteria.uniqueResult();
            return count.intValue();
        } catch (HibernateException e) {
            // Handle any exceptions if necessary
            e.printStackTrace();
            return 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public int getInternalTransferCountForUser(Members member){
        Session session = null;
        try{
            session = SessionConfiguration.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Transactions.class);
            criteria.add(Restrictions.eq("account", member.getAccount()));
            criteria.add(Restrictions.eq("transactionType", "Internal Transfer"));
            criteria.setProjection(Projections.rowCount());
            Long count = (Long) criteria.uniqueResult();
            return count.intValue();
        }catch (HibernateException e){
            e.printStackTrace();
            return 0;
        }
    }
    

    public Account getAccountByAccountId(int accountId) {
        Session session = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            String hql = "FROM Account a WHERE a.accountId = :accountId";
            Query query = session.createQuery(hql);
            query.setParameter("accountId", accountId);
            return (Account) query.uniqueResult();
        } catch (NoResultException e) {
            // If no account found, return null or handle the situation as needed
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void saveTransaction(Transactions transaction) {
        Session session = null;
        Transaction tx = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(transaction);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Transactions getTransactionById(int transactionId) {
        Session session = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            return (Transactions) session.get(Transactions.class, transactionId);
        } catch (Exception e) {
            // Handle any exceptions if necessary
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    public List<Transactions> getTransactionsForAccount(Account account) {
        Session session = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Transactions.class);
            criteria.add(Restrictions.eq("account", account));
            return criteria.list();
        } catch (Exception e) {
            // Handle any exceptions if necessary
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public long getTotalTransactionCount() {
        Session session = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            String hql = "SELECT COUNT(*) FROM Transactions";
            Query query = session.createQuery(hql);
            return (Long) query.uniqueResult();
        } catch (HibernateException e) {
            // Handle any exceptions if necessary
            e.printStackTrace();
            return 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public int getTotalMemberCount() {
        Session session = null;
        try {
            session = SessionConfiguration.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Members.class);
            criteria.setProjection(Projections.rowCount());
            return ((Long) criteria.uniqueResult()).intValue();
        } catch (HibernateException e) {
            // Handle any exceptions if necessary
            e.printStackTrace();
            return 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
        
}
