package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.Worker;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class ManagerReportController extends AbstractScenes{
	public static ManagerReportController instance;

    @FXML
    private Button totalNumOfVisitors;

    @FXML
    private Button usageRepotr;

    @FXML
    private Button revenueReportPerMonth;

    @FXML
    private Text managerName;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnStatus;

    @FXML
    private Button btnReport;

    @FXML
    private Button btmManagingPark;

    @FXML
    private Button btnDiscount;

	@FXML
	void logout(ActionEvent event) {
//    	exitConnection
		DataTransfer data = new DataTransfer(TypeOfMessage.LOGOUT, ChatClient.worker);
		ClientUI.chat.accept(data);
		ChatClient.connected = false;
		ChatClient.worker = new Worker(null, null, null, null, null, null);
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
	}

    @FXML
    void makeReportNumOfVisitors(ActionEvent event) {
    	switchScenes("/client/boundaries/totalAmountOfVisitorsReport.fxml", "Manager");
    }

    @FXML
    void makeReportRevenue(ActionEvent event) {
    	switchScenes("/client/boundaries/MonthlyIncomeReport.fxml", "Manager");
    }

    @FXML
    void makeReportUsage(ActionEvent event) {

    }

	@FXML
	void showDiscount(ActionEvent event) {
		switchScenes("/client/boundaries/DiscountManager.fxml", "Manager");
	}

	@FXML
	void showManagingPark(ActionEvent event) throws IOException {

		switchScenes("/client/boundaries/managingPark.fxml", "Manager");
	}

	@FXML
	void showReport(ActionEvent event) {
		switchScenes("/client/boundaries/reportManager.fxml", "Manager");
	}

	@FXML
	void showStatus(ActionEvent event) {
		switchScenes("/client/boundaries/manager.fxml", "Manager");
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		managerName.setText("Hello " + ChatClient.worker.getWorkerName());

	}
	

}
