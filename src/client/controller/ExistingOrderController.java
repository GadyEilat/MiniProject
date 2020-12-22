package client.controller;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ExistingOrderController extends AbstractScenes{

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
    private ImageView backBtn;

    @FXML
    void GoBack(MouseEvent event) {

    }

    public void notFound() {

		msgFromController.setText("Order Number Not Found");
	}
	
	
	public void isFound() {
		switchScenes("/client/boundaries/Order Management.fxml", "Order Management");
	}
    
    @FXML
    void NextButton(ActionEvent event) {
    	String OrderNum = OrderNumberTxt.getText();
		if (OrderNum.trim().isEmpty()) {
			msgFromController.setText("Please enter an existing order number");
		} 
		else { //send to server and check the order number? (FIX)
//			ClientUI.chat.accept(OrderNum);
			switchScenes("/client/boundaries/Order Management.fxml", "Order Management");
		}
    }

    @FXML
    void initialize() {
    }
}
