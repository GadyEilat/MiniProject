package client.controller;
import java.net.URL;

import java.util.ResourceBundle;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CancelConfirmationController extends AbstractScenes{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button dontCancelBtn;

    @FXML
    void Cancel(ActionEvent event) {
    	//Cancel the Order --> Remove it from DataBase (Fix)
    	Stage current;
    	
    	switchScenes("/client/boundaries/Order Management.fxml", "ExistingOrder.fxml");
    }

    @FXML
    void DontCancel(ActionEvent event) { //go back to previous window
    	//fix --> need to check which window was before (Order Management or Change Order Details)???
    	switchScenes("/client/boundaries/Order Management.fxml", "Order Management");
    }

    @FXML
    void initialize() {
    }
}
