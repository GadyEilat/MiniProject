package client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class DepartmantManagerApproveController extends AbstractScenes {
	public static DepartmantManagerApproveController instance;

	@FXML
	private TextField maxVisitorsField;
	
	@FXML
	private Text departmentManagerName;
	
	@FXML
	private Button btnCancelOfMaxVisitors;

	@FXML
	private Button btnApproveOfMaxVisitors;

	@FXML
	private Button btnApproveOfDiscount;

	@FXML
	private Button btnCancelOfDiscount;

	@FXML
	private TextField maxHourField;

	@FXML
	private Button btnCancelOfMaxHours;

	@FXML
	private Button btnApproveOfMaxHours;

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnStatus;

	@FXML
	private Button btnReports;

	@FXML
	private Button btnApproval;

	@FXML
	void approveChangeOfDiscount(ActionEvent event) {

	}

	@FXML
	void approveChangeOfMaxVHour(ActionEvent event) {

	}

	@FXML
	void approveChangeOfMaxVisitors(ActionEvent event) {

	}

	@FXML
	void cancelChangesOfDiscount(ActionEvent event) {

	}

	@FXML
	void cancelTheChangesOfMaxHour(ActionEvent event) {

	}

	@FXML
	void cancelTheChangesOfMaxVisitors(ActionEvent event) {

	}

	@FXML
	void logout(ActionEvent event) {
		// exit Logout
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

	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		departmentManagerName.setText("Hello " + ChatClient.worker.getWorkerName());
	}

}
