package client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.EmailDetails;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

public class SubscriptionOrderDoneController implements Initializable {
	@FXML
	private Button btnOK;

	@FXML
	private TextField dateText;

	@FXML
	private TextField OrderNumber;

	@FXML
	private Text priceText;

	@FXML
	void closePopOut(ActionEvent event) {
		Stage stage = (Stage) btnOK.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		priceText.setText(ChatClient.order.getTotalPrice());
		OrderNumber.setText(ChatClient.order.getOrderNumber());
		dateText.setText(ChatClient.order.getDate());
		String toSend = "You successfully created an order. " + ChatClient.order.getNameOnOrder()
				+ ".<br>The order details are:<br>Order Number: " + ChatClient.order.getOrderNumber() + "<br>Park: "
				+ ChatClient.order.getParkName() + "<br>Date: " + ChatClient.order.getDate() + "<br>Time: "
				+ ChatClient.order.getHour() + "<br>Amount of visitors: " + ChatClient.order.getNumOfVisitors() + "<br>Price: "+ ChatClient.order.getTotalPrice();
		EmailDetails newOrder = new EmailDetails(ChatClient.order.getEmail(), "GoNature New Order", toSend);
		DataTransfer maildata = new DataTransfer(TypeOfMessage.SENDMAIL, newOrder);
		ClientUI.chat.accept(maildata);

	}

}
