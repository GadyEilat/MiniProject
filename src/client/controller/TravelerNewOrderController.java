package client.controller;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import client.ChatClient;
import client.ClientUI;
import client.logic.EmailDetails;
import client.logic.Order;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import client.logic.Visitor;
import client.logic.WaitingList;
import client.logic.Worker;
import client.logic.maxVis;
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
 * TravelerNewOrderController class
 * @author Aviv Kamary
 * This controller is responsible for the Regular Traveler order screen to create a new order.
 * The controller expands the AbstractScenes class that replaces the scenes within the main stage. 
 * It is possible to return back to the kind of travelers screen.
 */
public class TravelerNewOrderController extends AbstractScenes {
	public Order TravelerOrder = new Order(null, null, null, null, null, null, null, null);
	public String newTravelerID = null;
	static boolean thereIsSpot = false;
	Double price = 30.00, pricePerPerson;
	String strPrice = null;
	
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

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
	private Button backBtn;

	@FXML
	private Text errorEmail;

	@FXML
	private TextField firstName;

	@FXML
	private Button LogOutBtn;

	public static TravelerNewOrderController instance;
	ObservableList<String> list, list2, list3;

	public void notFound() {
		errorEmail.setText("Error! cannot create Order");
	}

	public void isFound() {


		switchScenes("/client/boundaries/TravelerOrderSuccess.fxml", "Order Success");
		System.out.println("Order Updated Successfully");


	}


  

	  /**
	   * setTimeComboBox method
	   * This method adding times to the Time combo box in the screen and display it.
	   */
	private void setTimeComboBox() {
		ArrayList<String> al = new ArrayList<String>();
		al.add("8:00");
		al.add("9:00");
		al.add("10:00");
		al.add("11:00");
		al.add("12:00");
		al.add("13:00");
		al.add("14:00");
		al.add("15:00");

		list = FXCollections.observableArrayList(al);
		chooseTime.setItems(list);
	}

	/**
	 * setParkComboBox method
	 * This method adding the parks names to the Park combo box in the screen and display it.
	 */
	private void setParkComboBox() {
		ArrayList<String> a2 = new ArrayList<String>();
		a2.add("Park1");
		a2.add("Park2");
		a2.add("Park3");
		list2 = FXCollections.observableArrayList(a2);
		parkNameBtn.setItems(list2);
	}

	/**
	 * setNumOfVisitorsComboBox method
	 * This method adding the numbers of visitors that the regular traveler can choose and display it to the screen.
	 */
	private void setNumOfVisitorsComboBox() {
		ArrayList<String> a3 = new ArrayList<String>();
		a3.add("1");
		a3.add("2");
		a3.add("3");
		a3.add("4");
		a3.add("5");
		a3.add("6");
		a3.add("7");
		a3.add("8");
		a3.add("9");
		a3.add("10");
		a3.add("11");
		a3.add("12");
		a3.add("13");
		a3.add("14");
		a3.add("15");

		list3 = FXCollections.observableArrayList(a3);
		numVisitorsBtn.setItems(list3);
		numVisitorsBtn.getSelectionModel().selectFirst();
	}

	/**
	 * LogOutButton method
	 * @param event
	 * In this method you can log out to the kind of travelers screen.
	 */
	
	@FXML
	void LogOutButton(ActionEvent event) {
		switchScenes("/client/boundaries/Travelers.fxml", "");
	}

	/**
	 * backButton method
	 * @param event
	 * In this method you can return back to the Regular traveler screen to enter another ID.
	 */
	
	@FXML
	void backButton(ActionEvent event) {
		switchScenes("/client/boundaries/RegularTraveler.fxml", "");
	}


	/**
	 * continueToPayButton method
	 * @param event
	 * This method pull the details that the Traveler enter in the screen and saves it in the Order's Table.
	 * This method also checking if there is not a place in the parks and if not, he either can join the waiting list or change another date.
	 */


