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

public class ExistingOrderController extends AbstractScenes{

	public Order order = new Order(null,null,null,null,null,null, null, null);
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

    @FXML
    void GoBack(ActionEvent event) {
    	switchScenes("/client/boundaries/Travelers.fxml", "Travelers");
    }

    public void wasCanceled() {
    	switchScenes("/client/boundaries/Travelers.fxml", "Travelers");
    }
    public void notFound() {

		msgFromController.setText("Order Number Not Found");
	}
	
	
	public void isFound() {
		switchScenes("/client/boundaries/Order Management.fxml", "Order Management");
	}
	
	public static ExistingOrderController instance;
    
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

    public void initialize(URL location, ResourceBundle resources) {
    	instance=this;
    }
}
