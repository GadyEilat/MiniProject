package client.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.ParkInfo;
import client.logic.Worker;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

/**
 * DepartmantManagerApproveController class.This class is responsible for the
 * approval screen of the park department manager. The controller expands the
 * AbstractScenes class that replaces the scenes within the main stage. It is
 * possible to confirm the allowable visitor gap, the time limit per visitor,
 * the maximum number of visitors in the park and the discounts. All those
 * requests are sent from the park manager. It is possible to log out of the
 * department manager user, it is possible to go to the reports screen and the
 * status screen where the number of visitors is shown.
 * 
 * @author Liram Amilov
 */

public class DepartmantManagerApproveController extends AbstractScenes {
	public static DepartmantManagerApproveController instance;
	private String datesToApprveShow[][];
	private ParkInfo parkInfo;
	private ParkInfo parkInfoToApprove = new ParkInfo(null, null, null, null, null, null);
	ObservableList<String> discountItems = FXCollections.observableArrayList();
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

	/**
	 * approveChangeOfDiscount method. This method is responsible for approving a
	 * change in the discount requested by the park manager. All requests for
	 * discounts are listed in the table. The department manager must select the
	 * desired discount and approve or cancel it.The updated data is sent to the
	 * server.
	 * 
	 * @param event
	 */

	@FXML
	void approveChangeOfDiscount(ActionEvent event) {
		int pickDate;
		ObservableList<Integer> selectedIndices = showAllDiscountDate.getSelectionModel().getSelectedIndices();
		ObservableList<String> selected = showAllDiscountDate.getSelectionModel().getSelectedItems();
		String[][] sendApproveDate = new String[selectedIndices.size()][1];
		if (selectedIndices.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Please select date-discount to approve");
			alert.show();
		} else {
			for (int i = 0; i < selectedIndices.size(); i++) {
				pickDate = selectedIndices.get(i);
				sendApproveDate[i][0] = datesToApprveShow[pickDate][0];

			}
			parkInfoToApprove.setDiscountDates(sendApproveDate);
			showAllDiscountDate.getItems().removeAll(selected);
			DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO, parkInfoToApprove);
			ClientUI.chat.accept(data);
			parkInfoToApprove.setDiscountDates(null);
		}

