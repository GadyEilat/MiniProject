package client.controller;

import com.gluonhq.charm.glisten.control.ProgressBar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ManagerController extends AbstractScenes {

	///////////////////////// Report Scene

	@FXML
	private Button totalNumOfVisitors;

	@FXML
	private Button usageRepotr;

	@FXML
	private Button revenueReportPerMonth;

	//////////////////////// Status Scene

	@FXML
	private Text currentVisitors;

	@FXML
	private Text maxVisitors;

	@FXML
	private Text numOfSubscribers;

	@FXML
	private ProgressBar progressBar;

	////////////////////// Managing Park Scene

	@FXML
	private Button btnChange;

	@FXML
	private TextField maxVisitorField;

	@FXML
	private Text maxVisitorCurrent;

	@FXML
	private TextField gapForVisitors;

	@FXML
	private Text gapForVisitorsCurrent;

	////////////////////// Discount Scene

	@FXML
	private Button btnSave;

	@FXML
	private TextField discountField;

	@FXML
	private DatePicker datePicker;

	/////////////////////// For All Scenes

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
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
	}

	@FXML
	void showDiscount(ActionEvent event) {
		switchScenes("/client/boundaries/DiscountManager.fxml", "Manager");
	}

	@FXML
	void showManagingPark(ActionEvent event) {
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

	///////////////////// Status Scene Function

	public void updateNumberOfVisitorAndSub() {

	}

	//////////////////// Discount Scene Function

	@FXML
	void saveDiscountAndDate(ActionEvent event) {

	}

	///////////////////////// Report Scene Functions

	@FXML
	void makeReportNumOfVisitors(ActionEvent event) {

	}

	@FXML
	void makeReportRevenue(ActionEvent event) {

	}

	@FXML
	void makeReportUsage(ActionEvent event) {

	}

	///////////////////////// Managing Park Scene Function
	@FXML
	void changeTheSetting(ActionEvent event) {

	}

}
