package org.pahappa.systems.kimanyisacco.views.authentication;

import java.io.IOException;

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
        // Save the member to the database
        mimp.saveMember(member);
        member = new Members();

        // Redirect to the login page after the member is saved
        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        FacesContext.getCurrentInstance().getExternalContext().redirect(path + Hyperlinks.login);

    }
}
