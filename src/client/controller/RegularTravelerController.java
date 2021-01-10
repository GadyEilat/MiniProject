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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
	/**
	 * RegularTravelerController class
	 * @author Aviv Kamary
	 * This controller is responsible for the Regular Traveler order screen to create a new order.
	 * The controller expands the AbstractScenes class that replaces the scenes within the main stage. 
	 * It is possible to return back to the kind of travelers screen.
	 */
public class RegularTravelerController extends AbstractScenes {
	public String ID=null;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField TravelerID;

	@FXML
	private Button NextTravelerID;

	@FXML
	private Text errorLogin;

	@FXML
	private Button backBtn;

	public void notFound() {

		errorLogin.setText("Visitor ID Not Found");
	}

	public void isFound() {
	}

	public static RegularTravelerController instance;

	/**
	 * NextTravelerID method
	 * @param event
	 * This method taking the ID the user entered in the screen to save it in the database.
	 * This method also checks if it's a valid ID between 0-9.
	 */
	@FXML
	void NextTravelerID(ActionEvent event) {
		String guideID = TravelerID.getText();
		if (guideID.trim().isEmpty()) {
			errorLogin.setText("You must enter an ID number");
		} else if (guideID.length() == 9 && guideID.matches("[0-9]+")) {
			ID=guideID;
			switchScenes("/client/boundaries/TravelerNewOrder.fxml", "New Order");
		} else {
			errorLogin.setText("You must enter a valid ID number");
		}
	}
	/**
	 * backButton method
	 * @param event
	 * In this method you can return back to the kind of travelers screen to choose another kind of entrance.
	 */
	@FXML
	void backButton(ActionEvent event) {
		switchScenes("/client/boundaries/Travelers.fxml", "Traveler");
	}
	/**
	 * initialize method
	 * @param location
	 * @param resources
	 * This method is responsible for defining variables by communicating with the server, 
	 * is responsible for screen visibility (caption and titles) and on-screen functionality.
	 * This method taking the RegularTraveler instance the user entered the screen.
	
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
	}
}
