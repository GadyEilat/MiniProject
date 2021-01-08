package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.ParkInfo;
import client.logic.Subscriber;
import client.logic.TourGuide;
import client.logic.Worker;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ServiceRepresentativeController extends AbstractScenes {

	public static ServiceRepresentativeController instance;

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;

	@FXML
	private TextField telField;

	@FXML
	private TextField idNumberField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField credit4Digit1;

	@FXML
	private ComboBox<String> choiceNumberOfMembers;

	@FXML
	private TextField credit4Digit3;

	@FXML
	private TextField credit4Digit2;

	@FXML
	private TextField credit4Digit4;

	@FXML
	private Button btnSave;

	@FXML
	private RadioButton btnRadioGroup;

	@FXML
	private ToggleGroup RadioGroup;

	@FXML
	private RadioButton btnRadioFamily;

	@FXML
	private Text serviceName;

	@FXML
	private Button btnLogout;
	boolean isFamily = true;

	@FXML
	void logout(ActionEvent event) {
		// exit Logout
		DataTransfer data = new DataTransfer(TypeOfMessage.LOGOUT, ChatClient.worker);
		ClientUI.chat.accept(data);
		ChatClient.worker = new Worker(null, null, null, null, null, null);
		ChatClient.subscriber = new Subscriber();
		ChatClient.connected = false;
		switchScenes("/client/boundaries/workerLogin.fxml", "Worker Login");
	}

	@FXML
	void saveSubAndGetSubNumber(ActionEvent event) {
		// Saving To the correct DB and generate SUB number

		String firstName = firstNameField.getText();
		String lasttName = lastNameField.getText();
		String id = idNumberField.getText();
		String telNumber = telField.getText();
		String email = emailField.getText();

		if (firstName.trim().isEmpty() || lasttName.trim().isEmpty() || id.trim().isEmpty()
				|| telNumber.trim().isEmpty() || email.trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Please enter all the field");
			alert.show();
		}

		if (isFamily) {// RadioGroup.getSelectedToggle().equals(btnRadioFamily)
			String credit = credit4Digit1.getText() + "-" + credit4Digit2.getText() + "-" + credit4Digit3.getText() + "-"
					+ credit4Digit4.getText();
			if(!credit.trim().isEmpty()) {
			String numOfMembers = choiceNumberOfMembers.getValue();
			Subscriber newSubscriber = new Subscriber(id, firstName, lasttName, email, telNumber, numOfMembers, credit,
					null);
			DataTransfer data = new DataTransfer(TypeOfMessage.INSERTINFO, newSubscriber);
			ClientUI.chat.accept(data);
			choiceNumberOfMembers.getSelectionModel().clearSelection();
			}else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("Please enter all the field");
				alert.show();
			}
		} else if (!isFamily) {// RadioGroup.getSelectedToggle().equals(btnRadioGroup)
			TourGuide newTourGuide = new TourGuide(id,firstName,lasttName,email,telNumber); // add credit card !!!!!!!!!!!!!!!!!
			DataTransfer data = new DataTransfer(TypeOfMessage.INSERTINFO, newTourGuide);
			ClientUI.chat.accept(data);
		}
		firstNameField.clear();
		lastNameField.clear();
		idNumberField.clear();
		telField.clear();
		emailField.clear();
		credit4Digit1.clear();
		credit4Digit2.clear();
		credit4Digit3.clear();
		credit4Digit4.clear();
	}


	public void showNumOfSubPopOut() throws IOException {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Stage helpWindow = new Stage();
				FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/client/boundaries/subNumPopup.fxml"));
				Parent current = null;
				try {
					current = fxmlLoad.load();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				helpWindow.initModality(Modality.APPLICATION_MODAL);
				helpWindow.setTitle("Subscription Number");
				Scene scene = new Scene(current);
				helpWindow.setMinHeight(400);
				helpWindow.setMinWidth(600);
				helpWindow.setMaxHeight(400);
				helpWindow.setMaxWidth(600);
				helpWindow.setScene(scene);
				helpWindow.showAndWait();
			}
		});
	}

	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		serviceName.setText("Hello " + ChatClient.worker.getWorkerName());
		btnRadioFamily.setSelected(true);
		RadioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ob, Toggle old, Toggle newT) {

				RadioButton selected = (RadioButton) RadioGroup.getSelectedToggle();

				if (selected != null)
					if (selected.getText().equals("Group")) {
						choiceNumberOfMembers.setDisable(true);
						credit4Digit1.setDisable(true);
						credit4Digit2.setDisable(true);
						credit4Digit3.setDisable(true);
						credit4Digit4.setDisable(true);
						isFamily = false;
					} else {
						choiceNumberOfMembers.setDisable(false);
						credit4Digit1.setDisable(false);
						credit4Digit2.setDisable(false);
						credit4Digit3.setDisable(false);
						credit4Digit4.setDisable(false);
						isFamily = true;
					}
			}
		});

		ObservableList<String> numOfMembers;
		ArrayList<String> Members = new ArrayList<String>();
		Members.add("1");
		Members.add("2");
		Members.add("3");
		Members.add("4");
		Members.add("5");
		Members.add("6");
		Members.add("7");
		Members.add("8");
		Members.add("9");
		Members.add("10");
		Members.add("11");
		Members.add("12");
		Members.add("13");
		Members.add("14");
		Members.add("15");
		numOfMembers = FXCollections.observableArrayList(Members);
		choiceNumberOfMembers.setItems(numOfMembers);

//		choiceNumberOfMembers.getSelectionModel().select(0);

	}
}
