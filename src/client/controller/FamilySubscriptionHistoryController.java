package client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import client.ChatClient;
import client.ClientUI;
import client.logic.Order;
import client.logic.Subscriber;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FamilySubscriptionHistoryController class. This controller is responsible for
 * the screen displaying the family subscriber's visit history. The controller
 * expands the AbstractScenes class that replaces the scenes within the main
 * stage. The screen shows all the orders that the subscriber has ordered (order
 * number, date, time and more). It is possible to return to the screen where
 * the details are edited. It is possible to log out of the family subscription
 * account. It is possible to go to the screen where you can create a new order.
 * 
 * @author Daniella Amdur
 */

public class FamilySubscriptionHistoryController extends AbstractScenes {

	@FXML
	private TableView<Order> tableOfHistory;

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
	private TableColumn<Order, String> col_orderNum;

	@FXML
	private TableColumn<Order, String> col_Date;

	@FXML
	private TableColumn<Order, String> col_EntryHour;

	@FXML
	private TableColumn<Order, String> col_parkName;

	@FXML
	private TableColumn<Order, String> col_numOfVisitors;

	@FXML
	private Text subNumber;

	public static FamilySubscriptionHistoryController instance;

	ObservableList<Order> oblist = FXCollections.observableArrayList();

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
		Subscriber subscriber = new Subscriber(null, null, null, null, null, null, null,
				ChatClient.subscriber.getSubscriberNumber());
		DataTransfer data = new DataTransfer(TypeOfMessage.REQUESTINFO, subscriber);
		ClientUI.chat.accept(data);
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
	 * initialize method. This method is responsible for defining variables by
	 * communicating with the server, is responsible for screen visibility (caption
	 * and titles) and on-screen functionality. The method creates a new
	 * subscription object with the ID number, the data is sent to the EcoServer and
	 * then to the server and request information of history data by the ID number.
	 * 
	 * @param location
	 * @param resources
	 */

	public void initialize(URL location, ResourceBundle resources) {
		instance = this;

		familyName.setText("Hello " + ChatClient.subscriber.getLname() + " Family");
		subNumber.setText("Subscriber Number: " + ChatClient.subscriber.getSubscriberNumber());

		col_orderNum.setCellValueFactory(new PropertyValueFactory<Order, String>("orderNumber"));
		col_Date.setCellValueFactory(new PropertyValueFactory<Order, String>("Date"));
		col_EntryHour.setCellValueFactory(new PropertyValueFactory<Order, String>("hour"));
		col_parkName.setCellValueFactory(new PropertyValueFactory<Order, String>("parkName"));
		col_numOfVisitors.setCellValueFactory(new PropertyValueFactory<Order, String>("numOfVisitors"));

		Subscriber subscriber = new Subscriber(ChatClient.subscriber.getId(), null, null, null, null, null, null, null);

		DataTransfer data = new DataTransfer(TypeOfMessage.REQUESTINFO_HISTORY, subscriber);
		ClientUI.chat.accept(data);
		tableOfHistory.setItems(oblist);
	}

	/**
	 * getLine method. The method adds another order history object to the history
	 * list of all subscription orders.
	 * 
	 * @param orderHistory
	 */

	public void getLine(Order orderHistory) {
		oblist.add(new Order(orderHistory.getParkName(), orderHistory.getHour(), orderHistory.getDate(),
				orderHistory.getEmail(), orderHistory.getOrderNumber(), orderHistory.getNumOfVisitors(),
				orderHistory.getNameOnOrder(), orderHistory.getID()));
	}

}
