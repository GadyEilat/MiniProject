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
		// switchScenes("/client/boundaries/TravelerNewOrder.fxml", "");
	}

	public static RegularTravelerController instance;
	public Object gID = "4";

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

	@FXML
	void backButton(ActionEvent event) {
		switchScenes("/client/boundaries/Travelers.fxml", "Traveler");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
	}
}
