package client.controller;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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

	@FXML
	void logout(ActionEvent event) {
//    	exitConnection
		DataTransfer data = new DataTransfer(TypeOfMessage.LOGOUT, ChatClient.worker);
		ClientUI.chat.accept(data);
		ChatClient.worker = new Worker(null, null, null, null, null, null);
		ChatClient.connected = false;
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
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


	public void updateNumberOfVisitorAndSub() {
		progressBar.setProgress(Integer.valueOf(ChatClient.parkInfo.getCurrentVisitors())/Integer.valueOf(ChatClient.worker.getPark().getMaxVisitors()));
		currentVisitors.setText(ChatClient.parkInfo.getCurrentVisitors());
	}

	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		managerName.setText("Hello " + ChatClient.worker.getWorkerName());
		maxVisitors.setText(ChatClient.worker.getPark().getMaxVisitors());
		numOfSubscribers.setText(ChatClient.worker.getPark().getNumOfSub());
		DataTransfer data = new DataTransfer(TypeOfMessage.REQUESTINFO,
				new ParkInfo(null , ChatClient.worker.getPark().getNumberOfPark(),null,null,null,ChatClient.worker.getRole()));
		ClientUI.chat.accept(data);
		
	}

}
