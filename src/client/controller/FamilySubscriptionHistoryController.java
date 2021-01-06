package client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.Order;
import client.logic.Subscriber;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class FamilySubscriptionHistoryController extends AbstractScenes{

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
	
	ObservableList <Order> oblist=FXCollections.observableArrayList();

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
	


	
	
	
	
	
	
	
	
   	public void initialize(URL location, ResourceBundle resources) {
    	instance = this;
    	
    	familyName.setText("Hello " + ChatClient.subscriber.getLname() + " Family");
		subNumber.setText("Subscriber Number: " + ChatClient.subscriber.getSubscriberNumber());
       	       	
      	col_orderNum.setCellValueFactory(new PropertyValueFactory<Order, String>("orderNumber"));
      	col_Date.setCellValueFactory(new PropertyValueFactory<Order, String>("Date"));
      	col_EntryHour.setCellValueFactory(new PropertyValueFactory<Order, String>("hour"));
      	col_parkName.setCellValueFactory(new PropertyValueFactory<Order, String>("parkName"));
      	col_numOfVisitors.setCellValueFactory(new PropertyValueFactory<Order, String>("numOfVisitors"));

      
      	Subscriber subscriber = new Subscriber( ChatClient.subscriber.getId(), null, null, null, null, null, null, null);
      	
       	DataTransfer data = new DataTransfer(TypeOfMessage.REQUESTINFO_HISTORY,subscriber);
       	ClientUI.chat.accept(data);
       	tableOfHistory.setItems(oblist);
       	
   	}


    public void getLine(Order t) {
    	
    	oblist.add(new Order(t.getParkName(), t.getHour(), t.getDate(), t.getEmail(), t.getOrderNumber(), t.getNumOfVisitors(), t.getNameOnOrder(), t.getID()));
    }
    


}
