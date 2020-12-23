package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import client.ChatClient;
import client.ClientUI;
import client.logic.TourGuide;
import client.logic.TourGuideOrder;
import client.logic.Visitor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class TourGuideOrderSController extends AbstractScenes {
	
	TourGuide tourguide;
	TourGuideOrder tourguideorder;
	//TourGuideNewOrderController orderup= new TourGuideNewOrderController();
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button viewOrderTourBtn;

    @FXML
    private Button returnToMenuTourBTN;

    @FXML
    private Button LogOutBtn;

    @FXML
    private Button updateDetalisGuideBtn;

    @FXML
    private Button NewOrderBtn;

    @FXML
    private Button myOrdersBtn;
    
    @FXML
    private Text TourNo;
    
    @FXML
    private TextField dateOrderGuide;

    @FXML
    private TextField orderNumberGuide;

    @FXML
    private TextField guideNameOrderS;
    
//    public void recieve(){
//       // System.out.println("I recieved "+data);
//        this.dateOrderGuide.setText(orderup.tourguideorderr.getDate());
//        this.orderNumberGuide.setText(orderup.tourguideorderr.getOrderNumber());
//    }
    
    
    public void loadGuide(TourGuide tourguideO, TourGuideOrder newOrederG) {
    	this.tourguide = tourguideO;
    	this.tourguideorder=newOrederG;
         this.TourNo.setText(tourguideO.getFname());
         this.guideNameOrderS.setText(tourguideO.getFname());
         this.dateOrderGuide.setText(newOrederG.getDate());
         this.orderNumberGuide.setText(newOrederG.getOrderNumber());
    }

    @FXML
    void LogOutButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideLogin.fxml", "Login");
    }

    @FXML
    void NewOrderButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideNewOrder.fxml", "New Order");
    }

    @FXML
    void myOrdersButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideMyOrders.fxml", "My Orders");
    }

    @FXML
    void returnToMenuTourButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideMainMenu.fxml", "Main Menu");
    }

    @FXML
    void updateDetalisGuideButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideChangeDetails.fxml", "Change Details");
    }

    @FXML
    void viewOrderTourButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideMyOrders.fxml", "My Orders");
    }
    
    
    @Override
  		public void initialize(URL location, ResourceBundle resources) {
  	    	loadGuide(ChatClient.tourguide, TourGuideNewOrderController.tourguideorderr);
  	    	//recieve();
    }
    
    
    
    
    

//    @FXML
//    void initialize() {
//        assert viewOrderTourBtn != null : "fx:id=\"viewOrderTourBtn\" was not injected: check your FXML file 'TourGuideOrderSuccssed.fxml'.";
//        assert returnToMenuTourBTN != null : "fx:id=\"returnToMenuTourBTN\" was not injected: check your FXML file 'TourGuideOrderSuccssed.fxml'.";
//        assert LogOutBtn != null : "fx:id=\"LogOutBtn\" was not injected: check your FXML file 'TourGuideOrderSuccssed.fxml'.";
//        assert updateDetalisGuideBtn != null : "fx:id=\"updateDetalisGuideBtn\" was not injected: check your FXML file 'TourGuideOrderSuccssed.fxml'.";
//        assert NewOrderBtn != null : "fx:id=\"NewOrderBtn\" was not injected: check your FXML file 'TourGuideOrderSuccssed.fxml'.";
//        assert myOrdersBtn != null : "fx:id=\"myOrdersBtn\" was not injected: check your FXML file 'TourGuideOrderSuccssed.fxml'.";
//
//    }
}
