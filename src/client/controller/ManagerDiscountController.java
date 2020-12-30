package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.logic.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ManagerDiscountController extends AbstractScenes {
	public static ManagerDiscountController instance;

    @FXML
    private TextField discountField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button btnSave;

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
	void logout(ActionEvent event) {
//    	exitConnection
		ChatClient.worker = new Worker(null, null, null, null, null, null);
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
	}

    @FXML
    void saveDiscountAndDate(ActionEvent event) {

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

	}

}
