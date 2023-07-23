package org.pahappa.systems.kimanyisacco.views.profile;

// import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.pahappa.systems.kimanyisacco.views.authentication.UserSessionBean;

import java.io.IOException;

@ManagedBean(name = "profile")
@SessionScoped
public class Profile {
    private String firstName;

    @ManagedProperty("#{userSessionBean}")
    private UserSessionBean userSessionBean;

    public void init() {
        // Retrieve the user's first name from the session
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        firstName = (String) externalContext.getSessionMap().get("loggedInMember");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }

    public void logout() throws IOException {
        // Clear the session and redirect to the login page
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + "/pages/auth/Login.xhtml");
    }

    public void viewProfile() throws IOException {
        // Redirect to the profile page if the session exists
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        if (externalContext.getSession(false) != null) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/pages/transaction/Profile.xhtml");
        }
    }
}
