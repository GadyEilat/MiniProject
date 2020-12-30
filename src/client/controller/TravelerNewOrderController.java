package client.controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import client.ChatClient;
import client.ClientUI;
import client.logic.Order;

import client.logic.Visitor;
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
	Order TravelerOrder = new Order(null,null,null,null,null,null,null, null);
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
    private Button LogOutBtn;

    
    ObservableList<String> list,list2,list3;
    
    private void setTimeComboBox() {
		ArrayList<String> al = new ArrayList<String>();	
		al.add("8:00-12:00");
		al.add("12:00-16:00");

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
		/*a3.add("11");
		a3.add("12");
		a3.add("13");
		a3.add("14");
		a3.add("15");*/
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
        
    	TravelerOrder.setParkName(orderPark);
    	TravelerOrder.setDate(orderDate);
    	TravelerOrder.setHour(ordeTime);
    	TravelerOrder.setNumOfVisitors(orderNumOfVisitors);
    	TravelerOrder.setEmail(orderEmail);
    //	ClientUI.chat.accept(TravelerOrder);
    	System.out.println("Order Updated Successfully");
    	
    	switchScenes("/client/boundaries/TravelerOrderSuccess.fxml", "");
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
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	//loadGuide(ChatClient.tourguide);
    	setTimeComboBox();
    	setParkComboBox();
    	setNumOfVisitorsComboBox();
	}
    
}
