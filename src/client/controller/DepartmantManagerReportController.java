package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.ParkInfo;
import client.logic.Worker;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Side;

public class DepartmantManagerReportController extends AbstractScenes{
	public static DepartmantManagerReportController instance;

    @FXML
    private Button btnVisitReport;
    
    @FXML
    private Text departmentManagerName;

    @FXML
    private Button btnCancellationReport;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnStatus;

    @FXML
    private Button btnReports;

    @FXML
    private Button btnApproval;


    @FXML
    void showCacnellationReport(ActionEvent event) {
		Stage helpWindow = new Stage();
		FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/client/boundaries/CacnellationReportController.fxml"));
		Parent current = null;
		try {
			current = fxmlLoad.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		helpWindow.setTitle("Cacnellation Report");
		Scene scene = new Scene(current);
		helpWindow.setMinHeight(600);
		helpWindow.setMinWidth(800);
		helpWindow.setMaxHeight(600);
		helpWindow.setMaxWidth(800);
		helpWindow.setScene(scene);
		helpWindow.show();
    	  
//		final CategoryAxis xAxis = new CategoryAxis();
//		final NumberAxis yAxis = new NumberAxis();
//		BarChart barCancelation = new BarChart<String, Number>(xAxis, yAxis);
//
//		barCancelation.setTitle("Cacnellation Report");
//		xAxis.setLabel("Traveler kind");
//		yAxis.setLabel("Number of visitors");
//		XYChart.Series Canceld = new XYChart.Series();
//		Canceld.setName("2003");
//		Canceld.getData().add(new XYChart.Data("austria", 25601.34));
//		Canceld.getData().add(new XYChart.Data("brazil", 20148.82));
//		Canceld.getData().add(new XYChart.Data("france", 10000));
//		
//		XYChart.Series approvedAndNotArrived = new XYChart.Series();
//		approvedAndNotArrived.setName("2004");
//		approvedAndNotArrived.getData().add(new XYChart.Data("austria", 57401.85));
//		approvedAndNotArrived.getData().add(new XYChart.Data("brazil", 41941.19));
//		approvedAndNotArrived.getData().add(new XYChart.Data("france", 45263.37));
//		barCancelation.getData().addAll(Canceld,approvedAndNotArrived);
//		StackPane pane = new StackPane(barCancelation);
//		//Setting the Scene
//    		      Scene scene = new Scene(pane, 595, 300);
//    		      Stage helpWindow = new Stage();
//    		      helpWindow.setTitle("Bar Chart");
//    		      helpWindow.setScene(scene);
//    		      helpWindow.show();
    }
    
    @FXML
    void showVisitReport(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {
    	//exit Logout
		DataTransfer data = new DataTransfer(TypeOfMessage.LOGOUT, ChatClient.worker);
		ClientUI.chat.accept(data);
		ChatClient.parkInfo = new ParkInfo(null, null, null, null, null);
		ChatClient.worker = new Worker(null, null, null, null, null, null);
		ChatClient.connected = false;
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
    }

    @FXML
    void showApproval(ActionEvent event) {
		switchScenes("/client/boundaries/approveManagerChanges.fxml", "Departmant Manager");
    }

    @FXML
    void showReports(ActionEvent event) {
		switchScenes("/client/boundaries/reportsDM.fxml", "Departmant Manager");
    }

    @FXML
    void showStatus(ActionEvent event) {
		switchScenes("/client/boundaries/mainDepartmantManager.fxml", "Departmant Manager");
    }
    
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		departmentManagerName.setText("Hello " + ChatClient.worker.getWorkerName());
	}


}
