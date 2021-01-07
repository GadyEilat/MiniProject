package client.controller;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import client.ChatClient;
import client.ClientUI;
import client.logic.Order;
import client.logic.Subscriber;
import client.logic.maxVis;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

/**
 * 
 * @author Daniella Amdur
 *
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
    
    public static subscriberNewOrderController instance;
	ObservableList<String> list, list2, list3;
	Order SubscriptionOrder = new Order(null, null, null, null, null, null, null, null);
	public String newTravelerID = null;
	static boolean thereIsSpot = false;

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
				SubscriptionOrder.setID(newTravelerID);

				DataTransfer data2 = new DataTransfer(TypeOfMessage.SUBSCRIBER_NEWORDER, SubscriptionOrder);
				ClientUI.chat.accept(data2);


			}
		}
    }
    
    public void isFound() {
//		switchScenes("/client/boundaries/TravelerOrderSuccess.fxml", "Order Success");
		System.out.println("Order Updated Successfully");
	}
    
    public void answerFromDataBase() {
    	if (thereIsSpot) {
			ClientUI.chat.accept(data);

		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("There is no spot. Please change date or enter waiting list.");
			alert.show();
		}
    }

    /**
	 * logout method
	 * @param event 
	 * This method is responsible for disconnecting from the family subscription user
	 * and transferring to the main screen by creating a new subscription object and replacing the screen.
	 */
    
    @FXML
    void logout(ActionEvent event) {
    	ChatClient.subscriber = new Subscriber();
		switchScenes("/client/boundaries/FamilySubEnter.fxml", "Family Subscription");
    }

    /**
	 * showEditInfo method
	 * @param event
	 * The method creates a new subscription object with the subscriber number, the data is sent to the EcoServer 
	 * and then to the server and displays the data by the subscription number
	 * This method is responsible for switching the screen to the subscriber's edit details screen.
	 */
    
    @FXML
    void showEditInfo(ActionEvent event) {
    	switchScenes("/client/boundaries/EditInfoFamily.fxml", "Family Subscription");

    }

    /**
	 * showHistroryOfVisit method
	 * @param event  
	 * This method is responsible for switching the screen to the subscriber's history orders screen.
	 */
    
    @FXML
    void showHistroryOfVisit(ActionEvent event) {
		switchScenes("/client/boundaries/HistoryOfFamilyVisits.fxml", "Family Subscription");
    }

    @FXML
    void showNewOrder(ActionEvent event) {
    	switchScenes("/client/boundaries/FamilyNewOrder.fxml", "Family Subscription");
    }

    @FXML
    void waitingListButton(ActionEvent event) {
//    	Alert alert = new Alert(AlertType.INFORMATION);
//		alert.setHeaderText(null);
//		alert.setContentText("Entered waiting list sucssesfully.");
//		alert.show();
//		WaitingList wait = new WaitingList(null, null, null, null, null, null, null, null, null, null);
//		wait.setDate(TravelerOrder.getDate());
//		wait.setEmail(TravelerOrder.getEmail());
//		wait.setID(TravelerOrder.getID());
//		wait.setNameOnOrder(TravelerOrder.getNameOnOrder());
//		wait.setNumOfVisitors(TravelerOrder.getNumOfVisitors());
//		wait.setParkName(TravelerOrder.getParkName());
//		wait.setTime(TravelerOrder.getHour());
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		LocalDateTime now = LocalDateTime.now();
//		wait.setTimeOfEntrance(dtf.format(now));
//
//		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("HH:mm:ss");
//		LocalDateTime dateNow = LocalDateTime.now();
//		wait.setDateOfEntrance(dtf1.format(dateNow));
//		DataTransfer data = new DataTransfer(TypeOfMessage.NEW_ORDERWAITINGLIST, wait);
//		ClientUI.chat.accept(data);
//		switchScenes("/client/boundaries/Travelers.fxml", "New waiting list");
   }
    
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

	private void setParkComboBox() {
		ArrayList<String> a2 = new ArrayList<String>();
		a2.add("Park1");
		a2.add("Park2");
		a2.add("Park3");
		list2 = FXCollections.observableArrayList(a2);
		parkNameBtn.setItems(list2);
	}

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
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	public static final LocalDate LOCAL_DATE(String dateString) { // method for dealing with dates.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
//		newTravelerID = RegularTravelerController.instance.ID;
		setTimeComboBox();
		setParkComboBox();
		setNumOfVisitorsComboBox();
	}
	
//	public maxVis checkDate(Order s, maxVis t) {
//		maxVis visMax = new maxVis(null, null, null, 0, 0, null, 0);
//		DataTransfer data2 = new DataTransfer(TypeOfMessage.SUBSCRIBER_NEWORDER, s);
//		ClientUI.chat.accept(data2);
//		return visMax;
//	}

//	public void checkDate2(maxVis t) {
//		maxVis visMax = new maxVis(null, null, null, 0, 0, null, 0);
//		visMax.setDate(t.getDate());
//		visMax.setPark(t.getPark());
//		visMax.setVisitorsInOrder(t.getVisitorsInOrder());
//		visMax.setAllowed1(t.getAllowed1());
//		visMax.setAllowed2(t.getAllowed2());
//		if (Integer.valueOf(visMax.getVisitorsInOrder() + visMax.getAllowed2()) < visMax.getAllowed1())
//			thereIsSpot = true;
//		answerFromDataBase();
//	}

}