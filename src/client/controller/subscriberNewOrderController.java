package client.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import client.ChatClient;
import client.ClientUI;
import client.logic.Order;
import client.logic.Subscriber;
import client.logic.WaitingList;
import client.logic.maxVis;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.application.Platform;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * subscriberNewOrderController class. This class is responsible for the new
 * order screen for a family subscription. The controller expands the
 * AbstractScenes class that replaces the scenes within the main stage. It is
 * possible to return to the screen where the details are edited. It is possible
 * to log out of the family subscription account. It is possible to go to the
 * order history screen for the same subscription. It is possible to enter the
 * waiting list if the selected date or time is busy.
 * 
 * @author Daniella Amdur
 */

public class subscriberNewOrderController extends AbstractScenes {

	DataTransfer data;

	@FXML
	private TextField enterEmail;

	@FXML
	private Button continueToPayBtn;

	@FXML
	private Button waitingListBtn;

	@FXML
	private ComboBox<String> numVisitorsBtn;

	@FXML
	private ComboBox<String> parkNameBtn;

	@FXML
	private ComboBox<String> chooseTime;

	@FXML
	private DatePicker chooseDayBtn;

	@FXML
	private Text errorEmail;

	@FXML
	private TextField firstName;

	@FXML
	private Text familyName;

	@FXML
	private Button btnLogout;

	@FXML
	private Text subNumber;

	@FXML
	private Button btnEditInfo;

	@FXML
	private Button btnHistoryOfVisit;

	@FXML
	private Button btnNewOrder;

	Double price = 30.00, pricePerPerson;

	public static subscriberNewOrderController instance;
	ObservableList<String> list, list2, list3;
	Order SubscriptionOrder = new Order(null, null, null, null, null, null, null, null);
	public String newTravelerID = null;
	static boolean thereIsSpot = false;

	/**
	 * continueToPayButton method. This method is responsible for the continue
	 * button to receive confirmation of the new order details. The method receives
	 * all the data entered, checks whether it is correct, performs the order cost
	 * calculations and sends all the data to the server.
	 * 
	 * @param event
	 */

	@FXML
	void continueToPayButton(ActionEvent event) {
		String orderPark = parkNameBtn.getSelectionModel().getSelectedItem().toString();
		LocalDate orderDate = chooseDayBtn.getValue();
		String ordeTime = chooseTime.getSelectionModel().getSelectedItem().toString();
		String orderNumOfVisitors = numVisitorsBtn.getSelectionModel().getSelectedItem().toString();
		String orderEmail = (enterEmail.getText());
		String orderName = (firstName.getText());

		if (java.time.LocalDate.now().isAfter(chooseDayBtn.getValue())) {
			errorEmail.setText("Invalid Date");
		}

		else if (!validate(orderEmail))
			errorEmail.setText("You must enter a valid Email");
		else {

			if (validate(orderEmail)) {
				String ordDate = orderDate.toString();
				SubscriptionOrder.setParkName(orderPark);
				SubscriptionOrder.setDate(ordDate);
				SubscriptionOrder.setHour(ordeTime);
				SubscriptionOrder.setNumOfVisitors(orderNumOfVisitors);
				SubscriptionOrder.setEmail(orderEmail);
				SubscriptionOrder.setNameOnOrder(orderName);
				SubscriptionOrder.setID(ChatClient.subscriber.getId());
				pricePerPerson = price * 0.85 * 0.80;
				price = pricePerPerson * Double.valueOf(orderNumOfVisitors);
				pricePerPerson = 0.0;
				SubscriptionOrder.setTotalPrice(String.format("%.2f", price));
				price = 30.0;
				SubscriptionOrder.setOrderKind("Subscriber");
				SubscriptionOrder.setPrePaid("No");
				SubscriptionOrder.setApproved("false");
				DataTransfer data2 = new DataTransfer(TypeOfMessage.SUBSCRIBER_NEWORDER, SubscriptionOrder);
				ClientUI.chat.accept(data2);
			}
		}
	}

	/**
	 * thereIsPlaceForVisitors method. This method is responsible for checking the
	 * correctness and availability of the order, if the order is correct, the
	 * method prints a message that the order has been successful. if the order is
	 * not valid, the method prints a message stating that the order has failed.
	 */

