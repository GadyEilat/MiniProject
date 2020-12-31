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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ManagerManageParkController extends AbstractScenes {
	public static ManagerManageParkController instance;

    @FXML
    private Button btnChange;

    @FXML
    private TextField maxVisitorField;

    @FXML
    private Text maxVisitorCurrent;

    @FXML
    private TextField gapForVisitorsField;

    @FXML
    private Text gapForVisitorsCurrent;
    
    @FXML
    private TextField MaxTimeField;

    @FXML
    private Text MaxTimeCurrent;
    
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
	private Text msgText;
	
    @FXML
    private Button btnDiscount;

    @FXML
    void changeTheSetting(ActionEvent event) {
    	String MaxTime = MaxTimeField.getText();
    	String maxVisitor = maxVisitorField.getText();
    	String gapForVisitors = gapForVisitorsField.getText();
		if (maxVisitor.trim().isEmpty() && gapForVisitors.trim().isEmpty() && MaxTime.trim().isEmpty()) {
			msgText.setText("Empty Fields");
		} else {
			ParkInfo parkInfo = new ParkInfo(ChatClient.worker.getPark().getNumberOfPark(),maxVisitor , gapForVisitors, MaxTime,null);
			DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO_REQUEST, parkInfo);
			MaxTimeField.clear();
			maxVisitorField.clear();
			gapForVisitorsField.clear();
			Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setHeaderText(null);
        	alert.setContentText("Wait To Department Manager To Approve");
        	alert.show();
			ClientUI.chat.accept(data);
		}
    }

	@FXML
	void logout(ActionEvent event) {
//    	exitConnection
		ChatClient.worker = new Worker(null, null, null, null, null, null);
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
	
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		managerName.setText("Hello " + ChatClient.worker.getWorkerName());
		maxVisitorCurrent.setText(ChatClient.worker.getPark().getMaxVisitors());
		gapForVisitorsCurrent.setText(ChatClient.worker.getPark().getGapOfVisitors());

	}
	
	

}