		disableButtonIfEmpty();
		checkIfAllFieldClear = 0;
	}

	/**
	 * approveChangeOfMaxVHour method. This method is responsible for approving the
	 * maximum time per visitor in the park. The updated data is sent to the server.
	 * 
	 * @param event
	 */

	@FXML
	void approveChangeOfMaxVHour(ActionEvent event) {
		parkInfoToApprove.setMaxHourToVisit(maxHourField.getText());
		maxHourField.clear();
		disableButtonIfEmpty();
		if (checkIfAllFieldClear == 3)
			parkInfoToApprove.setChangeSettingToTrue(true);
		DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO, parkInfoToApprove);
		ClientUI.chat.accept(data);
		parkInfoToApprove.setMaxHourToVisit(null);
		parkInfoToApprove.setChangeSettingToTrue(false);
		checkIfAllFieldClear = 0;
	}

	/**
	 * approveChangeOfMaxVisitors method. This method is responsible for confirming
	 * the limited number of visitors. The updated data is sent to the server.
	 * 
	 * @param event
	 */

	@FXML
	void approveChangeOfMaxVisitors(ActionEvent event) {
		parkInfoToApprove.setMaxVisitors(maxVisitorsField.getText());
		maxVisitorsField.clear();
		disableButtonIfEmpty();
		if (checkIfAllFieldClear == 3)
			parkInfoToApprove.setChangeSettingToTrue(true);
		DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO, parkInfoToApprove);
		ClientUI.chat.accept(data);
		parkInfoToApprove.setMaxVisitors(null);
		parkInfoToApprove.setChangeSettingToTrue(false);
		checkIfAllFieldClear = 0;
	}

	/**
	 * approveGapForVisitors method. This method is responsible for confirming the
	 * allowable gap of visitors. The updated data is sent to the server.
	 * 
	 * @param event
	 */

	@FXML
	void approveGapForVisitors(ActionEvent event) {
		parkInfoToApprove.setGapOfVisitors(GapForVisitorsField.getText());
		GapForVisitorsField.clear();
		disableButtonIfEmpty();
		if (checkIfAllFieldClear == 3)
			parkInfoToApprove.setChangeSettingToTrue(true);
		DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO, parkInfoToApprove);
		ClientUI.chat.accept(data);
		parkInfoToApprove.setGapOfVisitors(null);
		parkInfoToApprove.setChangeSettingToTrue(false);
		checkIfAllFieldClear = 0;
	}

	/**
	 * cancelChangesOfDiscount method. This method is responsible for canceling the
	 * discount request. The updated data is sent to the server.
	 * 
	 * @param event
	 */

	@FXML
	void cancelChangesOfDiscount(ActionEvent event) {
		int pickDate;
		ObservableList<Integer> selectedIndices = showAllDiscountDate.getSelectionModel().getSelectedIndices();
		ObservableList<String> selected = showAllDiscountDate.getSelectionModel().getSelectedItems();
		String[][] sendApproveDate = new String[selectedIndices.size()][1];
		if (selectedIndices.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Please select date-discount to cancel");
			alert.show();
		} else {
			for (int i = 0; i < selectedIndices.size(); i++) {
				pickDate = selectedIndices.get(i);
				sendApproveDate[i][0] = datesToApprveShow[pickDate][0];
			}
			parkInfoToApprove.setDiscountDates(sendApproveDate);
			showAllDiscountDate.getItems().removeAll(selected);
			DataTransfer data = new DataTransfer(TypeOfMessage.DELETEINFO, parkInfoToApprove);
			ClientUI.chat.accept(data);
			parkInfoToApprove.setDiscountDates(null);
		}
		disableButtonIfEmpty();
		checkIfAllFieldClear = 0;
	}

	/**
	 * cancelTheChangesOfMaxHour method. This method is responsible for canceling
	 * the maximum time per visitor in the park. The updated data is sent to the
	 * server.
	 * 
	 * @param event
	 */

	@FXML
	void cancelTheChangesOfMaxHour(ActionEvent event) {
		parkInfoToApprove.setMaxHourToVisit("toDelete");
		maxHourField.clear();
		disableButtonIfEmpty();
		if (checkIfAllFieldClear == 3)
			parkInfoToApprove.setChangeSettingToTrue(true);
		DataTransfer data = new DataTransfer(TypeOfMessage.DELETEINFO, parkInfoToApprove);
		ClientUI.chat.accept(data);
		parkInfoToApprove.setMaxHourToVisit(null);
		parkInfoToApprove.setChangeSettingToTrue(false);
		checkIfAllFieldClear = 0;
	}

	/**
	 * cancelTheChangesOfMaxVisitors method. This method is responsible for
	 * canceling the maximum visitor request in the park. The updated data is sent
	 * to the server.
	 * 
	 * @param event
	 */

	@FXML
	void cancelTheChangesOfMaxVisitors(ActionEvent event) {
		parkInfoToApprove.setMaxVisitors("toDelete");
		maxVisitorsField.clear();
		disableButtonIfEmpty();
		if (checkIfAllFieldClear == 3)
			parkInfoToApprove.setChangeSettingToTrue(true);
		DataTransfer data = new DataTransfer(TypeOfMessage.DELETEINFO, parkInfoToApprove);
		ClientUI.chat.accept(data);
		parkInfoToApprove.setMaxVisitors(null);
		parkInfoToApprove.setChangeSettingToTrue(false);
		checkIfAllFieldClear = 0;
	}

	/**
	 * CancelGapForVisitors method. This method is responsible for canceling the
	 * request for the allowable gap of visitors. The updated data is sent to the
	 * server.
	 * 
	 * @param event
	 */

	@FXML
	void CancelGapForVisitors(ActionEvent event) {
		parkInfoToApprove.setGapOfVisitors("toDelete");
		GapForVisitorsField.clear();
		disableButtonIfEmpty();
		if (checkIfAllFieldClear == 3)
			parkInfoToApprove.setChangeSettingToTrue(true);
		DataTransfer data = new DataTransfer(TypeOfMessage.DELETEINFO, parkInfoToApprove);
		ClientUI.chat.accept(data);
		parkInfoToApprove.setGapOfVisitors(null);
		parkInfoToApprove.setChangeSettingToTrue(false);
		checkIfAllFieldClear = 0;
	}

	/**
	 * logout method. This method is responsible for disconnecting from the
	 * department manager user and transferring to the main login screen.
	 * 
	 * @param event
	 */

