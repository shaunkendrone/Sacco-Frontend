package org.pahappa.systems.kimanyisacco.services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.pahappa.Dao.SaccoDao;
import org.pahappa.systems.kimanyisacco.models.Members;

public class MemberServiceImp implements MemberService{
    private final SaccoDao memberDao = new SaccoDao();
    @Override
    public void saveMember(Members member) {
        String hashedPassword = hashPassword(member.getPassword());
        member.setPassword(hashedPassword);
        memberDao.saveMember(member);
    }

    private String hashPassword(String password) {
        // Use a strong hashing algorithm
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public Members getMemberByEmail(String email) {
        return memberDao.getMemberByEmail(email);
    }

    @Override
    public List<Members> getAllMembers() {
        return memberDao.getAllMembers();
    }

    @Override
    public List<Members> getApprovedMembers() {
        return memberDao.getApprovedMembers();
    }

    @Override
    public boolean isEmailExists(String email) {
        // Delegate the call to the SaccoDao's isEmailExists() method
        return memberDao.isEmailExists(email);
    }

    @Override
    public boolean isPhoneNumberExists(String phoneNumber) {
        return memberDao.isPhoneNumberExists(phoneNumber);
    }
    
    @Override
    public void updateMember(Members member) {
        // Call the SaccoDao to update the member in the database
        memberDao.updateMember(member);
    }
}
