package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class AbstractScenes implements Initializable{
	public void switchScenes(String fxmlFile) {
				Parent current;
				try {
						ClientUI.fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
						current = (Parent) ClientUI.fxmlLoader.load();
						Scene scene = new Scene(current);
						ClientUI.primaryStage.setScene(scene); 
						ClientUI.primaryStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
	public void switchScenes(String fxmlFile, String cssFile) {
		Parent current;
		try {
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
			current = (Parent)fxmlLoader.load();;
            Stage primaryStage = new Stage();
			Scene scene = new Scene(current);
			scene.getStylesheets().add(cssFile);
			ClientUI.primaryStage.setScene(scene);
			ClientUI.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}


}
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
