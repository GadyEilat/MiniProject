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

/**
 * ManagerReportController class. This class expands the AbstractScenes class
 * that replaces the scenes within the main stage. This class is responsible for
 * the park manager's report screen. It is possible to log out from the park
 * manager user, it is possible to go to the status screen, the management
 * screen and the discount screen.
 * 
 * @author Liran Amilov
 */

public class ManagerReportController extends AbstractScenes {
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

	/**
	 * logout method. This method is responsible for disconnecting from the
	 * department manager user and transferring to the main login screen.
	 * 
	 * @param event
	 */

	@FXML
	void logout(ActionEvent event) {
//    	exitConnection
		DataTransfer data = new DataTransfer(TypeOfMessage.LOGOUT, ChatClient.worker);
		ClientUI.chat.accept(data);
		ChatClient.connected = false;
		ChatClient.worker = new Worker(null, null, null, null, null, null);
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
	}

	/**
	 * makeReportNumOfVisitors method.
	 * 
	 * @param event
	 */

	@FXML
	void makeReportNumOfVisitors(ActionEvent event) {

	}

	/**
	 * makeReportRevenue method.
	 * 
	 * @param event
	 */

	@FXML
	void makeReportRevenue(ActionEvent event) {
    	switchScenes("/client/boundaries/totalAmountOfVisitorsReport.fxml", "Manager");
	}

	/**
	 * makeReportUsage method.
	 * 
	 * @param event
	 */
    	
	@FXML
	void makeReportUsage(ActionEvent event) {
		switchScenes("/client/boundaries/MonthlyIncomeReport.fxml", "Manager");
	}

	/**
	 * showDiscount method. This method is responsible for transferring the screen
	 * to the discount screen.
	 * 
	 * @param event
	 */

	@FXML
	void showDiscount(ActionEvent event) {
		switchScenes("/client/boundaries/DiscountManager.fxml", "Manager");
	}

	/**
	 * showManagingPark method. This method is responsible for transferring the
	 * screen to the park management screen.
	 * 
	 * @param event
	 * @throws IOException
	 */

	@FXML
	void showManagingPark(ActionEvent event) throws IOException {

		switchScenes("/client/boundaries/managingPark.fxml", "Manager");
	}

	/**
	 * showReport method. This method is responsible for transferring the screen to
	 * the reports screen.
	 * 
	 * @param event
	 */

	@FXML
	void showReport(ActionEvent event) {
		switchScenes("/client/boundaries/reportManager.fxml", "Manager");
	}

	/**
	 * showStatus method. This method is responsible for transferring the screen to
	 * the status screen.
	 * 
	 * @param event
	 */

	@FXML
	void showStatus(ActionEvent event) {
		switchScenes("/client/boundaries/manager.fxml", "Manager");
	}

	/**
	 * initialize method. This method is responsible for defining variables by
	 * communicating with the server, is responsible for screen visibility (caption
	 * and titles) and on-screen functionality.
	 * 
	 * @param location
	 * @param resources
	 */

	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		managerName.setText("Hello " + ChatClient.worker.getWorkerName());
	}

}
