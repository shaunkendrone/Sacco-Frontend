package org.pahappa.systems.kimanyisacco.views.charts;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.pahappa.Dao.SaccoDao;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

@ManagedBean(name = "chartJsView")
@RequestScoped
public class ChartJsView implements java.io.Serializable {
    private PieChartModel pieModel;
    private DonutChartModel donutModel;

    private List<Members> approvedMembers;
    private List<Members> members;

    public List<Members> getMembers() {
        return members;
    }

    public void setMembers(List<Members> members) {
        this.members = members;
    }

    public List<Members> getApprovedMembers() {
        return approvedMembers;
    }

    public void setApprovedMembers(List<Members> approvedMembers) {
        this.approvedMembers = approvedMembers;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    @PostConstruct
    public void init(){
        SaccoDao saccoDao = new SaccoDao();
        members = saccoDao.allMembers();
        approvedMembers = saccoDao.getApprovedMembers();
        createPieModel();
        createDonutModel();
    }

    private void createPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();
    
        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
    
        int maleCount = 0;
        int femaleCount = 0;
    
        // Iterate over the approvedMembers list to count genders
        for (Members member : approvedMembers) {
            if (member.getGender().equalsIgnoreCase("Male")) {
                maleCount++;
            } else if (member.getGender().equalsIgnoreCase("Female")) {
                femaleCount++;
            }
        }
    
        // Add the counts to the pie chart dataset
        values.add(maleCount);
        values.add(femaleCount);
        dataSet.setData(values);
    
        // Set background colors for male and female slices in the chart
        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(54, 162, 235)"); // Blue for Male
        bgColors.add("rgb(255, 99, 132)"); // Red for Female
        dataSet.setBackgroundColor(bgColors);
    
        data.addChartDataSet(dataSet);
    
        // Set the labels for male and female slices in the chart
        List<String> labels = new ArrayList<>();
        labels.add("Male");
        labels.add("Female");
        data.setLabels(labels);
    
        pieModel.setData(data);
    }
    
    public void createDonutModel() {
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();
        DonutChartOptions options = new DonutChartOptions();
        // options.setMaintainAspectRatio(false);
        donutModel.setOptions(options);

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        
        // Calculate the count of approved, rejected, and pending members
        int approvedCount = 0;
        int rejectedCount = 0;
        int pendingCount = 0;

        for (Members member : members) {
            String status = member.getStatus(); // Assuming you have a getStatus() method in Members
            if (status.equalsIgnoreCase("Approved")) {
                approvedCount++;
            } else if (status.equalsIgnoreCase("Rejected")) {
                rejectedCount++;
            } else if (status.equalsIgnoreCase("Pending")) {
                pendingCount++;
            }
        }
        
        values.add(approvedCount);
        values.add(rejectedCount);
        values.add(pendingCount);
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(0, 123, 255)"); // Blue for Approved
        bgColors.add("rgb(255, 99, 132)"); // Red for Rejected
        bgColors.add("rgb(255, 205, 86)"); // Yellow for Pending
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Approved");
        labels.add("Rejected");
        labels.add("Pending");
        data.setLabels(labels);

        donutModel.setData(data);
    }

    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
    }

}
