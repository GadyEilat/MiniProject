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
	    private ComboBox<?> KindOFTrvlr;
	    
	    @FXML
	    private Button nextBtn;

	    @FXML
	    private Button backBtn; 

	  
	    @FXML
	    void backButton(ActionEvent event) {

	    }
   /* public void notFound() {

		MsgFromController.setText("Visitor ID Not Found");
	}
    
    public void isFound(){
    	switchScenes("/client/boundaries/TourGuideMainMenu.fxml", "");
	}*/
    public static TravelersController instance;
    public Object gID="4";
    
    
    
    @FXML
    void nextButton(ActionEvent event) {
    	
		switchScenes("/client/boundaries/RegularTraveler.fxml", "");
		}
    	
    @FXML
    void KindOfTraveler(ActionEvent event) {

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
		instance = this;
	}
}

