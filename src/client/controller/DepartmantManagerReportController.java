package client.controller;

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
import javafx.scene.control.Button;
import javafx.scene.text.Text;

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
