package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import client.controller.ClientGUIController;
//import gui.DataController;
//import gui.ClientFrameController;
import client.controller.DataGuiController;

public class ClientUI extends Application {
	public static ClientController chat; // only one instance

	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		chat = new ClientController("localhost", 5555);

		ClientGUIController startClienr = new ClientGUIController();
		startClienr.start(primaryStage);

	}

}
