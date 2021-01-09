package client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class CacnellationReport implements Initializable{

    @FXML
    private Label reportName;

    @FXML
    private Text monthText;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private BarChart<String, Number> barCancelation;
    

    final static String Regular_Traveler = "Regular Traveler";
    final static String Family_subscription = "Family subscription";
    final static String Group = "Group";
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialize(URL location, ResourceBundle resources) {

		barCancelation.setTitle("Cacnellation Report");
		xAxis.setLabel("Traveler kind");
		yAxis.setLabel("Number of visitors");
		XYChart.Series Canceld = new XYChart.Series<>();
		Canceld.setName("Canceled");
		Canceld.getData().add(new XYChart.Data(Regular_Traveler, 25601.34));
		Canceld.getData().add(new XYChart.Data(Family_subscription, 20148.82));
		Canceld.getData().add(new XYChart.Data(Group, 10000));
//		
		XYChart.Series approvedAndNotArrived = new XYChart.Series();
		approvedAndNotArrived.setName("Not Arrived");
		approvedAndNotArrived.getData().add(new XYChart.Data(Regular_Traveler, 57401.85));
		approvedAndNotArrived.getData().add(new XYChart.Data(Family_subscription, 41941.19));
		approvedAndNotArrived.getData().add(new XYChart.Data(Family_subscription, 45263.37));
		barCancelation.getData().addAll(Canceld,approvedAndNotArrived);
		
	}

}
