package org.pahappa.systems.kimanyisacco.views.authentication;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.LoginMember;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.services.LoginService;
import org.pahappa.systems.kimanyisacco.services.LoginServiceImp;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.MemberServiceImp;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "loginForm")
@SessionScoped
public class LoginForm {
    private LoginMember loginMember = new LoginMember();
    FacesMessage message;

    public LoginMember getLoginMember() {
        return loginMember;
    }

    public void setLoginMember(LoginMember loginMember) {
        this.loginMember = loginMember;
    }

    @ManagedProperty("#{userSessionBean}")
    private UserSessionBean userSessionBean;

    private MemberService memberService = new MemberServiceImp();

    private LoginService loginService = new LoginServiceImp();

    public void doLogin() throws IOException {

        boolean loginSuccessful = loginService.authenticate(loginMember.getEmail(), loginMember.getPassword(), "Approved");
        if (loginSuccessful) {

            Members member = memberService.getMemberByEmail(loginMember.getEmail());
        if (member != null) {
            // Store the logged-in user's details in the session
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInMember", member);

            loginMember = new LoginMember(); // Reset the loginMember object
            String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            FacesContext.getCurrentInstance().getExternalContext().redirect(path + Hyperlinks.dashboard);
            
            System.out.println(member.getFirstName());
        } else {
            // Redirect to the login page with an error message
            System.out.println("Member is null");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid Password");
        }
            
        } else {

            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid Credentials");
            FacesContext.getCurrentInstance().addMessage("messages",message);
            
            
        }
    }

    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }

    public void toLogin() throws IOException {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        FacesContext.getCurrentInstance().getExternalContext().redirect(path + Hyperlinks.login);
    }
}
