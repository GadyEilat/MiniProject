package client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.gluonhq.charm.glisten.control.ProgressBar;

import client.ChatClient;
import client.ClientUI;
import client.logic.ParkInfo;
import client.logic.Worker;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * DepartmantManagerController class. This class expands the AbstractScenes
 * class that replaces the scenes within the main stage. This method is
 * responsible for the status screen of the department manager where the number
 * of visitors in the park out of the limited number of visitors is displayed.
 * It is possible to log out of the department manager user, there are options
 * to go to the reports screen and the approvals screen.
 * 
 * @author Liran Amilov
 */

public class DepartmantManagerController extends AbstractScenes {
	public static DepartmantManagerController instance;

	@FXML
	private Text currentNumOfVisitors;

	@FXML
	private Text currentMaxOfVisitors;

	@FXML
	private ProgressBar progressBar;

	@FXML
	private Text departmentManagerName;

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnStatus;

	@FXML
	private Button btnReports;

	@FXML
	private Button btnApproval;

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
		ChatClient.worker = new Worker(null, null, null, null, null, null);
		ChatClient.parkInfo = new ParkInfo(null, null, null, null, null);
		ChatClient.connected = false;
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
	}

	/**
	 * showApproval method. This method is responsible for transferring the screen
	 * to the approvals screen of the department manager
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
	 * the status screen of the department manager
	 * 
	 * @param event
	 */

	@FXML
	void showStatus(ActionEvent event) {
		switchScenes("/client/boundaries/mainDepartmantManager.fxml", "Departmant Manager");
	}

	/**
	 * updateNumberOfVisitor method. This method is responsible for updating the bar
	 * showing the number of visitors on the status screen
	 */

	public void updateNumberOfVisitor() {

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
		currentMaxOfVisitors.setText(ChatClient.worker.getPark().getMaxVisitors());

	}

}
