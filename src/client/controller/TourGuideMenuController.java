package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import client.ChatClient;
import client.ClientUI;
import client.logic.TourGuide;
import client.logic.Visitor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TourGuideMenuController extends AbstractScenes  {
	TourGuide tourguide;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button LogOutBtn;

    @FXML
    private Text tourName;
    @FXML
    private Button updateDetalisGuideBtn;

    @FXML
    private Button NewOrderBtn;

    @FXML
    private Button myOrdersBtn;
    @FXML
    private Button changeOrdersGuide;
    
    
    
    public void loadGuide(TourGuide tourguidee) {
    	this.tourguide = tourguidee;
         this.tourName.setText(tourguidee.getFname());
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
    void updateDetalisGuideButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideChangeDetails.fxml", "Change Details");
         
    }    

    @FXML
    void changeOrdersGuideBtn(ActionEvent event) {

    }
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	loadGuide(ChatClient.tourguide);

	}
    
    
    
//    @FXML
//    void initialize() {
//        assert LogOutBtn != null : "fx:id=\"LogOutBtn\" was not injected: check your FXML file 'TourGuideMainMenu.fxml'.";
//        assert updateDetalisGuideBtn != null : "fx:id=\"updateDetalisGuideBtn\" was not injected: check your FXML file 'TourGuideMainMenu.fxml'.";
//        assert NewOrderBtn != null : "fx:id=\"NewOrderBtn\" was not injected: check your FXML file 'TourGuideMainMenu.fxml'.";
//        assert myOrdersBtn != null : "fx:id=\"myOrdersBtn\" was not injected: check your FXML file 'TourGuideMainMenu.fxml'.";
//
//    }
}

