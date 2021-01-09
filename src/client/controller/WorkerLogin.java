package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import common.DataTransfer;
import common.TypeOfMessage;
import client.logic.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * WorkerLogin class. This class expands the AbstractScenes class that replaces
 * the scenes within the main stage. This class is responsible for the entrance
 * screen of the park workers. It is possible to return to the main screen of
 * the system, it is possible to click on the help button and receive
 * communication data for help.
 * 
 * @author Liran Amilov
 */

public class WorkerLogin extends AbstractScenes {
	String userName;
	String password;
	Worker usrAndPass;

	public static WorkerLogin instance;

	@FXML
	private TextField userNameField;

	@FXML
	private Button LogInButton;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Text msgText;

	@FXML
	private Button BackButton;

	@FXML
	private Button helpButton;;

	/**
	 * btnReturnToMain method. This method is responsible for moving the screen to
	 * the main screen of the system.
	 * 
	 * @param event
	 */

	@FXML
	void btnReturnToMain(ActionEvent event) {
		switchScenes("/client/boundaries/main.fxml", "GoNature");
	}

	/**
	 * checkLogIn method. This method is responsible for checking the integrity of
	 * the data when the user logs in. If one of the fields is missing, an error
	 * message is printed.
	 * 
	 * @param event
	 */

	@FXML
	void checkLogIn(ActionEvent event) {
		userName = userNameField.getText();
		password = passwordField.getText();
		if (userName.trim().isEmpty()) {
			msgText.setText("You must enter User Name");
		} else if (password.trim().isEmpty()) {
			msgText.setText("You must enter Password");
		} else {
			usrAndPass = new Worker(userName, password, null, null);
			DataTransfer data = new DataTransfer(TypeOfMessage.LOGIN_REQUEST, usrAndPass);
			ClientUI.chat.accept(data);
		}
	}

	/**
	 * logInAnswerFailed method. This method is responsible for printing an error
	 * message when you enter an incorrect username or password.
	 */

	public void logInAnswerFailed() {
		msgText.setText("Incorrect UserName or Password");
	}

	/**
	 * checkLogInAnswer method. This method is responsible for transferring the
	 * screen to the screen of that specific worker.
	 * 
	 * @param worker
	 */

	public void checkLogInAnswer(Worker worker) {
		switchScenes(worker.getScene(), worker.getRole());
	}

	/**
	 * alreadyConnected method. This method is responsible for printing a message
	 * when the employee is already connected and trying to connect again.
	 */

	public void alreadyConnected() {
		msgText.setText("You already Connected");
	}

	/**
	 * helpWindowPopOut method. This method is responsible for the PopOut window
	 * that appears when you click Help. The window displays contact information for
	 * help.
	 * 
	 * @param event
	 * @throws IOException
	 */

	@FXML
	void helpWindowPopOut(ActionEvent event) throws IOException {
		Stage helpWindow = new Stage();
		FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/client/boundaries/help.fxml"));
		Parent current = fxmlLoad.load();
		helpWindow.initModality(Modality.APPLICATION_MODAL);
		helpWindow.setTitle("Help");
		Scene scene = new Scene(current);
		helpWindow.setMinHeight(400);
		helpWindow.setMinWidth(600);
		helpWindow.setMaxHeight(400);
		helpWindow.setMaxWidth(600);
		helpWindow.setScene(scene);
		helpWindow.showAndWait();
	}

	/**
	 * initialize method. This method is responsible for defining variables by
	 * communicating with the server, is responsible for screen visibility (caption
	 * and titles) and on-screen functionality.
	 * 
	 * @param location
	 * @param resources
	 */

	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
	}
}
