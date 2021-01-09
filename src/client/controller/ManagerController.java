package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.gluonhq.charm.glisten.control.ProgressBar;

import client.ChatClient;
import client.ClientUI;
import client.logic.Worker;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * ManagerController class. This class expands the AbstractScenes class that
 * replaces the scenes within the main stage. This class is responsible for the
 * status screen of the park manager where he can see the number of park
 * visitors and the number of subscribers. It is possible to log out of the park
 * manager user, it is possible to go to the reports screen, the management
 * screen and the discounts screen.
 * 
 * @author Liran Amilov
 */

public class ManagerController extends AbstractScenes {
	public static ManagerController instance;

	@FXML
	private Text currentVisitors;

	@FXML
	private Text maxVisitors;

	@FXML
	private Text numOfSubscribers;

	@FXML
	private ProgressBar progressBar;

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
		ChatClient.worker = new Worker(null, null, null, null, null, null);
		ChatClient.connected = false;
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
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
	 * updateNumberOfVisitorAndSub method. This method is responsible for updating
	 * the bar showing the number of visitors on the status screen
	 */

	public void updateNumberOfVisitorAndSub() {

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
		maxVisitors.setText(ChatClient.worker.getPark().getMaxVisitors());
		numOfSubscribers.setText(ChatClient.worker.getPark().getNumOfSub());
	}

}
