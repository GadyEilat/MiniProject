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

import java.io.IOException;

import client.controller.ClientGUIController;
//import gui.DataController;
//import gui.ClientFrameController;
import client.controller.DataGuiController;

public class ClientUI extends Application {
	public static ClientController chat; // only one instance
	public static Stage primaryStage;
	public static FXMLLoader fxmlLoader = new FXMLLoader();
	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		chat = new ClientController("localhost", 5555);
		ClientUI.primaryStage = primaryStage;
		Parent current;
		try {
<<<<<<< HEAD
			//a
			ClientUI.fxmlLoader = new FXMLLoader(getClass().getResource("/client/boundaries/Existing Order.fxml"));
=======
			
			ClientUI.fxmlLoader = new FXMLLoader(getClass().getResource("/client/boundaries/Travelers.fxml"));
>>>>>>> refs/heads/Aviv
			current = (Parent)fxmlLoader.load();;
			Scene scene = new Scene(current);
			ClientUI.primaryStage.setScene(scene); 
			ClientUI.primaryStage.setMinHeight(600);
			ClientUI.primaryStage.setMinWidth(800);
			ClientUI.primaryStage.setTitle("Main Entrance");
			ClientUI.primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

}
