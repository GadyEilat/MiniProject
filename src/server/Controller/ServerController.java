package server.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.EchoServer;
import server.ServerUI;

public class ServerController  {	
	
	@FXML
	private Button btnExit = null;
	@FXML
	private Button btnRun = null;
	@FXML
	private Label lbllist;
	@FXML
	private TextField portxt;
	@FXML
	public TextArea msgArea;
	
	public static ServerController instance;

	public void Run(ActionEvent event) throws Exception {
		int port;
		if(portxt.getText().isEmpty()) {
			port = ServerUI.DEFAULT_PORT;
			ServerUI.runServer(port);
		}
		else {
			try { //if the port is not an int, don't runServer.
			port = (new Integer(portxt.getText()));
			ServerUI.runServer(port);
			}
			catch (NumberFormatException e) {
				System.out.println("The entered port is invalid");
			}
		}
//		if(port.trim().isEmpty())
//		{
//			port = ServerUI.DEFAULT_PORT;
//					
//		}
//		else
//		{
//			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
//			Stage primaryStage = new Stage();
//			FXMLLoader loader = new FXMLLoader();
//			ServerUI.runServer(p);
//		}
	}

	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/server/boundaries/serverGUI.fxml"));			
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("ServerGUI.css").toExternalForm());
		primaryStage.setTitle("GoNature Server");
		primaryStage.setScene(scene);	
		primaryStage.show();
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		displayMsg("exit GoNature Server!");
		//TimeUnit.SECONDS.sleep(5);
		System.exit(0);
	}
	public void displayMsg(String msg) {
		try {
			msgArea.appendText(msg + "\n");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}