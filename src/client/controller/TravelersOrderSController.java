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

	/**
	 * TravelersOrderSController class
	 * @author Aviv Kamary
	 * This controller is responsible for displaying the details of a confirmation order and display it to the user.
	 * The controller expands the AbstractScenes class that replaces the scenes within the main stage.
	 * In this method you can return to the GoNature main screen after displaying the details to the user.
	 * This method also send a confirmation email to the user. 
	 */
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
	private Button LogOutBtn;

	public static TravelersOrderSController instance;

	/**
	 * LogOutButton method
	 * @param event
	 * In this method the user can return to the GoNature main screen.
	 */
	@FXML
	void LogOutButton(ActionEvent event) {
		switchScenes("/client/boundaries/main.fxml", "GoNature");
	}

	/**
	 * initialize method
	 * @param location
	 * @param resources
	 * This method is responsible for defining variables by communicating with the server, 
	 * is responsible for screen visibility (caption and titles) and on-screen functionality.
	 * This method uses the Traveler's Order details the user entered in the last screen and display what it needs to the screen.
	 * Also, this method sending an email of confirmation order to the user.
	 */
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
		
		String toSend = "You successfully created an order. " + OrderSuccess.getNameOnOrder()
				+ ".<br>The order details are:<br>Order Number: " + OrderSuccess.getOrderNumber() + "<br>Park: "
				+ OrderSuccess.getParkName() + "<br>Date: " + OrderSuccess.getDate() + "<br>Time: "
				+ OrderSuccess.getHour() + "<br>Amount of visitors: " + OrderSuccess.getNumOfVisitors();
		EmailDetails details = new EmailDetails(OrderSuccess.getEmail(), "GoNature New Order", toSend);
		DataTransfer maildata = new DataTransfer(TypeOfMessage.SENDMAIL, details);
		ClientUI.chat.accept(maildata);
	}
}
