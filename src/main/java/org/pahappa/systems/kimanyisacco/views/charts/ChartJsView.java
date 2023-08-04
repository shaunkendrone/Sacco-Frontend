package org.pahappa.systems.kimanyisacco.views.charts;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.pahappa.Dao.SaccoDao;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.models.Transactions;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
// import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
// import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;
// import org.primefaces.model.charts.optionconfig.title.Title;
// import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

@ManagedBean(name = "chartJsView")
@RequestScoped
public class ChartJsView implements java.io.Serializable {
    private PieChartModel pieModel;
    private DonutChartModel donutModel;
    private BarChartModel ageDistributionModel;
    private DonutChartModel donutModel2;
    private LineChartModel lineModel;

    
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

    public DonutChartModel getDonutModel2() {
        return donutModel2;
    }

    public void setDonutModel2(DonutChartModel donutModel2) {
        this.donutModel2 = donutModel2;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }


    @PostConstruct
    public void init() {
        SaccoDao saccoDao = new SaccoDao();
        members = saccoDao.allMembers();
        approvedMembers = saccoDao.getApprovedMembers();
        createPieModel();
        createDonutModel();
        createAgeDistributionModel();
        createLineModel();
        
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

    public void createAgeDistributionModel() {
        ageDistributionModel = new BarChartModel();
        ChartData data = new ChartData();
        ageDistributionModel.setData(data);

        BarChartDataSet dataSet = new BarChartDataSet();
        List<Number> values = new ArrayList<>();

        // Get the age distribution data from your DAO
        SaccoDao saccoDao = new SaccoDao();
        List<Integer> ageDistribution = saccoDao.getAgeDistribution();

        values.addAll(ageDistribution);
        dataSet.setData(values);

        // Assuming you want varying colors for the bars
        List<String> backgroundColors = new ArrayList<>();
        backgroundColors.add("rgb(255, 99, 132)");
        backgroundColors.add("rgb(54, 162, 235)");
        backgroundColors.add("rgb(255, 205, 86)");
        backgroundColors.add("rgb(75, 192, 192)");
        backgroundColors.add("rgb(153, 102, 255)");
        dataSet.setBackgroundColor(backgroundColors);

        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();
        // Make sure the number of labels matches the number of data points
        labels.add("18-25");
        labels.add("25-32");
        labels.add("32-40");
        labels.add("40-50");
        labels.add("50+");
        data.setLabels(labels);

        BarChartOptions options = new BarChartOptions();
        Legend legend = new Legend();
        legend.setDisplay(true); // Display the legend
        options.setLegend(legend);
        ageDistributionModel.setOptions(options);

        ageDistributionModel.setData(data);
    }

    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
    }

    public BarChartModel getAgeDistributionModel() {
        return ageDistributionModel;
    }

    public void setAgeDistributionModel(BarChartModel ageDistributionModel) {
        this.ageDistributionModel = ageDistributionModel;
    }

    public void createLineModel() {
    lineModel = new LineChartModel();
    ChartData data = new ChartData();

    // Initialize datasets for each transaction type
    LineChartDataSet depositDataSet = new LineChartDataSet();
    LineChartDataSet withdrawalDataSet = new LineChartDataSet();
    LineChartDataSet transferDataSet = new LineChartDataSet();

    // Retrieve all transactions from the database
    SaccoDao saccoDao = new SaccoDao();
    List<Transactions> transactions = saccoDao.getAllTransactions();

    List<Object> depositValues = new ArrayList<>();
    List<Object> withdrawalValues = new ArrayList<>();
    List<Object> transferValues = new ArrayList<>();
    
    // Process the transactions and populate the datasets
    for (Transactions transaction : transactions) {
        // Assuming you have a "transactionType" field in Transactions class
        String transactionType = transaction.getTransactionType();
        
        // Assuming you have a "transactionAmount" field in Transactions class
        int transactionAmount = (int) transaction.getTransactionAmount();
        
        if ("Deposit".equals(transactionType)) {
            depositValues.add(transactionAmount);
            withdrawalValues.add(null); // Placeholder for other datasets
            transferValues.add(null);   // Placeholder for other datasets
        } else if ("Withdrawal".equals(transactionType)) {
            depositValues.add(null);    // Placeholder for other datasets
            withdrawalValues.add(transactionAmount);
            transferValues.add(null);   // Placeholder for other datasets
        } else if ("Internal Transfer".equals(transactionType)) {
            depositValues.add(null);    // Placeholder for other datasets
            withdrawalValues.add(null); // Placeholder for other datasets
            transferValues.add(transactionAmount);
        }
    }

    depositDataSet.setData(depositValues);
    depositDataSet.setFill(false);
    depositDataSet.setLabel("Deposits");
    depositDataSet.setBorderColor("rgb(75, 192, 192)");
    depositDataSet.setTension(0.1);

    withdrawalDataSet.setData(withdrawalValues);
    withdrawalDataSet.setFill(false);
    withdrawalDataSet.setLabel("Withdrawals");
    withdrawalDataSet.setBorderColor("rgb(192, 75, 75)");
    withdrawalDataSet.setTension(0.1);

    transferDataSet.setData(transferValues);
    transferDataSet.setFill(false);
    transferDataSet.setLabel("Internal Transfers");
    transferDataSet.setBorderColor("rgb(75, 75, 192)");
    transferDataSet.setTension(0.1);

    data.addChartDataSet(depositDataSet);
    data.addChartDataSet(withdrawalDataSet);
    data.addChartDataSet(transferDataSet);

    // Populate labels for days of the week
    List<String> daysOfWeekLabels = new ArrayList<>();
    daysOfWeekLabels.add("Monday");
    daysOfWeekLabels.add("Tuesday");
    daysOfWeekLabels.add("Wednesday");
    daysOfWeekLabels.add("Thursday");
    daysOfWeekLabels.add("Friday");
    daysOfWeekLabels.add("Saturday");
    daysOfWeekLabels.add("Sunday");
    data.setLabels(daysOfWeekLabels);

    // Options and title remain the same

    LineChartOptions options = new LineChartOptions();
    Title title = new Title();
    title.setDisplay(true);
    title.setText("Line Chart");
    options.setTitle(title);

    lineModel.setOptions(options);
    lineModel.setData(data);
}


}
