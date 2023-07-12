package org.pahappa.systems.kimanyisacco.views.dashboard;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Date;

@ViewScoped
@ManagedBean(name = "dashboard")
public class Dashboard {
    private Date systemTime;


    public Dashboard() {
        this.systemTime = new Date();
    }

    public Date getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Date systemTime) {
        this.systemTime = systemTime;
    }

}
