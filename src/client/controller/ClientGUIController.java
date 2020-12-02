package client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClientGUIController {

    @FXML
    private ResourceBundle resources;

    @FXML
	private Button showTableBtn;
    
    @FXML
    private Button exitBtn;
    
    @FXML
    private URL location;

    @FXML
    void initialize() {

    }
    @FXML
    void ShowInfo(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		ChatClient.list.clear();
		ClientUI.chat.accept("show");

		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/client/boundaries/DataGui.fxml").openStream());
		DataGuiController dataController = loader.getController();

		dataController.SetRows(ChatClient.list);

		Scene scene = new Scene(root);
		primaryStage.setTitle("Data Table");

		primaryStage.setScene(scene);
		primaryStage.show();

	}
    
    @FXML
    public void ExitButton(ActionEvent event) throws Exception 
    {
		System.exit(0);
	}

}
