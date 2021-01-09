package client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

/**
 * SubscriptionEditController class. This controller is responsible for the
 * screen that displays the details of the family subscription in the park. The
 * controller expands the AbstractScenes class that replaces the scenes within
 * the main stage. It is possible to edit the details and save. It is possible
 * to log out of the family subscription account. It is possible to go to the
 * order history screen for the same subscription and create a new order.
 * 
 * @author Daniella Amdur
 */

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

	/**
	 * logout method. This method is responsible for disconnecting from the family
	 * subscription user and transferring to the main screen by creating a new
	 * subscription object and replacing the screen.
	 * 
	 * @param event
	 */

	@FXML
	void logout(ActionEvent event) {
		ChatClient.subscriber = new Subscriber();
		switchScenes("/client/boundaries/FamilySubEnter.fxml", "Family Subscription");
	}

	/**
	 * showEditInfo method. This method is responsible for switching the screen to
	 * the subscriber's edit details screen.
	 * 
	 * @param event
	 */

	@FXML
	void showEditInfo(ActionEvent event) {
		switchScenes("/client/boundaries/EditInfoFamily.fxml", "Family Subscription");
	}

	/**
	 * showHistroryOfVisit method. This method is responsible for switching the
	 * screen to the subscriber's history orders screen.
	 * 
	 * @param event
	 */

	@FXML
	void showHistroryOfVisit(ActionEvent event) {
		switchScenes("/client/boundaries/HistoryOfFamilyVisits.fxml", "Family Subscription");
	}

	/**
	 * showNewOrder method. This method is responsible for switching the screen to
	 * the screen where you can create a new order.
	 * 
	 * @param event
	 */

	@FXML
	void showNewOrder(ActionEvent event) {
		switchScenes("/client/boundaries/FamilyNewOrder.fxml", "Family Subscription");
	}

	/**
	 * updateFamilyInfo method. This method is responsible for saving the data after
	 * editing. The method receives the data from the fields, makes sure they are
	 * all present and valid. The method creates a new subscription object with all
	 * of the updated data, the data is sent to the EcoServer and then to the server
	 * and entered into the database by the subscription number.
	 * 
	 * @param event
	 */

	@FXML
	void updateFamilyInfo(ActionEvent event) {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String idNumber = idNumberField.getText();
		String telEdit = telEditField.getText();
		String numofmem = familyNumberField.getValue();
		String email = emailField.getText();
		String credit = credit4Digit1.getText() + "-" + credit4Digit2.getText() + "-" + credit4Digit3.getText() + "-"
				+ credit4Digit4.getText();
		if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || telEdit.trim().isEmpty()
				|| idNumber.trim().isEmpty() || email.trim().isEmpty() || numofmem.trim().isEmpty()
				|| credit.trim().isEmpty()) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("One of the fields is missing");
			alert.show();
		} else {
			boolean emailT = validate(email);
			if (emailT == false || idNumber.length() != 9 || (telEdit.length() != 9 && telEdit.length() != 10)
					|| credit4Digit1.getText().length() != 4 || credit4Digit2.getText().length() != 4
					|| credit4Digit3.getText().length() != 4 || credit4Digit4.getText().length() != 4) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("One or more of the fileds are incorrect");
				alert.show();
			} else {
				Subscriber subscriber = new Subscriber(idNumber, firstName, lastName, email, telEdit, numofmem, credit,
						ChatClient.subscriber.getSubscriberNumber());
				DataTransfer data = new DataTransfer(TypeOfMessage.UPDATEINFO, subscriber);
				ClientUI.chat.accept(data);
				familyName.setText("Hello " + lastName + " Family");
				ChatClient.subscriber.setLname(lastName);
			}
		}
	}

	/**
	 * initialize method. This method is responsible for defining variables by
	 * communicating with the server, is responsible for screen visibility and
	 * on-screen functionality (for example combobox options).
	 * 
	 * @param location
	 * @param resources
	 */

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

	/**
	 * Configuring the email address. Legal characters and desirable structure.
	 */

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * validate method. The method checks if the email entered is correct.
	 * 
	 * @param emailStr
	 * @return True or False
	 */

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

}