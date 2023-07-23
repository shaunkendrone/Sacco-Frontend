package org.pahappa.systems.kimanyisacco.views.authentication;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.Members;

@ManagedBean(name = "userSessionBean")
@SessionScoped
public class UserSessionBean implements Serializable {
    private Members loggedInUser;

    public Members getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Members loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void logout() throws IOException {
        // Invalidate the session and redirect to the login page
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getRequestContextPath() + Hyperlinks.login);
    }
}
