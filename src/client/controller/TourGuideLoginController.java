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
import common.DataTransfer;
import common.TypeOfMessage;
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

public class TourGuideLoginController extends AbstractScenes {
      TourGuide tourguide;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Text MsgFromController;

    @FXML
    private Button LogINGuideBtn;

    @FXML
    private TextField GuideID;
    
    
    public void notFound() {

		MsgFromController.setText("Visitor ID Not Found");
	}
    
    public void isFound(){
    	switchScenes("/client/boundaries/TourGuideMainMenu.fxml", "Main Menu");
	}
    public static TourGuideLoginController instance;
    public Object gID="4";
    
    
    
    @FXML
    void LogINGuideButton(ActionEvent event) {
    	String guideID = GuideID.getText();
    	if (guideID.trim().isEmpty()) {
    		MsgFromController.setText("You must enter an ID number");
    	}
        else {
        	TourGuide tourguide= new TourGuide(guideID,null,null,null,null);
           	DataTransfer data = new DataTransfer(TypeOfMessage.TOURGUIDELOGIN,tourguide);
	
		ClientUI.chat.accept(data);
		//switchScenes("/client/boundaries/TourGuideMainMenu.fxml", "");
		}
    	
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
		instance = this;
	}
}

