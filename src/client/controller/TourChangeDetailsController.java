package client.controller;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

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
public class TourChangeDetailsController extends AbstractScenes {
	TourGuide tourguide;
	
	   @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private TextField ChangeName;

	    @FXML
	    private TextField changeLNameTour;

	    @FXML
	    private TextField changeEmailTour;

	    @FXML
	    private Button updateDetailsTourBtn;

	    @FXML
	    private TextField changePhoneTour;

	    @FXML
	    private Button LogOutBtn;

	    @FXML
	    private Text tourNameM;

	    @FXML
	    private Button updateDetalisGuideBtn;

	    @FXML
	    private Button NewOrderBtn;

	    @FXML
	    private Button myOrdersBtn;
	    
	    @FXML
	    private Button backButtonUpdateDetails;
    
    
    public void loadGuide(TourGuide tourguideM) {
    	this.tourguide = tourguideM;
         this.tourNameM.setText(tourguideM.getFname());
    }
    
    
    
    public void loadGuideMenuChange(TourGuide tourguide) {
		this.tourguide = tourguide;
		this.ChangeName.setText(tourguide.getFname());
		this.changeLNameTour.setText(tourguide.getLname());
		this.changeEmailTour.setText(tourguide.getEmail());
		this.changePhoneTour.setText(tourguide.getTeln());
	}
    
    @FXML
    void backButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideMainMenu.fxml", "Main Menu");
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
    void updateDetailsTourButton(ActionEvent event) {
    	String updatedName = (ChangeName.getText());
    	String updatedLName = (changeLNameTour.getText());
    	String updatedEmail = (changeEmailTour.getText());
    	String updatedPhone = (changePhoneTour.getText());
    	//String updatedID="4";
    	tourguide.setId(tourguide.getId());
    	tourguide.setFname(updatedName);
    	tourguide.setLname(updatedLName);
    	tourguide.setEmail(updatedEmail);
		tourguide.setteln(updatedPhone);
		ClientUI.chat.accept(tourguide);
		System.out.println("tourguide Updated Successfully");
    }

    @FXML
    void updateDetalisGuideButton(ActionEvent event) {

    }
    @FXML
    void changeEmailTourM(ActionEvent event) {

    }

    @FXML
    void changeLNameTourM(ActionEvent event) {

    }

    @FXML
    void changeNameTourG(ActionEvent event) {

    }

    @FXML
    void changePhoneTourM(ActionEvent event) {

    }
    
    
  
    
    @Override
   	public void initialize(URL location, ResourceBundle resources) {
       	loadGuide(ChatClient.tourguide);
       	loadGuideMenuChange(ChatClient.tourguide);

   	}
//    @FXML
//    void initialize() {
//        assert updateDetailsTourBtn != null : "fx:id=\"updateDetailsTourBtn\" was not injected: check your FXML file 'TourGuideChangeDetails.fxml'.";
//        assert LogOutBtn != null : "fx:id=\"LogOutBtn\" was not injected: check your FXML file 'TourGuideChangeDetails.fxml'.";
//        assert updateDetalisGuideBtn != null : "fx:id=\"updateDetalisGuideBtn\" was not injected: check your FXML file 'TourGuideChangeDetails.fxml'.";
//        assert NewOrderBtn != null : "fx:id=\"NewOrderBtn\" was not injected: check your FXML file 'TourGuideChangeDetails.fxml'.";
//        assert myOrdersBtn != null : "fx:id=\"myOrdersBtn\" was not injected: check your FXML file 'TourGuideChangeDetails.fxml'.";
//
//    }
}




