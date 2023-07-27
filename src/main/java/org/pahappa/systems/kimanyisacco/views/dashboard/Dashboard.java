package org.pahappa.systems.kimanyisacco.views.dashboard;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
// import javax.faces.bean.SessionScoped;
// import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.Members;
// import org.pahappa.systems.kimanyisacco.User.User;
import org.pahappa.systems.kimanyisacco.views.authentication.UserSessionBean;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.MemberServiceImp;

@ManagedBean(name = "dashboard")
@SessionScoped
public class Dashboard {
    MemberService memberService = new MemberServiceImp();
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String firstName;
    private String lastName;
    private String email;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String phoneNumber;
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String address;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String occupation;
    

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private boolean editMode = false;

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

     // Method to toggle edit mode for a given field
     public void toggleEditMode(String field) {
        switch (field) {
            case "firstName":
                setEditMode(!isEditMode());
                break;
            case "lastName":
                setEditMode(!isEditMode());
                break;
            case "email":
                setEditMode(!isEditMode());
                break;
            case "phoneNumber":
                setEditMode(!isEditMode());
                break;
            case "address":
                setEditMode(!isEditMode());
                break;
            case "occupation":
                setEditMode(!isEditMode());
                break;
            default:
                break;
        }
    }

    @ManagedProperty("#{userSessionBean}")
    private UserSessionBean userSessionBean;

    public Dashboard() throws IOException {
        Members loggedInUser = (Members) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .get("loggedInUser");
        if (loggedInUser != null) {
            firstName = loggedInUser.getFirstName();
            lastName = loggedInUser.getLastName();
            email = loggedInUser.getEmail();
            phoneNumber = loggedInUser.getPhoneNumber();
            address = loggedInUser.getAddress();
            occupation = loggedInUser.getOccupation();
        } else {
            // firstName = "Guest";
            String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            FacesContext.getCurrentInstance().getExternalContext().redirect(path + Hyperlinks.login);
        }
    }

     // Method to update the user's profile
    public void updateProfile() {
        Members loggedInUser = (Members) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .get("loggedInUser");
        if (loggedInUser != null) {
            // Update the user's information in the session
            loggedInUser.setFirstName(firstName);
            loggedInUser.setLastName(lastName);
            loggedInUser.setEmail(email);
            loggedInUser.setPhoneNumber(phoneNumber);
            loggedInUser.setAddress(address);
            loggedInUser.setOccupation(occupation);

            // Save the updated user information to the database using the MemberService
            memberService.updateMember(loggedInUser);

            // Display a success message if needed.
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile Updated", "Your profile has been updated successfully."));

            // Switch back to view mode
            setEditMode(false);

            firstName = loggedInUser.getFirstName();
            lastName = loggedInUser.getLastName();
            email = loggedInUser.getEmail();
            phoneNumber = loggedInUser.getPhoneNumber();
            address = loggedInUser.getAddress();
            occupation = loggedInUser.getOccupation();

        }
    }

    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }

}
