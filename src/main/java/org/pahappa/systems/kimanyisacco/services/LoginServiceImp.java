package org.pahappa.systems.kimanyisacco.services;

import org.mindrot.jbcrypt.BCrypt;
import org.pahappa.systems.kimanyisacco.enumerations.Status;
import org.pahappa.systems.kimanyisacco.models.Members;

public class LoginServiceImp implements LoginService {
    private MemberService memberService;

    public LoginServiceImp() {
        this.memberService = new MemberServiceImp();
    }

    @Override
    public boolean authenticate(String email, String password, Status status){
        Members member = memberService.getMemberByEmail(email);
        if (member != null) {
            if (BCrypt.checkpw(password, member.getPassword())) {
                if (member.getStatus().equals(status)) {
                    return true;
                }
            }
        }
        return false;
    }

     

}
