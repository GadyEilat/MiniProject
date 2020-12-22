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

public abstract class AbstractScenes implements Initializable {
	
	public void switchScenes(String fxmlFile, String Title) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Parent current;
				try {
					double width = TempMain.primaryStage.getWidth();
					double height = TempMain.primaryStage.getHeight();
					TempMain.fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
					current = (Parent) TempMain.fxmlLoader.load();
					Scene scene = new Scene(current);
<<<<<<< HEAD
					ClientUI.primaryStage.setTitle(Title);
					ClientUI.primaryStage.setScene(scene);
					ClientUI.primaryStage.setWidth(width);
					ClientUI.primaryStage.setHeight(height);
					ClientUI.primaryStage.setMinHeight(600);
					ClientUI.primaryStage.setMinWidth(800);
					ClientUI.primaryStage.show();
=======
					TempMain.primaryStage.setTitle(Title);
					TempMain.primaryStage.setScene(scene);
					TempMain.primaryStage.setWidth(width);
					TempMain.primaryStage.setHeight(height);
					TempMain.primaryStage.show();
>>>>>>> refs/remotes/origin/GadyUpdated
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		
	}

//	public void switchScenes(String fxmlFile,String Title, String cssFile) {
//		Parent current;
//		try {
//
//			double width = ClientUI.primaryStage.getWidth();
//			double height = ClientUI.primaryStage.getHeight();
//			ClientUI.fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
//			current = (Parent) ClientUI.fxmlLoader.load();
//			Scene scene = new Scene(current);
//			scene.getStylesheets().add(cssFile);
//			ClientUI.primaryStage.setTitle(Title);
//			ClientUI.primaryStage.setScene(scene);
//			ClientUI.primaryStage.setWidth(width);
//			ClientUI.primaryStage.setHeight(height);
//			ClientUI.primaryStage.show();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public void initialize(URL location, ResourceBundle resources) {

	}
}
