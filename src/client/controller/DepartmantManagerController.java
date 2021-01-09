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

public class DepartmantManagerController extends AbstractScenes{
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

    @FXML
    void logout(ActionEvent event) {
    	//exit Logout
		DataTransfer data = new DataTransfer(TypeOfMessage.LOGOUT, ChatClient.worker);
		ClientUI.chat.accept(data);
		ChatClient.worker = new Worker(null, null, null, null, null, null);
		ChatClient.parkInfo = new ParkInfo(null, null, null, null, null);
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
    

	public void updateNumberOfVisitor() {
		progressBar.setProgress(Integer.valueOf(ChatClient.parkInfo.getCurrentVisitors())/Integer.valueOf(ChatClient.worker.getPark().getMaxVisitors()));
		currentNumOfVisitors.setText(ChatClient.parkInfo.getCurrentVisitors());
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		departmentManagerName.setText("Hello " + ChatClient.worker.getWorkerName());
		currentMaxOfVisitors.setText(ChatClient.worker.getPark().getMaxVisitors());
		DataTransfer data = new DataTransfer(TypeOfMessage.REQUESTINFO,
				new ParkInfo(null , ChatClient.worker.getPark().getNumberOfPark(),null,null,null,ChatClient.worker.getRole()));
		ClientUI.chat.accept(data);
	}

}
