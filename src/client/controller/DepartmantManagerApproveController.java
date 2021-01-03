package client.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.ParkInfo;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

public class DepartmantManagerApproveController extends AbstractScenes {
	public static DepartmantManagerApproveController instance;
	private String datesToApprveShow[][];
	private ParkInfo parkInfo;
	private ParkInfo parkInfoToApprove = new ParkInfo(null,null,null,null,null,null);
	ObservableList<String> discountItems =FXCollections.observableArrayList ();
	private int checkIfAllFieldClear = 0;
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
    private TextField maxVisitorsField;

    @FXML
    private Button btnCancelOfMaxVisitors;

    @FXML
    private Button btnApproveOfMaxVisitors;

    @FXML
    private Button btnApproveOfDiscount;

    @FXML
    private Button btnCancelOfDiscount;

    @FXML
    private ListView<String> showAllDiscountDate;

    @FXML
    private TextField maxHourField;

    @FXML
    private Button btnCancelOfMaxHours;

    @FXML
    private Button btnApproveOfMaxHours;

    @FXML
    private TextField GapForVisitorsField;

    @FXML
    private Button btnCancelGapForVisitors;

    @FXML
    private Button btnapproveGapForVisitors;


	@FXML
	void approveChangeOfDiscount(ActionEvent event) {
		
		
		disableButtonIfEmpty();
	}

	@FXML
	void approveChangeOfMaxVHour(ActionEvent event) {
    	parkInfoToApprove.setMaxHourToVisit(maxHourField.getText());
    	maxHourField.clear();
    	disableButtonIfEmpty();
    	if (checkIfAllFieldClear>3)
    		parkInfoToApprove.setChangeSettingToTrue(true);
    	DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO, parkInfoToApprove);
    	ClientUI.chat.accept(data);
    	parkInfoToApprove.setMaxHourToVisit(null);
    	parkInfoToApprove.setChangeSettingToTrue(false);
	}

	@FXML
	void approveChangeOfMaxVisitors(ActionEvent event) {
    	parkInfoToApprove.setMaxVisitors(maxVisitorsField.getText());
    	maxVisitorsField.clear();
    	disableButtonIfEmpty();
    	if (checkIfAllFieldClear>3)
    		parkInfoToApprove.setChangeSettingToTrue(true);
    	DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO, parkInfoToApprove);
    	ClientUI.chat.accept(data);
    	parkInfoToApprove.setMaxVisitors(null);
    	parkInfoToApprove.setChangeSettingToTrue(false);
	}
	
    @FXML
    void approveGapForVisitors(ActionEvent event) {
    	parkInfoToApprove.setGapOfVisitors(GapForVisitorsField.getText());
    	GapForVisitorsField.clear();
    	disableButtonIfEmpty();
    	if (checkIfAllFieldClear>3)
    		parkInfoToApprove.setChangeSettingToTrue(true);
    	DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO, parkInfoToApprove);
    	ClientUI.chat.accept(data);
    	parkInfoToApprove.setGapOfVisitors(null);
    	parkInfoToApprove.setChangeSettingToTrue(false);
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
    void CancelGapForVisitors(ActionEvent event) {

    }

//    private void checkIfAllFieldClear
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
	
	private void disableButtonIfEmpty() {
		if(maxHourField.getText().trim().isEmpty()) {
			btnCancelOfMaxHours.setDisable(true);
			btnApproveOfMaxHours.setDisable(true);
			checkIfAllFieldClear++;
		}
		if(maxVisitorsField.getText().trim().isEmpty()) {
			 btnCancelOfMaxVisitors.setDisable(true);
			 btnApproveOfMaxVisitors.setDisable(true);
			 checkIfAllFieldClear++;
		}
		if(GapForVisitorsField.getText().trim().isEmpty()) {
			btnapproveGapForVisitors.setDisable(true);
			btnCancelGapForVisitors.setDisable(true);
			checkIfAllFieldClear++;
		}
		if(showAllDiscountDate.getItems().isEmpty()) {
			btnApproveOfDiscount.setDisable(true);;
			btnCancelOfDiscount.setDisable(true);;
		}
	}
	
	public void loadData() {
		this.parkInfo=ChatClient.parkInfo;
		this.datesToApprveShow = ChatClient.datesToApprveShow;
		maxHourField.setText(parkInfo.getMaxHourToVisit());
		maxVisitorsField.setText(parkInfo.getMaxVisitors());
		GapForVisitorsField.setText(parkInfo.getGapOfVisitors());
		for (int i = 0; i < datesToApprveShow.length; i++) {
			discountItems.add(datesToApprveShow[i][0]+" "+datesToApprveShow[i][1]+"%");
		}
		showAllDiscountDate.setItems(discountItems);
		parkInfoToApprove.setNumberOfPark(parkInfo.getNumberOfPark());
		disableButtonIfEmpty();
	}
	public void notFond() {
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setHeaderText(null);
    	alert.setContentText("Can not take data from DataBase!");
    	alert.show();
	}

	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		departmentManagerName.setText("Hello " + ChatClient.worker.getWorkerName());
		DataTransfer data = new DataTransfer(TypeOfMessage.REQUESTINFO,
				new ParkInfo(null , ChatClient.worker.getPark().getNumberOfPark(),null,null,null,ChatClient.worker.getRole()));
		ClientUI.chat.accept(data);
		
	}

}
