package client.controller;

import client.ChatClient;
import client.ClientUI;
import client.logic.Subscriber;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class FamilySubscriptionHistoryController extends AbstractScenes{

	@FXML
	private TableView<?> tableOfHistory;

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
		Subscriber subscriber = new Subscriber(null, null, null, null, null, null, null, ChatClient.subscriber.getSubscriberNumber());
		DataTransfer data = new DataTransfer(TypeOfMessage.REQUESTINFO, subscriber);
		ClientUI.chat.accept(data);
		switchScenes("/client/boundaries/EditInfoFamily.fxml", "Family Subscription");

	}

	@FXML
	void showHistroryOfVisit(ActionEvent event) {
		switchScenes("/client/boundaries/HistoryOfFamilyVisits.fxml", "Family Subscription");

	}

	@FXML
	void showNewOrder(ActionEvent event) {
		//switchScenes("/client/boundaries/EditInfoFamily.fxml", "Family Subscription");

	}

	/////////////////// History Of family scene Functions

	@FXML
	void showTableOfHistory(ActionEvent event) {

	}


}
