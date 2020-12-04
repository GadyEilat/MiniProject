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
	public ClientGUIController() {

	}

	boolean found;
	private Visitor visitor = new Visitor(null, null, null, null, null);
	Object lock = new Object();
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

	public static ClientGUIController instance;

	////////////////////////////////////////////////////////////////////////////// 1
	////////////////////////////////////////////////////////////////////////////// query
	////////////////////////////////////////////////////////////////////////////// 2
	////////////////////////////////////////////////////////////////////////////// string
	public void answerForID(ArrayList<String> st) throws IOException {
		if (st.isEmpty()) {
			MsgFromController.setText("Visitor ID Not Found");
			
		} else {
			visitor.setId(st.get(0));
			visitor.setFname(st.get(1));
			visitor.setLname(st.get(2));
			visitor.setEmail(st.get(3));
			visitor.setteln(st.get(4));
			found = true;

		}
	}

	@FXML
	public void ShowInfo(ActionEvent event) throws Exception {
		
		String ID = txtID.getText();
		if (ID.trim().isEmpty()) {
			MsgFromController.setText("You must enter an ID number");
		} else {
			ClientUI.chat.accept(ID);

			if (found) {
//				((Node) event.getSource()).getScene().getWindow().hide();
//				Stage primaryStage = new Stage();
//				Pane root = fxmlLoader.load(getClass().getResource("/client/boundaries/DataGui.fxml").openStream());
				switchScenes("/client/boundaries/DataGui.fxml");
				DataGuiController dataGuiController = ClientUI.fxmlLoader.getController();
				dataGuiController.loadVisitor(visitor);
//				Scene scene = new Scene(root);
//				primaryStage.setTitle("Student Managment Tool");
//				primaryStage.setScene(scene);
//				primaryStage.show();
				
				found = false;
			
			}
//    	switchScenes("/client/boundaries/DataGui.fxml");
//    	ClientUI.chat.accept("SELECT * FROM visitors;");
		}
	}

//	public void start(Stage primaryStage) throws IOException {
//		// Parent root =
//		// FXMLLoader.load(getClass().getResource("/client/boundaries/DataGui.fxml"));
////		Parent root = (Parent)FXMLLoader.load(getClass().getResource("/client/boundaries/ClientGUI.fxml"));
////		Scene scene = new Scene(root);
////		primaryStage.setTitle("Client");
////		primaryStage.setScene(scene);
////
////		primaryStage.show();
//		switchScenes("/client/boundaries/ClientGUI.fxml");
//	}

	@FXML
	public void ExitButton(ActionEvent event) throws Exception {
		System.exit(0);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;

	}

}
