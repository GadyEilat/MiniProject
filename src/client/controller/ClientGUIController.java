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

	boolean found = false;
	private Visitor visitor = new Visitor(null, null, null, null, null);
	boolean lock = true;
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
	////////////////////////////////////////////////////////////////////////////// ArrayList<String>
	////////////////////////////////////////////////////////////////////////////// st
	public void notFound() {
//		if (st.isEmpty()) {
		MsgFromController.setText("Visitor ID Not Found");
//			lock = false;
//
//		} else {
//			visitor.setId(st.get(0));
//			visitor.setFname(st.get(1));
//			visitor.setLname(st.get(2));
//			visitor.setEmail(st.get(3));
//			visitor.setteln(st.get(4));
//			found = true;
//			lock = false;
//			
//		}
	}
//	public void isFound(){
//		switchScenes("/client/boundaries/DataGui.fxml");
//		DataGuiController dataGuiController = ClientUI.fxmlLoader.getController();
//		dataGuiController.loadVisitor(ChatClient.visitor);	
//		
//		}
	public void isFound(boolean found){
		this.found=found;
	}


	@FXML
	public void ShowInfo() throws Exception {

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
				dataGuiController.loadVisitor(ChatClient.visitor);
//				Scene scene = new Scene(root);
//				primaryStage.setTitle("Student Managment Tool");
//				primaryStage.setScene(scene);
//				primaryStage.show();
//
				found = false;
			}
//			lock = false;
		}
//    	switchScenes("/client/boundaries/DataGui.fxml");
//    	ClientUI.chat.accept("SELECT * FROM visitors;");

	}

//	public void start(Stage primaryStage) throws IOException {
//		FXMLLoader.load(getClass().getResource("/client/boundaries/DataGui.fxml"));
//		Parent root = (Parent)FXMLLoader.load(getClass().getResource("/client/boundaries/DataGui.fxml"));
//		Scene scene = new Scene(root);
//		ClientUI.primaryStage.setTitle("Client");
//		ClientUI.primaryStage.setScene(scene);
//
//		ClientUI.primaryStage.show();
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
