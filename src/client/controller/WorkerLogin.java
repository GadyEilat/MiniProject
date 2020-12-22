package client.controller;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientUI;
import common.DataTransfer;
import common.TypeOfMessage;
import common.logic.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WorkerLogin extends AbstractScenes {
	String userName;
	String password;
	Worker usrAndPass;
	@FXML
	private TextField userNameFeild;

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

	@FXML
	void btnReturnToMain(ActionEvent event) {
		switchScenes("/client/boundaries/main.fxml", "Main Entrance");
	}

	@FXML
	void checkLogIn(ActionEvent event) {
		userName = userNameFeild.getText();
		password = passwordField.getText();
		if(userName.trim().isEmpty()) {
			msgText.setText("You must enter User Name");
		}
		else if(password.trim().isEmpty()) {
			msgText.setText("You must enter Password");
		}
		else {
			usrAndPass = new Worker(userName,password);
			DataTransfer data = new DataTransfer(TypeOfMessage.LOGIN_REQUEST,usrAndPass);
			ClientUI.chat.accept(data);
//			checkLogInAnswer(userName);
		}
	}
	
	public void checkLogInAnswer(String user) {
//		if(answerIsFalse)
//			msgText.setText("Password or User Name inncorect");
//		else
		if(user.equals("m"))
			switchScenes("/client/boundaries/manager.fxml", "Manager");
		else if(user.equals("d"))
			switchScenes("/client/boundaries/mainDepartmantManager.fxml", "Departmant Manager");
		else if(user.equals("n"))
			switchScenes("/client/boundaries/FamilySubscriptionRegistration.fxml", "Service Representative");
		else
			msgText.setText(user + " not found");
	}

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
}