//    private void checkIfAllFieldClear
	@FXML
	void logout(ActionEvent event) {
		// exit Logout
		DataTransfer data = new DataTransfer(TypeOfMessage.LOGOUT, ChatClient.worker);
		ClientUI.chat.accept(data);
		ChatClient.parkInfo = new ParkInfo(null, null, null, null, null);
		ChatClient.worker = new Worker(null, null, null, null, null, null);
		ChatClient.connected = false;
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
	}

	/**
	 * showApproval method. This method is responsible for transferring the screen
	 * to the certificate screen of the department manager
	 * 
	 * @param event
	 */

	@FXML
	void showApproval(ActionEvent event) {
		switchScenes("/client/boundaries/approveManagerChanges.fxml", "Departmant Manager");
	}

	/**
	 * showReports method. This method is responsible for transferring a screen to
	 * the reports screen of the department manager.
	 * 
	 * @param event
	 */

	@FXML
	void showReports(ActionEvent event) {
		switchScenes("/client/boundaries/reportsDM.fxml", "Departmant Manager");
	}

	/**
	 * showStatus method. This method is responsible for moving a screen to the
	 * status screen of the department manager where the number of visitors to the
	 * park is shown.
	 * 
	 * @param event
	 */

	@FXML
	void showStatus(ActionEvent event) {
		switchScenes("/client/boundaries/mainDepartmantManager.fxml", "Departmant Manager");
	}

	/**
	 * disableButtonIfEmpty method. This method is responsible for not allowing a
	 * button to be pressed if that field is empty.
	 */

	private void disableButtonIfEmpty() {
		if (maxHourField.getText().trim().isEmpty()) {
			btnCancelOfMaxHours.setDisable(true);
			btnApproveOfMaxHours.setDisable(true);
			checkIfAllFieldClear++;
		}
		if (maxVisitorsField.getText().trim().isEmpty()) {
			btnCancelOfMaxVisitors.setDisable(true);
			btnApproveOfMaxVisitors.setDisable(true);
			checkIfAllFieldClear++;
		}
		if (GapForVisitorsField.getText().trim().isEmpty()) {
			btnapproveGapForVisitors.setDisable(true);
			btnCancelGapForVisitors.setDisable(true);
			checkIfAllFieldClear++;
		}
		if (showAllDiscountDate.getItems().isEmpty()) {
			btnApproveOfDiscount.setDisable(true);
			btnCancelOfDiscount.setDisable(true);
//			checkIfAllFieldClear++;
		}
	}

	/**
	 * loadData method. This method pulls the data from the database and displays it
	 * after the approval of the department manager.
	 */

	public void loadData() {
		this.parkInfo = ChatClient.parkInfo;
		this.datesToApprveShow = ChatClient.datesToApprveShow;
		if (parkInfo.getMaxHourToVisit() != null) {
			maxHourField.setText(parkInfo.getMaxHourToVisit());
		}
		if (parkInfo.getMaxVisitors() != null) {
			maxVisitorsField.setText(parkInfo.getMaxVisitors());
		}
		if (parkInfo.getGapOfVisitors() != null) {
			GapForVisitorsField.setText(parkInfo.getGapOfVisitors());
		}
		if (datesToApprveShow != null) {
			for (int i = 0; i < datesToApprveShow.length; i++) {
				discountItems.add(datesToApprveShow[i][0] + " " + datesToApprveShow[i][1] + "%");
			}
			showAllDiscountDate.setItems(discountItems);
			showAllDiscountDate.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		}
		parkInfoToApprove.setNumberOfPark(parkInfo.getNumberOfPark());
		disableButtonIfEmpty();
	}

	/**
	 * notFond method. This method displays an error message stating that the data
	 * could not be taken from the database because it was not found.
	 */

	public void notFond() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("Can not take data from DataBase!");
		alert.show();
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
		checkIfAllFieldClear = 0;
		departmentManagerName.setText("Hello " + ChatClient.worker.getWorkerName());
		DataTransfer data = new DataTransfer(TypeOfMessage.REQUESTINFO, new ParkInfo(null,
				ChatClient.worker.getPark().getNumberOfPark(), null, null, null, ChatClient.worker.getRole()));
		ClientUI.chat.accept(data);
	}

}
