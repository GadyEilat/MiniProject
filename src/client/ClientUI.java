package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Optional;

import common.DataTransfer;
import common.TypeOfMessage;



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
			ClientUI.fxmlLoader = new FXMLLoader(getClass().getResource("/client/boundaries/main.fxml"));
			current = (Parent)fxmlLoader.load();;
			Scene scene = new Scene(current);
			ClientUI.primaryStage.setScene(scene); 
			ClientUI.primaryStage.setMinHeight(600);
			ClientUI.primaryStage.setMinWidth(800);
			ClientUI.primaryStage.setTitle("GoNature");
			ClientUI.primaryStage.show();
			
			primaryStage.getIcons().add(new Image(this.getClass().getResource("/LOGONATURE.png").toString()));
			primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.getButtonTypes().remove(ButtonType.OK);
					alert.getButtonTypes().add(ButtonType.CANCEL);
					alert.getButtonTypes().add(ButtonType.YES);
					alert.setTitle("Quit application");
					alert.setContentText(String.format("Are you sure you want to quit?"));
					alert.initOwner(primaryStage.getOwner());
					Optional<ButtonType> res = alert.showAndWait();

					if (res.isPresent()) {
						if (res.get().equals(ButtonType.CANCEL)) {
							event.consume();
						} else if (chat.client != null) {
							if (ChatClient.connected) {
								DataTransfer data = new DataTransfer(TypeOfMessage.LOGOUT, ChatClient.worker);
								ClientUI.chat.accept(data);
							}
							chat.client.quit();
						}
					}
				}

			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

}
