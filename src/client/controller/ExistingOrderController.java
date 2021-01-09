package client.controller;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import client.logic.Order;
import client.logic.Visitor;
import common.DataTransfer;
import common.TypeOfMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * ExistingOrderController Class:
 * The window Existing Order relates to an order that was already made, and someone wants to make changes on it.
 * he needs to enter his order number in order to view his details
 * The controller expands the AbstractScenes class that replaces the scenes within the main stage. 
 * @author Gady
 *
 *
 */
public class ExistingOrderController extends AbstractScenes{

	public Order order = new Order(null,null,null,null,null,null, null, null,null,null,null,null);
	public static String recievedOrderNum= null;	
	    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text msgFromController;
    
    @FXML
    private TextField OrderNumberTxt;

    @FXML
    private Button nextBtn;

    @FXML
    private Button BackBtn;

    @FXML
    private ImageView backBtnImage;

    /**
     * goes back to the Travelers screen
     * @param event when you click on the button "Back"
     */
    @FXML
    void GoBack(ActionEvent event) {
    	switchScenes("/client/boundaries/Travelers.fxml", "Travelers");
    }

    /**
     * get here from chat client if no such order number was found
     */
    public void notFound() {
		msgFromController.setText("Order Number Not Found");
	}
	
	/**
	 * get here from chat client if we found an order number in DB as was written. then move to Order Management window.
	 */
	public void isFound() {
		switchScenes("/client/boundaries/Order Management.fxml", "Order Management");
	}
	
	public static ExistingOrderController instance;
    
	/**
	 * go to the next window, but before check if the order number that was entered exist in DB
	 * @param event if next button was clicked.
	 */
    @FXML
    void NextButton(ActionEvent event) {
    	String OrderNum = OrderNumberTxt.getText();
		if (OrderNum.trim().isEmpty()) {
			msgFromController.setText("Please enter an existing order number");
		}
		else { //send to server and check the order number? (FIX)
			order.setOrderNumber(OrderNum);
			DataTransfer data = new DataTransfer(TypeOfMessage.GET_INFO,order);
			ClientUI.chat.accept(data);
		}
    }
/**
 * initialize the window
 */
    public void initialize(URL location, ResourceBundle resources) {
    	instance=this;
    }
}
