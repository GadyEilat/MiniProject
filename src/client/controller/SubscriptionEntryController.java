package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import client.logic.Subscriber;
import client.logic.Worker;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SubscriptionEntryController extends AbstractScenes{
	String subNum;
	String id;
	Subscriber subscriber;
	public static SubscriptionEntryController instance;
    @FXML
    private Button btnEnter;

    @FXML
    private TextField subNumField;

    @FXML
    private Text msgText;

    @FXML
    private Button btnHelp;
    
    @FXML
    private Button btnBack;
	
	@FXML
    void backToMain(ActionEvent event) {
		switchScenes("/client/boundaries/Travelers.fxml", "Traveler");
    }
    
    @FXML
    void checkSubNumAndEnter(ActionEvent event) {
    	subNum = subNumField.getText();
    	
		if (subNum.trim().isEmpty()) 
			msgText.setText("You must enter a subscription number");
		
		else {
			id = subNum;
			subscriber = new Subscriber(id, null, null, null, null, null, null, subNum);
			DataTransfer data = new DataTransfer(TypeOfMessage.LOGIN_REQUEST, subscriber);
			ClientUI.chat.accept(data);
		}
    }

    @FXML
    void helpPopOut(ActionEvent event) throws IOException {
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
    public void initialize(URL location, ResourceBundle resources) {
		instance = this;
	}
    
    public void subscriberNotFound () {
    	msgText.setText("Not Found");
    }
    
    public void subscriberFound () {
    	switchScenes("/client/boundaries/EditInfoFamily.fxml", "Family Subscription");

    }

}
