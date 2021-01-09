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

/**
 * Pop up window for canceling an order.
 * The window is oepened after you decide to cancel an existing order.
 * You got 2 options, either Cancel it, or not cancel it.
 * If you cancel the order, it will be removed from DB and you will no longer have access to it.
 * If you don't cancel it, you'll just go to the previous window.
 * @author Gady
 *
 */
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

    /**
     * click cancel to cancel your order and delete it from database, afterwards a check of waitinglist is also being applied.
     * @param event if cancel button was clicked
     */
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

    /**
     * if you decided not to cancel, the pop up window will close and you'll get back to the previous one. 
     * @param event if that button was clicked.
     */
    @FXML
    void DontCancel(ActionEvent event) { //go back to previous window
    	Stage stage = (Stage) dontCancelBtn.getScene().getWindow();
        stage.close();	
    }
/**
 * Initializing the window.
 */
    public void initialize() {
    }
}
