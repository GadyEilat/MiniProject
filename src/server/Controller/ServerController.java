package server.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
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

public class ServerController implements Initializable {	
	public static ServerController instance;
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
	
	boolean isRunning = false;
	
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
				displayMsg("The entered port is invalid");
			}
		}
//		new SendEmail("liam69516@gmail.com", "TEST", "tmuna or kishalon");
	}
	public void disableRunBtn() {
		portxt.setDisable(true);
		btnRun.setDisable(true);
	}
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/server/boundaries/serverGUI.fxml"));			
		Scene scene = new Scene(root);
		primaryStage.setTitle("GoNature Server");
		primaryStage.setScene(scene);	
		primaryStage.show();
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		displayMsg("exit GoNature Server!");
		ServerUI.stopListen();
		System.exit(0);
	}
	public void displayMsg(String msg) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					msgArea.appendText(msg + "\n");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance=this;
	}

}