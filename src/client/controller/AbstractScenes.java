package client.controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class AbstractScenes {
	public void switchScenes(String fxmlFile) {
				Parent current;
				try {
					
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
					current = (Parent)fxmlLoader.load();;
		            Stage primaryStage = new Stage();
					Scene scene = new Scene(current);
		            primaryStage.setScene(new Scene(current)); 
					primaryStage.show();
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
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}


}
}
