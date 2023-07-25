package org.pahappa.systems.kimanyisacco.services;

import java.util.List;

import org.pahappa.systems.kimanyisacco.models.Members;

public interface MemberService {
    void saveMember(Members member);
    Members getMemberByEmail(String email);
    List<Members> getAllMembers();
    List<Members> getApprovedMembers();
    boolean isEmailExists(String email);
    boolean isPhoneNumberExists(String phoneNumber);
}
