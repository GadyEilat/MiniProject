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

/**
 * TravelersController class
 * @author Aviv Kamary 
 * This controller is responsible for the screen displaying the kind of travelers that can enter and order. 
 * The controller expands the AbstractScenes class that replaces the scenes within the main stage.  
 * It is possible to go to a new screen and make a regular order. 
 * It is possible to go to a new screen and make a TourGuide order. 
 * It is possible to enter an existing order and make changes in it. 
 * It is possible to go to family subscribers screen. 
 * It is possible to return back to the Travelers Entrance.
 */

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

	public static TravelersController instance;
	
	
	/**
	 * backButton method
	 * @param event 
	 * This method is responsible for returning back to the GoNature Travelers Entrance.
	 */
	
	@FXML
	void backButton(ActionEvent event) {
		switchScenes("/client/boundaries/main.fxml", "GoNature");

	}

	/**
	 * existButton method
	 * @param event
	 * This method is responsible for entering to an Existing Order screen.
	 */
	
	@FXML
	void existButton(ActionEvent event) {
		switchScenes("/client/boundaries/Existing Order.fxml", "Existing Order");
	}

	/**
	 * familyButton method
	 * @param event
	 * This method is responsible for entering to the Family Subscription screen
	 */
	
	@FXML
	void familyButton(ActionEvent event) {
		switchScenes("/client/boundaries/FamilySubEnter.fxml", "Family Subscription");
	}

	/**
	 * newButton method
	 * @param event
	 * This method is responsible for entering to the Regular Traveler screen.
	 */
	
	@FXML
	void newButton(ActionEvent event) {
		switchScenes("/client/boundaries/RegularTraveler.fxml", "Regular Traveler");
	}

	/**
	 * tourButton method
	 * @param event
	 * This method is responsible for entering the TourGuide screen.
	 */
	@FXML
	void tourButton(ActionEvent event) {
		switchScenes("/client/boundaries/TourGuideLogin.fxml", "Tour Guide");
	}

	/**
	 * initialize method
	 * @param location
	 * @param resources
	 * This method is responsible for defining variables by communicating with the server, 
	 * is responsible for screen visibility (caption and titles) and on-screen functionality.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;

	}
}
