package client.controller;
import java.net.URL;

import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import client.logic.Order;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CancelConfirmationController extends AbstractScenes{
	public String orderNumberToBeDeleted=null;
	public Order orderToBeDeleted=null;
	
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
    	orderToBeDeleted=OrderManagementController.instance.ord;
    	DataTransfer data = new DataTransfer(TypeOfMessage.DELETEINFO,orderToBeDeleted);
		ClientUI.chat.accept(data);
		switchScenes("/client/boundaries/Existing Order.fxml", "Existing Order");
    	Stage stage = (Stage) cancelBtn.getScene().getWindow();
    	ChatClient.order = new Order();
        stage.close();
    }

    @FXML
    void DontCancel(ActionEvent event) { //go back to previous window
    	Stage stage = (Stage) dontCancelBtn.getScene().getWindow();
        stage.close();	
    }

    public void initialize() {
    }
}
