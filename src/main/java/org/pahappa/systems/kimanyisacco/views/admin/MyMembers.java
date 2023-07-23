package org.pahappa.systems.kimanyisacco.views.admin;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.pahappa.Dao.SaccoDao;
import org.pahappa.systems.kimanyisacco.models.Members;

@ManagedBean(name = "myMembers")
@ViewScoped
public class MyMembers {
    private List<Members> members;
    private List<Members> approvedMembers;
    private Members selectedMember;
    public Members getSelectedMember() {
        return selectedMember;
    }

    public void setSelectedMember(Members selectedMember) {
        this.selectedMember = selectedMember;
        System.out.println("Selected member: " + selectedMember.getLastName());
    }

    public MyMembers() {
        // Initialize the members list in the constructor
        SaccoDao saccoDao = new SaccoDao();
        members = saccoDao.getAllMembers();
        approvedMembers = saccoDao.getApprovedMembers();
    }

    public List<Members> getMembers() {
        return members;
    }

    public List<Members> getApprovedMembers() {
        return approvedMembers;
    }

    public void approveUser(Members member) {
        SaccoDao saccoDao = new SaccoDao();
        saccoDao.updateMemberStatus(member);
        members.remove(member); // Remove the approved member from the list
    }

    public void rejectUser(Members member) {
        SaccoDao saccoDao = new SaccoDao();
        saccoDao.updateMemberStatusToRejected(member);
        members.remove(member); // Remove the rejected member from the list
    }
}
