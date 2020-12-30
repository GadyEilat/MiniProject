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
	    private URL location;
	    @FXML
	    private ComboBox<String> KindOFTrvlr;
	    
	    @FXML
	    private Button nextBtn;

	    @FXML
	    private Button backBtn; 

	  
	    @FXML
	    void backButton(ActionEvent event) {
	    	switchScenes("/client/boundaries/main.fxml", "GoNature");

	    }
   /* public void notFound() {

		MsgFromController.setText("Visitor ID Not Found");
	}
    
    public void isFound(){
    	switchScenes("/client/boundaries/TourGuideMainMenu.fxml", "");
	}*/
    public static TravelersController instance;
    ObservableList<String> list;
    String kindOfTraveler=null;
    
    @FXML
    void nextButton(ActionEvent event) {
    	kindOfTraveler=KindOFTrvlr.getValue();
    	if (kindOfTraveler=="New") { //go to aviv screen
    		switchScenes("/client/boundaries/RegularTraveler.fxml", "Regular Traveler");
    	}
    	else if (kindOfTraveler=="Existing Order") { // go to gady screen
    		switchScenes("/client/boundaries/Existing Order.fxml", "Existing Order");
		}
    	else if (kindOfTraveler=="Family Member") { //go to daniela screen
    		switchScenes("/client/boundaries/FamilySubEnter.fxml", "Family Subscription");
		}
    	else if (kindOfTraveler=="Tour Guide") { //go to elad screen
    		switchScenes("/client/boundaries/TourGuideLogin.fxml", "Tour Guide");
		}
    	else { //nothing picked.
    		//fix --> send a message saying "please pick a kind of traveler" or something.
    	}
    }
    @FXML
    void KindOfTraveler(ActionEvent event) {
    	
    }
    
    private void setKindComboBox() {
    	ArrayList<String> al = new ArrayList<String>();	
		al.add("New");
		al.add("Existing Order");
		al.add("Family Member");
		al.add("Tour Guide");
		list = FXCollections.observableArrayList(al);
		KindOFTrvlr.setItems(list);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		setKindComboBox();
	}
}

