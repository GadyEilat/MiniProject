package client.controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.ChatClient;
import client.ClientUI;
import client.logic.Order;

import client.logic.Visitor;
import client.logic.Worker;
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

public class TravelerNewOrderController extends AbstractScenes{	
	public Order TravelerOrder = new Order(null,null,null,null,null,null,null, null);
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField enterEmail;

    @FXML
    private Button continueToPayBtn;

    @FXML
    private Button waitingListTourBtn;

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
    ObservableList<String> list,list2,list3;
    
    public void notFound() {
    	errorEmail.setText("Error! cannot create Order");
	}
	public void isFound() {
		switchScenes("/client/boundaries/TravelerOrderSuccess.fxml", "Order Success");
		
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
	
		list3 = FXCollections.observableArrayList(a3);
		numVisitorsBtn.setItems(list3);
	}
    
    
    
   
    
    @FXML
    void LogOutButton(ActionEvent event) {
    	switchScenes("/client/boundaries/Travelers.fxml", "");
    }

    @FXML
    void backButton(ActionEvent event) {
    	switchScenes("/client/boundaries/RegularTraveler.fxml", "");
    }

    @FXML
    void chooseDayButton(ActionEvent event) {

    }

    @FXML
    void continueToPayButton(ActionEvent event) {
    	String orderPark =parkNameBtn.getSelectionModel().getSelectedItem().toString();
    	String orderDate = chooseDayBtn.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    	String ordeTime = chooseTime.getSelectionModel().getSelectedItem().toString();
    	String orderNumOfVisitors = numVisitorsBtn.getSelectionModel().getSelectedItem().toString();
    	String orderEmail = (enterEmail.getText());
    	String orderName = (firstName.getText());
    	if(validate(orderEmail))
    	{
    		TravelerOrder.setParkName(orderPark);
        	TravelerOrder.setDate(orderDate);
        	TravelerOrder.setHour(ordeTime);
        	TravelerOrder.setNumOfVisitors(orderNumOfVisitors);
        	TravelerOrder.setEmail(orderEmail);
        	TravelerOrder.setNameOnOrder(orderName);
        	
			DataTransfer data = new DataTransfer(TypeOfMessage.NEW_ORDER,TravelerOrder);
			ClientUI.chat.accept(data);
        	
        	System.out.println("Order Updated Successfully");
        	
        //	switchScenes("/client/boundaries/TravelerOrderSuccess.fxml", "");
    	}
    	else {
    		errorEmail.setText("You must enter a valid Email");
		}
    	
    	
    }

    @FXML
    void numVisitorsButton(ActionEvent event) {

    }

    @FXML
    void parkNameButton(ActionEvent event) {

    }

    @FXML
    void waitingListTourButton(ActionEvent event) {
    	System.out.print("Entered waiting list sucssesfully");
    }
//Checking if it's a valid Email
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    	    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
    	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
    	        return matcher.find();
    	}
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	instance=this;
    	setTimeComboBox();
    	setParkComboBox();
    	setNumOfVisitorsComboBox();
	}
	
    
}
