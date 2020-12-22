package client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SubscriptionEntryController extends AbstractScenes{

    @FXML
    private Button btnEnter;

    @FXML
    private TextField subNumField;

    @FXML
    private Text msgText;

    @FXML
    private Button btnHelp;

    @FXML
    private ImageView helpButton;

    @FXML
    void checkSubNumAndEnter(ActionEvent event) {
    	// check with DB
		switchScenes("/client/boundaries/EditInfoFamily.fxml", "Family Subscription");
    }

    @FXML
    void helpPopOut(ActionEvent event) {

    }

}
