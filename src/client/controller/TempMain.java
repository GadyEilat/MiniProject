package client.controller;

import java.io.IOException;

import client.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TempMain extends Application{
	public static Stage primaryStage;
	public static ExistingOrderController controller;
	public BorderPane mainLayout;
	public static FXMLLoader fxmlLoader = new FXMLLoader();
	
	public static void main(String[] args) throws Exception{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		controller = new ExistingOrderController();
		TempMain.primaryStage = primaryStage;
		TempMain.primaryStage.setTitle("Existing Order");
		Parent current;
		try {
			TempMain.fxmlLoader = new FXMLLoader(getClass().getResource("/client/boundaries/Existing Order.fxml"));
			fxmlLoader.setController(controller);
			current = (Parent)fxmlLoader.load();
			Scene scene = new Scene(current);
			scene.getStylesheets().add("client/boundaries/ManagerStyle.css");
			TempMain.primaryStage.setScene(scene); 
			TempMain.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

}
