package org.pahappa.systems.kimanyisacco.views.dashboard;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
// import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.Members;
// import org.pahappa.systems.kimanyisacco.User.User;
import org.pahappa.systems.kimanyisacco.views.authentication.UserSessionBean;

@ManagedBean(name = "dashboard")
@ViewScoped
public class Dashboard {
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String firstName;

    @ManagedProperty("#{userSessionBean}")
    private UserSessionBean userSessionBean;

    public Dashboard() throws IOException{
        Members loggedInMember = (Members) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInMember");
        if (loggedInMember != null) {
            firstName = loggedInMember.getFirstName();
        } else {
            // firstName = "Guest";
            String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            FacesContext.getCurrentInstance().getExternalContext().redirect(path + Hyperlinks.login);
        }
    }

    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }
    

}
