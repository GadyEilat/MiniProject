package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
	private Visitor visitor;
	@FXML
	private ResourceBundle resources;

	@FXML
	private Text MsgFromController;

	@FXML
	private Button showTableBtn;

	@FXML
	private TextField txtID;

	@FXML
	private Button exitBtn;

	@FXML
	private URL location;

	@FXML
	void initialize() {

	}

	// @FXML
	/*
	 * void ShowInfo(ActionEvent event) throws Exception { FXMLLoader loader = new
	 * FXMLLoader(); ChatClient.list.clear(); ClientUI.chat.accept("show");
	 * 
	 * ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary
	 * Stage primaryStage = new Stage(); Pane root =
	 * loader.load(getClass().getResource("/client/boundaries/DataGui.fxml").
	 * openStream()); DataGuiController dataController = loader.getController();
	 * 
	 * dataController.SetRows(ChatClient.list);
	 * 
	 * Scene scene = new Scene(root); primaryStage.setTitle("Data Table");
	 * 
	 * primaryStage.setScene(scene); primaryStage.show();
	 * 
	 * }
	 */
//	@FXML
//	private void checkID() {
//		String ID = txtID.getText();
//		if (ID.trim().isEmpty()) {
//			MsgFromController.setText("You must enter a ID number");
//		} else {
//			ClientUI.chat.accept(ID);
//		}
//	}

	////////////////////////////////////////////////////////////////////////////// 1
	////////////////////////////////////////////////////////////////////////////// query
	////////////////////////////////////////////////////////////////////////////// 2
	////////////////////////////////////////////////////////////////////////////// string
	public boolean answerForID() {
		return false;
	}

	@FXML
	public void ShowInfo(ActionEvent event) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		String ID = txtID.getText();
		if (ID.trim().isEmpty()) {
			MsgFromController.setText("You must enter an ID number");
		} else {
			ClientUI.chat.accept(ID);
			if (ChatClient.vis.getId() == null)
				MsgFromController.setText("Visitor ID Not Found");
			else {
				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				Parent root = (Parent)fxmlLoader.load(getClass().getResource("/client/boundaries/DataGui.fxml").openStream());
				DataGuiController dataGuiController = fxmlLoader.getController();
				dataGuiController.loadVisitor(ChatClient.vis);
				primaryStage.setScene(new Scene(root));
				primaryStage.show();
			}
		}
//    	switchScenes("/client/boundaries/DataGui.fxml");
//    	ClientUI.chat.accept("SELECT * FROM visitors;");
	}

	public void start(Stage primaryStage) throws IOException {
		// Parent root =
		// FXMLLoader.load(getClass().getResource("/client/boundaries/DataGui.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("/client/boundaries/ClientGUI.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Client");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	@FXML
	public void ExitButton(ActionEvent event) throws Exception {
		System.exit(0);
	}

}