	@FXML
	void continueToPayButton(ActionEvent event) {
		String orderPark = parkNameBtn.getSelectionModel().getSelectedItem().toString();
		LocalDate orderDate = chooseDayBtn.getValue();
		String ordeTime = chooseTime.getSelectionModel().getSelectedItem().toString();
		String orderNumOfVisitors = numVisitorsBtn.getSelectionModel().getSelectedItem().toString();
		String orderEmail = (enterEmail.getText());
		String orderName = (firstName.getText());
		
		Double dblAmount = Double.valueOf(orderNumOfVisitors);
		pricePerPerson = price * 0.85;
		price = pricePerPerson * dblAmount;
		strPrice=String.valueOf(price);
		if (java.time.LocalDate.now().isAfter(chooseDayBtn.getValue())) {
			errorEmail.setText("Invalid Date");
		}

		else if (!validate(orderEmail))
			errorEmail.setText("You must enter a valid Email");
		else {

			if (validate(orderEmail)) {
				String ordDate = orderDate.toString();
				TravelerOrder.setParkName(orderPark);
				TravelerOrder.setDate(ordDate);
				TravelerOrder.setHour(ordeTime);
				TravelerOrder.setNumOfVisitors(orderNumOfVisitors);
				TravelerOrder.setEmail(orderEmail);
				TravelerOrder.setNameOnOrder(orderName);
				TravelerOrder.setID(newTravelerID);
				TravelerOrder.setTotalPrice(strPrice);
				TravelerOrder.setOrderKind("RegularOrder");
				checkDate(TravelerOrder, null);

				DataTransfer data = new DataTransfer(TypeOfMessage.NEW_ORDER, TravelerOrder);

				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (thereIsSpot) {
					ClientUI.chat.accept(data);

				



				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setContentText("There is no spot. Please change date or enter waiting list.");
					alert.show();
				}

			}
		}

	}



	/**
	 * waitingListButton method
	 * @param event
	 * This method handling the EnterWaitingList button and saves the Order's details.
	 * At the end of the actions, the screen return back to the kind of travelers screen.
	 */

	@FXML
	void waitingListButton(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("Entered waiting list sucssesfully.");
		alert.show();
		WaitingList wait = new WaitingList(null, null, null, null, null, null, null, null, null, null);
		wait.setDate(TravelerOrder.getDate());
		wait.setEmail(TravelerOrder.getEmail());
		wait.setID(TravelerOrder.getID());
		wait.setNameOnOrder(TravelerOrder.getNameOnOrder());
		wait.setNumOfVisitors(TravelerOrder.getNumOfVisitors());
		wait.setParkName(TravelerOrder.getParkName());
		wait.setTime(TravelerOrder.getHour());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		wait.setTimeOfEntrance(dtf.format(now));

		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime dateNow = LocalDateTime.now();
		wait.setDateOfEntrance(dtf1.format(dateNow));
		DataTransfer data = new DataTransfer(TypeOfMessage.NEW_ORDERWAITINGLIST, wait);
		ClientUI.chat.accept(data);
		switchScenes("/client/boundaries/Travelers.fxml", "New waiting list");
	}


	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	
	/**
	 * validate method
	 * @param emailStr
	 * This method checking if the email entered by the user is valid.
	 * @return true if it is a valid email, false if not.
	 */
	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	/**
	 * LOCAL_DATE method
	 * @param dateString
	 * This method making the date as the format we request.
	 * @return date as a LocalDate.
	 */
	public static final LocalDate LOCAL_DATE(String dateString) { // method for dealing with dates.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}

	/**
	 * initialize method
	 * @param location
	 * @param resources
	 * This method is responsible for defining variables by communicating with the server, 
	 * is responsible for screen visibility (caption and titles) and on-screen functionality.
	 * This method taking the RegularTraveler instance the user entered the screen before for saving it in the database.
	 * Also, this method setting all the combo boxes and display it in the screen for the user.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		newTravelerID = RegularTravelerController.instance.ID;
		setTimeComboBox();
		setParkComboBox();
		setNumOfVisitorsComboBox();
	}

	/**
	 * checkDate method
	 * @param s an entity of the order that been checked under this function
	 * @param t an entity that is been use for the waitinglist checks.
	 * @return an entity that holds few paramaters of the waiting list check.
	 */
	public maxVis checkDate(Order s, maxVis t) {
		maxVis visMax = new maxVis(null, null, null, 0, 0, null, 0);
		DataTransfer data2 = new DataTransfer(TypeOfMessage.CHECKMAXVIS, s);
		ClientUI.chat.accept(data2);
		return visMax;
	}

	/**
	 * checkDate2 method
	 * @param t an entity that is been use for the waitinglist checks.
	 */
	public void checkDate2(maxVis t) {

		maxVis visMax = new maxVis(null, null, null, 0, 0, null, 0);
		visMax.setDate(t.getDate());
		visMax.setPark(t.getPark());
		visMax.setVisitorsInOrder(t.getVisitorsInOrder());
		visMax.setAllowed1(t.getAllowed1());
		visMax.setAllowed2(t.getAllowed2());
		if (Integer.valueOf(visMax.getVisitorsInOrder() + visMax.getAllowed2()) < visMax.getAllowed1())
			thereIsSpot = true;
	}


}
