package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import client.ClientUI;
import client.logic.Subscriber;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * SubscriptionEntryController class. This controller is responsible for the
 * login screen of a user subscription. The controller expands the
 * AbstractScenes class that replaces the scenes within the main stage. The user
 * must enter his ID number or his subscription number in order to log in to his
 * user. It is possible to return to the main screen. There is an option to
 * click for help.
 * 
 * @author Daniella Amdur
 *
 */

public class SubscriptionEntryController extends AbstractScenes {
	String subNum;
	String id;
	Subscriber subscriber;
	public static SubscriptionEntryController instance;
	@FXML
	private Button btnEnter;

	@FXML
	private TextField subNumField;

	@FXML
	private Text msgText;

	@FXML
	private Button btnHelp;

	@FXML
	private Button btnBack;

	/**
	 * backToMain method. This method is responsible for changing the screen to the
	 * main screen of the family subscribers.
	 * 
	 * @param event
	 */

	@FXML
	void backToMain(ActionEvent event) {
		switchScenes("/client/boundaries/Travelers.fxml", "Traveler");
	}

	/**
	 * checkSubNumAndEnter method. This method is responsible for checking the
	 * validity of the ID number or the subscription number in the database by
	 * sending it to the server.
	 * 
	 * @param event
	 */

	@FXML
	void checkSubNumAndEnter(ActionEvent event) {
		subNum = subNumField.getText();

		if (subNum.trim().isEmpty())
			msgText.setText("You must enter a subscription number");

		else {
			id = subNum;
			subscriber = new Subscriber(id, null, null, null, null, null, null, subNum);
			DataTransfer data = new DataTransfer(TypeOfMessage.LOGIN_REQUEST, subscriber);
			ClientUI.chat.accept(data);
		}
	}

	/**
	 * helpPopOut method. This method is responsible for the PopOut window that
	 * displays the user help option.
	 * 
	 * @param event
	 * @throws IOException
	 */

	@FXML
	void helpPopOut(ActionEvent event) throws IOException {
		Stage helpWindow = new Stage();
		FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/client/boundaries/help.fxml"));
		Parent current = fxmlLoad.load();
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
	 * initialize method. This method is responsible for inserting the object into
	 * the instance so that we can use the object outside the class.
	 */

	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
	}

	/**
	 * subscriberNotFound method. This method is responsible for printing a message
	 * that the required subscription has not been found.
	 */

	public void subscriberNotFound() {
		msgText.setText("Not Found");
	}

	/**
	 * subscriberFound method. This method is responsible for changing the current
	 * screen to the family subscriber details screen.
	 */

	public void subscriberFound() {
		switchScenes("/client/boundaries/EditInfoFamily.fxml", "Family Subscription");

	}

}
