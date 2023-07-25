package org.pahappa.systems.kimanyisacco.views.authentication;

import java.io.IOException;

import javax.faces.application.FacesMessage;
// import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.MemberServiceImp;
// import org.primefaces.PrimeFaces;

@ManagedBean(name = "memberForm")
@SessionScoped
public class MemberForm {
    FacesMessage message;
    private Members member;
    private MemberService mimp;

    public Members getMember() {
        return member;
    }

    public void setMember(Members member) {
        this.member = member;
    }

    public MemberForm() {
        this.member = new Members();
        this.mimp = new MemberServiceImp();
    }

    public void doRegister() throws IOException {
        boolean emailExists = mimp.isEmailExists(member.getEmail());
        boolean phoneNumberExists = mimp.isPhoneNumberExists(member.getPhoneNumber());

        if (emailExists) {
            // Email already exists, display an error message
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Email already in use");
            FacesContext.getCurrentInstance().addMessage("messages", message);
            return; // Return without saving the member
        }

        if (phoneNumberExists) {
            // Phone number already exists, display an error message
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Phone number already in use");
            FacesContext.getCurrentInstance().addMessage("messages", message);
            return; // Return without saving the member
        }
        // Save the member to the database
        mimp.saveMember(member);
        member = new Members();

        // Redirect to the login page after the member is saved
        
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Member Registered Successfully");
        FacesContext.getCurrentInstance().addMessage("messages", message);
        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        FacesContext.getCurrentInstance().getExternalContext().redirect(path + Hyperlinks.login);
    }
}
