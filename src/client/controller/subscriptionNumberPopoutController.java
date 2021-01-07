package client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class subscriptionNumberPopoutController extends AbstractScenes{

    @FXML
    private Text msgForService;

    @FXML
    private Text subNum;

    @FXML
    private Text emailSent;

    @FXML
    private Button btnOK;
    
    @FXML
    void closePopOut(ActionEvent event) {
    	Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.close();
    }

	public void initialize(URL location, ResourceBundle resources) {
		if(ServiceRepresentativeController.instance.isFamily) {
			subNum.setText(ChatClient.subscriber.getSubscriberNumber());
			msgForService.setText("Subscriber Number");
		}
		else {
			msgForService.setText("Tour Guide");
			subNum.setText("Registered successfully");
		}

	}
}
