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
//import gui.DataController;
//import gui.ClientFrameController;
import gui.ClientDataTableController;

public class ClientUI extends Application {
	public static ClientController chat; // only one instance

	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//chat = new ClientController("127.0.0.1", 5555);
		
		//Parent root = FXMLLoader.load(getClass().getResource("/gui/DataGui.fxml"));
		Parent root2 = FXMLLoader.load(getClass().getResource("/gui/ClientGUI.fxml"));

		Scene scene = new Scene(root2);
		primaryStage.setTitle("Client");
		primaryStage.setScene(scene);

		primaryStage.show();
		
		//ClientDataTableController aFrame = new ClientDataTableController(); 

		//aFrame.start(primaryStage);
	}

}
