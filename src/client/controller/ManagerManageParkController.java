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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private Button helpButton;

	@FXML
	private Button btnDiscount;

    @FXML
    void helpWindowPopOut(ActionEvent event) {
		Stage helpWindow = new Stage();
		FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/client/boundaries/HelpForManagingPark.fxml"));
		Parent current = null;
		try {
			current = fxmlLoad.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		helpWindow.initModality(Modality.APPLICATION_MODAL);
		helpWindow.setTitle("Help");
		Scene scene = new Scene(current);
		helpWindow.setMinHeight(400);
		helpWindow.setMinWidth(600);
		helpWindow.setMaxHeight(400);
		helpWindow.setMaxWidth(600);
		helpWindow.setScene(scene);
		helpWindow.showAndWait();
    }
    
	@FXML
	void changeTheSetting(ActionEvent event) {
//    	MaxTimeField.textProperty().addListener((observable, oldValue, newValue) -> {
//	        if (!newValue.matches("\\d*")) {
//	        	MaxTimeField.setText(newValue.replaceAll("[^\\d]", ""));
//	        }
//	    });
		String MaxTime = MaxTimeField.getText();
		String maxVisitor = maxVisitorField.getText();
		String gapForVisitors = gapForVisitorsField.getText();
		if (maxVisitor.trim().isEmpty() && gapForVisitors.trim().isEmpty() && MaxTime.trim().isEmpty()) {
//			msgText.setText("Empty Fields");
			alertBox("Empty Fields");
		} else if (!MaxTime.matches("\\d*") || !maxVisitor.matches("\\d*") || !gapForVisitors.matches("\\d*")) {
			alertBox("Please enter only numbers");
		}else if (!gapForVisitors.trim().isEmpty()) {
			if(maxVisitor.trim().isEmpty()) {
				if(Integer.parseInt(gapForVisitors) < 10) {
					alertBox("See instruction in help button");
				}
			}else if(Integer.parseInt(gapForVisitors) > Integer.parseInt(maxVisitor)) {
					alertBox("See instruction in help button");
				}
		}else if(!MaxTime.trim().isEmpty()) {
			if (Integer.parseInt(MaxTime) > 8 || Integer.parseInt(MaxTime) > 1) {
				alertBox("See instruction in help button");
			}
		}else if(!maxVisitor.trim().isEmpty()) {
			if( Integer.parseInt(maxVisitor) < 50) {
				alertBox("See instruction in help button");
			}
		}
		else {
			ParkInfo parkInfo = new ParkInfo(ChatClient.worker.getPark().getNumberOfPark(), null, null, null, null);
			if (!maxVisitor.trim().isEmpty()) {
				parkInfo.setMaxVisitors(maxVisitor);
			}
			if (!gapForVisitors.trim().isEmpty()) {
				parkInfo.setGapOfVisitors(gapForVisitors);
			}
			if (!MaxTime.trim().isEmpty()) {
				parkInfo.setMaxHourToVisit(MaxTime);
			}
//			ParkInfo parkInfo = new ParkInfo(ChatClient.worker.getPark().getNumberOfPark(),maxVisitor , gapForVisitors, MaxTime,null);
			DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO_REQUEST, parkInfo);
			MaxTimeField.clear();
			maxVisitorField.clear();
			gapForVisitorsField.clear();
			alertBox("Wait To Department Manager To Approve");
			ClientUI.chat.accept(data);
		}
	}

	private void alertBox(String Msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(Msg);
		alert.show();
	}
	
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

	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		managerName.setText("Hello " + ChatClient.worker.getWorkerName());
		maxVisitorCurrent.setText(ChatClient.worker.getPark().getMaxVisitors());
		gapForVisitorsCurrent.setText(ChatClient.worker.getPark().getGapOfVisitors());
		MaxTimeCurrent.setText(ChatClient.worker.getPark().getMaxHourToVisit());

	}

}