	public void thereIsPlaceForVisitors() {

//		priceText.setText(String.format("Price: %.2f", price));
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Stage helpWindow = new Stage();
				FXMLLoader fxmlLoad = new FXMLLoader(
						getClass().getResource("/client/boundaries/subNewOrderComplete.fxml"));
				Parent current = null;
				try {
					current = fxmlLoad.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
		});
		System.out.println("Order Updated Successfully");
	}

	/**
	 * thereIsNoPlaceForVisitors method. This method is responsible for checking the
	 * correctness and availability of the order,
	 */

	public void thereIsNoPlaceForVisitors() {
		waitingListBtn.setDisable(false);
		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				Alert alert = new Alert(AlertType.INFORMATION);

				alert.setHeaderText(null);
				alert.setContentText(
						"There is no place in our park :( \nPlease change date / hour or enter waiting list.");
				alert.show();
			}
		});
	}

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
	 * showEditInfo method. The method creates a new subscription object with the
	 * subscriber number, the data is sent to the EcoServer and then to the server
	 * and displays the data by the subscription number This method is responsible
	 * for switching the screen to the subscriber's edit details screen.
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
	 * waitingListButton method. This method is responsible for entering the waiting
	 * list, the method enters the order details in the list sent to the server.
	 * 
	 * @param event
	 */

	@FXML
	void waitingListButton(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("Entered waiting list sucssesfully");
		alert.show();
		WaitingList wait = new WaitingList(null, null, null, null, null, null, null, null, null, null,null,null);
		wait.setParkName(SubscriptionOrder.getParkName());
		wait.setTime(SubscriptionOrder.getHour());
		wait.setDate(SubscriptionOrder.getDate());
		wait.setNumOfVisitors(SubscriptionOrder.getNumOfVisitors());
		wait.setEmail(SubscriptionOrder.getEmail());
		wait.setID(SubscriptionOrder.getID());
		wait.setNameOnOrder(SubscriptionOrder.getNameOnOrder());
		wait.setOrderNumber(SubscriptionOrder.getOrderNumber());
		wait.setOrderKind("Subscriber");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		wait.setTimeOfEntrance(dtf.format(now));

		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime dateNow = LocalDateTime.now();
		wait.setDateOfEntrance(dtf1.format(dateNow));
		DataTransfer data = new DataTransfer(TypeOfMessage.NEW_ORDERWAITINGLIST, wait);
		ClientUI.chat.accept(data);
	}

	/**
	 * setTimeComboBox method. This method is responsible for inserting the visiting
	 * hours in the park into the combobox.
	 */

	private void setTimeComboBox() {
		ArrayList<String> enterVisitTime = new ArrayList<String>();
		enterVisitTime.add("8:00");
		enterVisitTime.add("9:00");
		enterVisitTime.add("10:00");
		enterVisitTime.add("11:00");
		enterVisitTime.add("12:00");
		enterVisitTime.add("13:00");
		enterVisitTime.add("14:00");
		enterVisitTime.add("15:00");

		list = FXCollections.observableArrayList(enterVisitTime);
		chooseTime.setItems(list);
	}

	/**
	 * setParkComboBox method. This method is responsible for entering the different
	 * park numbers into the combobox.
	 */

	private void setParkComboBox() {
		ArrayList<String> enterParkNumber = new ArrayList<String>();
		enterParkNumber.add("Park1");
		enterParkNumber.add("Park2");
		enterParkNumber.add("Park3");
		list2 = FXCollections.observableArrayList(enterParkNumber);
		parkNameBtn.setItems(list2);
	}

	/**
	 * setNumOfVisitorsComboBox method. This method is responsible for entering the
	 * number of visitors into the combobox.
	 */

	private void setNumOfVisitorsComboBox() {
		ArrayList<String> enterNumberOfVisitors = new ArrayList<String>();
		enterNumberOfVisitors.add("1");
		enterNumberOfVisitors.add("2");
		enterNumberOfVisitors.add("3");
		enterNumberOfVisitors.add("4");
		enterNumberOfVisitors.add("5");
		enterNumberOfVisitors.add("6");
		enterNumberOfVisitors.add("7");
		enterNumberOfVisitors.add("8");
		enterNumberOfVisitors.add("9");
		enterNumberOfVisitors.add("10");
		enterNumberOfVisitors.add("11");
		enterNumberOfVisitors.add("12");
		enterNumberOfVisitors.add("13");
		enterNumberOfVisitors.add("14");
		enterNumberOfVisitors.add("15");

		list3 = FXCollections.observableArrayList(enterNumberOfVisitors);

		numVisitorsBtn.setItems(list3);
		numVisitorsBtn.getSelectionModel().select(ChatClient.subscriber.getAmountOfFamilyMember());
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

	/**
	 * LOCAL_DATE method. This method formats the date format.
	 * 
	 * @param dateString
	 * @return localDate
	 */

	public static final LocalDate LOCAL_DATE(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}

	/**
	 * initialize method. This method is responsible for defining variables by
	 * communicating with the server, is responsible for screen visibility (caption
	 * and titles) and on-screen functionality.
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		subNumber.setText("Subscriber Number: " + ChatClient.subscriber.getSubscriberNumber());
		familyName.setText("Hello " + ChatClient.subscriber.getLname() + " Family");

		instance = this;
		waitingListBtn.setDisable(true);
		enterEmail.setText(ChatClient.subscriber.getEmail());
		firstName.setText(ChatClient.subscriber.getFname());
//		newTravelerID = RegularTravelerController.instance.ID;
		setTimeComboBox();
		setParkComboBox();
		setNumOfVisitorsComboBox();
	}
}