package client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.Subscriber;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SubscriptionEditController extends AbstractScenes {

	public static SubscriptionEditController instance;

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;

	@FXML
	private TextField telEditField;

	@FXML
	private TextField idNumberField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField credit4Digit1;

	@FXML
	private ComboBox<String> familyNumberField;

	@FXML
	private TextField credit4Digit3;

	@FXML
	private TextField credit4Digit2;

	@FXML
	private TextField credit4Digit4;

	@FXML
	private Text subNumber;

	@FXML
	private Button btnSave;

	@FXML
	private Text familyName;

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnEditInfo;

	@FXML
	private Button btnHistoryOfVisit;

	@FXML
	private Button btnNewOrder;

	@FXML
	void logout(ActionEvent event) {
		ChatClient.subscriber = new Subscriber();
		switchScenes("/client/boundaries/FamilySubEnter.fxml", "Family Subscription");

	}

	@FXML
	void showEditInfo(ActionEvent event) {
		switchScenes("/client/boundaries/EditInfoFamily.fxml", "Family Subscription");

	}

	@FXML
	void showHistroryOfVisit(ActionEvent event) {
		switchScenes("/client/boundaries/HistoryOfFamilyVisits.fxml", "Family Subscription");
	}

	@FXML
	void showNewOrder(ActionEvent event) {
//		switchScenes("/client/boundaries/EditInfoFamily.fxml", "Family Subscription");
	}

	@FXML
	void updateFamilyInfo(ActionEvent event) {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String telEdit = telEditField.getText();
		String idNumber = idNumberField.getText();
		String email = emailField.getText();
		String numofmem = familyNumberField.getValue();
		String credit = credit4Digit1.getText() + "-" + credit4Digit2.getText() + "-" + credit4Digit3.getText() + "-"
				+ credit4Digit4.getText();

		if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || telEdit.trim().isEmpty()
				|| idNumber.trim().isEmpty() || email.trim().isEmpty() || numofmem.trim().isEmpty()
				|| credit.trim().isEmpty()) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("One of the fields is missing");
			alert.show();
		}

		else {
			Subscriber subscriber = new Subscriber(idNumber, firstName, lastName, email, telEdit, numofmem, credit, ChatClient.subscriber.getSubscriberNumber());
			DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO, subscriber);
			ClientUI.chat.accept(data);
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		firstNameField.setText(ChatClient.subscriber.getFname());
		lastNameField.setText(ChatClient.subscriber.getLname());
		telEditField.setText(ChatClient.subscriber.getTeln());
		idNumberField.setText(ChatClient.subscriber.getId());
		emailField.setText(ChatClient.subscriber.getEmail());

		String CreditCard = ChatClient.subscriber.getCreditCard();

		String[] result = CreditCard.split("-");

		credit4Digit1.setText(result[0]);
		credit4Digit2.setText(result[1]);
		credit4Digit3.setText(result[2]);
		credit4Digit4.setText(result[3]);

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
		familyNumberField.setItems(numOfMembers);

		familyNumberField.getSelectionModel().select(ChatClient.subscriber.getAmountOfFamilyMember());

		familyName.setText("Hello " + ChatClient.subscriber.getLname() + " Family");
		subNumber.setText("Subscriber Number: " + ChatClient.subscriber.getSubscriberNumber());

	}

}