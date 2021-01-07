package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import client.ChatClient;
import client.ClientUI;


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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;




public class TravelersController extends AbstractScenes {
      
	
	  @FXML
	    private ResourceBundle resources;

	  @FXML
	    private Button familyBtn;

	    @FXML
	    private Button newBtn;

	    @FXML
	    private Button existBtn;

	    @FXML
	    private Button tourBtn;
	    
	    @FXML
	    private URL location;
	 
	    
	    @FXML
	    private Button nextBtn;

	    @FXML
	    private Button backBtn; 

	  
	    @FXML
	    void backButton(ActionEvent event) {
	    	switchScenes("/client/boundaries/main.fxml", "GoNature");

	    }
   
    public static TravelersController instance;
    ObservableList<String> list;

    

    @FXML
    void existButton(ActionEvent event) {
    	switchScenes("/client/boundaries/Existing Order.fxml", "Existing Order");
    }

    @FXML
    void familyButton(ActionEvent event) {
    	switchScenes("/client/boundaries/FamilySubEnter.fxml", "Family Subscription");
    }

    @FXML
    void newButton(ActionEvent event) {
    	switchScenes("/client/boundaries/RegularTraveler.fxml", "Regular Traveler");
    }

    @FXML
    void tourButton(ActionEvent event) {
    	switchScenes("/client/boundaries/TourGuideLogin.fxml", "Tour Guide");
    }

    
  
    @Override
    public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		
	}
}

