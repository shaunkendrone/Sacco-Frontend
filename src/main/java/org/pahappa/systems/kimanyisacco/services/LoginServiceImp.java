package org.pahappa.systems.kimanyisacco.services;

import org.mindrot.jbcrypt.BCrypt;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.views.authentication.UserSessionBean;

public class LoginServiceImp implements LoginService {
    private MemberService memberService;
    private UserSessionBean userSessionBean;

    public LoginServiceImp() {
        this.memberService = new MemberServiceImp();
        this.userSessionBean = new UserSessionBean();
    }

    @Override
    public boolean authenticate(String email, String password, String status){
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
