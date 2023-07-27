package org.pahappa.systems.kimanyisacco.views.dashboard;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.pahappa.Dao.SaccoDao;
import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.services.AdminService;
import org.pahappa.systems.kimanyisacco.services.AdminServiceImp;
import org.pahappa.systems.kimanyisacco.views.authentication.UserSessionBean;

@ManagedBean(name = "adminDashboard")
@ViewScoped
public class AdminDashboard {
    private String firstName;
    private AdminService adminService;
    
    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    private int totalMembers;
    private long totalTransactions;
    private int approvedMembers;
    private int pendingMembers;

    public int getPendingMembers() {
        return pendingMembers;
    }

    public void setPendingMembers(int pendingMembers) {
        this.pendingMembers = pendingMembers;
    }

    public int getApprovedMembers() {
        return approvedMembers;
    }

    public void setApprovedMembers(int approvedMembers) {
        this.approvedMembers = approvedMembers;
    }

    public long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public int getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(int totalMembers) {
        this.totalMembers = totalMembers;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @ManagedProperty("#{userSessionBean}")
    private UserSessionBean userSessionBean;

    public UserSessionBean getUserSessionBean() {
        return userSessionBean;
    }

    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }

    public AdminDashboard() throws IOException{
        Members adminUser = (Members) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("adminUser");
        if(adminUser != null){
            firstName = adminUser.getFirstName();
            SaccoDao saccoDao = new SaccoDao();
            adminService = new AdminServiceImp(saccoDao);

            totalMembers = adminService.getTotalMemberCount();
            totalTransactions = (adminService.getTotalTransactionCount());
            approvedMembers = adminService.getApprovedMemberCount();
            pendingMembers = adminService.getPendingMemberCount();
    }else{
        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            FacesContext.getCurrentInstance().getExternalContext().redirect(path + Hyperlinks.login);
    }
}

    
}
