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

public class ClientGUIController extends AbstractScenes {
	public ClientGUIController() {

	}

	boolean found = false;
	@FXML
	private ResourceBundle resources;

	@FXML
	private Text MsgFromController;

	@FXML
	private Button showTableBtn;

	@FXML
	private TextField txtID;

    @FXML
    private Text tourName;
    
	@FXML
	private Button exitBtn;

	@FXML
	private URL location;

	public static ClientGUIController instance;

	public void notFound() {

		MsgFromController.setText("Visitor ID Not Found");
	}
	
	
	public void isFound(){
		switchScenes("/client/boundaries/DataGui.fxml", "GoNature Enter");
	}
	
	@FXML
	public void ShowInfo() throws Exception {

		String ID = txtID.getText();
		if (ID.trim().isEmpty()) {
			MsgFromController.setText("You must enter an ID number");
		} else {
			ClientUI.chat.accept(ID);
			}
		}

	@FXML
	public void ExitButton(ActionEvent event) throws Exception {
		System.exit(0);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;

	}

}
