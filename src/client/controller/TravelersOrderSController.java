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


public class TravelersOrderSController extends AbstractScenes {
	
	public Order OrderSuccess = new Order(null,null,null,null,null,null,null,null);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
       	instance=this;
       	OrderSuccess=TravelerNewOrderController.instance.TravelerOrder;
       	helloText.setText("Hello " + OrderSuccess.getNameOnOrder());
       	OrderNumber.setText(OrderSuccess.getOrderNumber());
       	dateText.setText(OrderSuccess.getDate());
    }
}

