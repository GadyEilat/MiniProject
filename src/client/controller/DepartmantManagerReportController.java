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

/**
 * DepartmantManagerReportController class. This class expands the
 * AbstractScenes class that replaces the scenes within the main stage. This
 * class is responsible for the reports that the department head can produce. It
 * is possible to log out of the department manager user, it is possible to go
 * to the status screen or the confirmation screen.
 * 
 * @author Liran Amilov
 */

public class DepartmantManagerReportController extends AbstractScenes {
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

	/**
	 * showCacnellationReport method. This method presents the report of
	 * cancellations of visits by visitors or visitors who did not come in the last
	 * month.
	 * 
	 * @param event
	 */

	@FXML
	void showCacnellationReport(ActionEvent event) {

	}

	/**
	 * showVisitReport method. This method presents the report of the visitors who
	 * approved and came to the park in the last month.
	 * 
	 * @param event
	 */

	@FXML
	void showVisitReport(ActionEvent event) {

	}

	/**
	 * logout method. This method is responsible for disconnecting from the
	 * department manager user and transferring to the main login screen.
	 * 
	 * @param event
	 */

	@FXML
	void logout(ActionEvent event) {
		// exit Logout
		DataTransfer data = new DataTransfer(TypeOfMessage.LOGOUT, ChatClient.worker);
		ClientUI.chat.accept(data);
		ChatClient.parkInfo = new ParkInfo(null, null, null, null, null);
		ChatClient.worker = new Worker(null, null, null, null, null, null);
		ChatClient.connected = false;
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
	}

	/**
	 * showApproval method. This method is responsible for transferring the screen
	 * to the approvals screen of the department manager.
	 * 
	 * @param event
	 */

	@FXML
	void showApproval(ActionEvent event) {
		switchScenes("/client/boundaries/approveManagerChanges.fxml", "Departmant Manager");
	}

	/**
	 * showReports method. This method is responsible for transferring the screen to
	 * the reports screen of the department manager.
	 * 
	 * @param event
	 */

	@FXML
	void showReports(ActionEvent event) {
		switchScenes("/client/boundaries/reportsDM.fxml", "Departmant Manager");
	}

	/**
	 * showStatus method. This method is responsible for transferring the screen to
	 * the status screen of the department manager.
	 * 
	 * @param event
	 */

	@FXML
	void showStatus(ActionEvent event) {
		switchScenes("/client/boundaries/mainDepartmantManager.fxml", "Departmant Manager");
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
		departmentManagerName.setText("Hello " + ChatClient.worker.getWorkerName());
	}

}
