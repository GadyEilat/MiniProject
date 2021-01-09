package client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * subscriptionNumberPopoutController class. This class expands the
 * AbstractScenes class that replaces the scenes within the main stage. This
 * class is responsible for the PopOut messages that display the family
 * subscriber's number and sends the subscriber an email with registration
 * confirmation and his subscription number.
 * 
 * @author Liran Amilov
 */

public class subscriptionNumberPopoutController extends AbstractScenes {

	@FXML
	private Text msgForService;

	@FXML
	private Text subNum;

	@FXML
	private Text emailSent;

	@FXML
	private Button btnOK;

	/**
	 * closePopOut method. This method is responsible for the OK button that closes
	 * the PopOut message.
	 * 
	 * @param event
	 */

	@FXML
	void closePopOut(ActionEvent event) {
		Stage stage = (Stage) btnOK.getScene().getWindow();
		stage.close();
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
		if (ServiceRepresentativeController.instance.isFamily) {
			subNum.setText(ChatClient.subscriber.getSubscriberNumber());
			msgForService.setText("Subscriber Number");
		} else {
			msgForService.setText("Tour Guide");
			subNum.setText("Registered successfully");
		}

	}
}
