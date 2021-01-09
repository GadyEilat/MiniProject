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

/**
 * ManagerManageParkController class. This class expands the AbstractScenes
 * class that replaces the scenes within the main stage. This class is
 * responsible for the management screen of the park manager. The administrator
 * has entered the max time, max visitors and the gap of visitors. It is
 * possible to log out from the park manager user, it is possible to move to
 * the status screen, the reports screen and the discounts screen. It is
 * possible to get help by clicking on the question mark.
 * 
 * @author Liran Amilov
 */

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
	boolean thereIsDataToSend = false;

	/**
	 * helpWindowPopOut method. This method is responsible for the PopOut window
	 * that shows the user a help message.
	 * 
	 * @param event
	 */

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

	/**
	 * changeTheSetting method. This method is responsible for checking the
	 * integrity of the fields entered. If one field is incorrect, a message will
	 * pop up telling you to access the help button. The data goes into the
	 * database.
	 * 
	 * @param event
	 */

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
		ParkInfo parkInfo = new ParkInfo(ChatClient.worker.getPark().getNumberOfPark(), null, null, null, null);
		if (maxVisitor.trim().isEmpty() && gapForVisitors.trim().isEmpty() && MaxTime.trim().isEmpty()) {
//			msgText.setText("Empty Fields");
			alertBox("Empty Fields");
		} else if (!MaxTime.matches("\\d*") || !maxVisitor.matches("\\d*") || !gapForVisitors.matches("\\d*")) {
			alertBox("Please enter only numbers");
		}else if (!gapForVisitors.trim().isEmpty()) {
			if(maxVisitor.trim().isEmpty()) {
				if(Integer.parseInt(gapForVisitors) < 10) {
					alertBox("See instruction in help button");
				}else {
					parkInfo.setGapOfVisitors(gapForVisitors);
					thereIsDataToSend = true;
				}
			}else if(Integer.parseInt(gapForVisitors) > Integer.parseInt(maxVisitor)) {
					alertBox("See instruction in help button");
				}
			else {
				parkInfo.setGapOfVisitors(gapForVisitors);
				parkInfo.setMaxVisitors(maxVisitor);
				thereIsDataToSend = true;
			}
			
		}else if(!MaxTime.trim().isEmpty()) {
			if (Integer.parseInt(MaxTime) > 8 || Integer.parseInt(MaxTime) < 1) {
				alertBox("See instruction in help button");
			}else {
				parkInfo.setMaxHourToVisit(MaxTime);
				thereIsDataToSend = true;
			}
		}else if(!maxVisitor.trim().isEmpty()) {
			if( Integer.parseInt(maxVisitor) < 50) {
				alertBox("See instruction in help button");
			}else {
				parkInfo.setMaxVisitors(maxVisitor);
				thereIsDataToSend = true;
			}
		}
		else{
			if (!maxVisitor.trim().isEmpty()) {
				parkInfo.setMaxVisitors(maxVisitor);
			}
			if (!gapForVisitors.trim().isEmpty()) {
				parkInfo.setGapOfVisitors(gapForVisitors);
			}
			if (!MaxTime.trim().isEmpty()) {
				parkInfo.setMaxHourToVisit(MaxTime);
			}
			thereIsDataToSend = true;
//			ParkInfo parkInfo = new ParkInfo(ChatClient.worker.getPark().getNumberOfPark(),maxVisitor , gapForVisitors, MaxTime,null);
		}
		if(thereIsDataToSend) {
			DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO_REQUEST, parkInfo);
			MaxTimeField.clear();
			maxVisitorField.clear();
			gapForVisitorsField.clear();
			alertBox("Wait To Department Manager To Approve");
			ClientUI.chat.accept(data);
		}
	}

	/**
	 * alertBox method. This method is responsible for displaying messages according
	 * to what the method receives.
	 * 
	 * @param Msg
	 */

	private void alertBox(String Msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(Msg);
		alert.show();
	}

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
		maxVisitorCurrent.setText(ChatClient.worker.getPark().getMaxVisitors());
		gapForVisitorsCurrent.setText(ChatClient.worker.getPark().getGapOfVisitors());
		MaxTimeCurrent.setText(ChatClient.worker.getPark().getMaxHourToVisit());

	}

}
