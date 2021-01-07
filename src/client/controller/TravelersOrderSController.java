package client.controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import client.ChatClient;
import client.ClientUI;
import client.logic.EmailDetails;
import client.logic.Order;
import client.logic.Visitor;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TravelersOrderSController extends AbstractScenes {

	public Order OrderSuccess = new Order(null, null, null, null, null, null, null, null);
	Double price = 30.00, pricePerPerson;
	String strPrice = null;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Text priceText;

	@FXML
	private TextField dateText;

	@FXML
	private TextField OrderNumber;

	@FXML
	private Text helloText;

	@FXML
	private Button returnToMenuTourBTN;

	@FXML
	private Button LogOutBtn;

	public static TravelersOrderSController instance;

	@FXML
	void LogOutButton(ActionEvent event) {
		switchScenes("/client/boundaries/main.fxml", "GoNature");
	}

	@FXML
	void returnToMenuTourButton(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		OrderSuccess = TravelerNewOrderController.instance.TravelerOrder;
		helloText.setText("Hello " + OrderSuccess.getNameOnOrder());
		OrderNumber.setText(OrderSuccess.getOrderNumber());
		dateText.setText(OrderSuccess.getDate());
		Double dblAmount = Double.valueOf(OrderSuccess.getNumOfVisitors());
		pricePerPerson = price * 0.85;
		price = pricePerPerson * dblAmount;
		priceText.setText(String.format("Price: %.2f", price));
		String toSend = "You Successfully create an order. " + OrderSuccess.getNameOnOrder()
				+ ".\nThe order details are:\nOrder Number: " + OrderSuccess.getOrderNumber() + "\nPark: "
				+ OrderSuccess.getParkName() + "\nDate: " + OrderSuccess.getDate() + "\nTime: "
				+ OrderSuccess.getHour() + "\nAmount of visitors: " + OrderSuccess.getNumOfVisitors();
		EmailDetails details = new EmailDetails(OrderSuccess.getEmail(), "GoNature New Order", toSend);
		DataTransfer maildata = new DataTransfer(TypeOfMessage.SENDMAIL, details);
		ClientUI.chat.accept(maildata);
	}
}
