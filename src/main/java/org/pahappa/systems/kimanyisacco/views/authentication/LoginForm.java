package org.pahappa.systems.kimanyisacco.views.authentication;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import java.io.IOException;

import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.LoginMember;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.services.LoginService;
import org.pahappa.systems.kimanyisacco.services.LoginServiceImp;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.MemberServiceImp;
// import org.primefaces.PrimeFaces;

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

    private void addFlashMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        facesContext.addMessage(null, new FacesMessage(severity, summary, detail));
    }

    private void initializeAdminUser() {
        // Check if the admin user already exists in the database
        Members adminUser = memberService.getMemberByEmail("admin1@gmail.com");
        if (adminUser == null) {
            // Admin user doesn't exist, create and save it in the database
            Members admin = new Members();
            admin.setFirstName("Shaun");
            admin.setLastName("Admin");
            admin.setEmail("admin1@gmail.com");
            admin.setPhoneNumber("0756453219");
            admin.setDateOfBirth(java.sql.Date.valueOf("2001-07-20")); // Set the date of birth
            admin.setGender("Male");
            admin.setPassword("adminpassword");
            admin.setStatus("Admin");
            admin.setAddress("Makerere");
            admin.setOccupation("Software Engineer"); 
            // Set other admin properties if needed

            // Save the admin user using the MemberService
            memberService.saveMember(admin);
        }
    }

    public void doLogin() throws IOException {
        // Initialize the admin user when the login form is accessed
        initializeAdminUser();
    

        boolean adminLoginSuccessful = loginService.authenticate(loginMember.getEmail(), loginMember.getPassword(), "Admin");
        boolean loginSuccessful = loginService.authenticate(loginMember.getEmail(), loginMember.getPassword(), "Approved");
        if (loginSuccessful || adminLoginSuccessful) {
    
            Members member = memberService.getMemberByEmail(loginMember.getEmail());
            if (member != null) {
                String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    
                // Check if the user is an admin, and redirect accordingly
                if (member.getEmail().equals("admin1@gmail.com")) {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("adminUser", member);
                    loginMember = new LoginMember();
                    addFlashMessage(FacesMessage.SEVERITY_INFO, "Login Successful",
                        "Welcome Admin");
                    FacesContext.getCurrentInstance().getExternalContext().redirect(path + Hyperlinks.adminDashboard);
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInUser", member);
                    loginMember = new LoginMember();
                    addFlashMessage(FacesMessage.SEVERITY_INFO, "Login Successful",
                        "Welcome, " + member.getFirstName() + " " + member.getLastName() + "!");
                    FacesContext.getCurrentInstance().getExternalContext().redirect(path + Hyperlinks.dashboard);
                }
 
            } else {
                // Redirect to the login page with an error message
                System.out.println("Member is null");
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid Credentials");
            }
    
        } else {
    
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid Credentials");
            FacesContext.getCurrentInstance().addMessage("messages", message);
    
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
